package org.kd.server.web.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.Infomation;
import org.kd.server.beans.entity.InfomationClickLikeLog;
import org.kd.server.beans.entity.InfomationDiscuss;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.DateUtil;
import org.kd.server.service.InfomationClickLikeLogService;
import org.kd.server.service.InfomationDiscussService;
import org.kd.server.service.InfomationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/infomation")
public class InfomationController {
	@Resource(name="infomationService")
	private InfomationService infomationService;
	
	@Resource(name="infomationDiscussService")
	private InfomationDiscussService infomationDiscussService;
	
	@Resource(name="infomationClickLikeLogService")
	private InfomationClickLikeLogService infomationClickLikeLogService;

	@RequestMapping("/infomationList")
	public String infomationList(@RequestParam(required=false,defaultValue = "1") int page,
			@RequestParam(required=false,defaultValue = "1")int newsType,
			@RequestParam(required=false,defaultValue = "10") int pageSize,Model model,
			HttpServletRequest request,@RequestParam(required=false,defaultValue = "1")int dateIntervalId) throws Exception{
		
		List<QueryCondition>  queryConditionList = new ArrayList<QueryCondition>();
		queryConditionList.add(new QueryCondition("newsType",QueryCondition.EQ,newsType));
		
		if(dateIntervalId!=1){
			Date date = null;
            switch (dateIntervalId) {
			case 2:
				date = DateUtil.findFirstDayOfWork(new Date());
							break;
			case 3:
				date = DateUtil.findFirstDayOfMonth(new Date());
				break;
			case 4:
				date = DateUtil.findMonthBeforeOrAfter(new Date(), -3);
				break;
			default:
				break;
			}
            if(date!=null){
            	queryConditionList.add(new QueryCondition("createTime",QueryCondition.GE,date));	
            }
            
		}

		Pagination<Infomation> infomationPagination = infomationService.getPagination(Infomation.class, queryConditionList, "order by createTime desc", page, pageSize);
		model.addAttribute("result", infomationPagination);
		
		if(!request.getRequestURI().endsWith(".json")){
			model.addAttribute("newsType", newsType);
		}
		
		return "infomation/infomationList";
	}
	
	@RequestMapping("/infomation")
	public String infomation(){
		
		
		return "infomation/infomation";
	}
	
	@ResponseBody
	@RequestMapping(value="/delInfomation",method=RequestMethod.POST)
	public boolean delInfomation(Long id) throws Exception{
		infomationService.delete(Infomation.class, id);
		
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/findById")
	public Map<String, Object> findById(Long id,Model model) throws Exception{
		Infomation infomation = infomationService.getById(Infomation.class, id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);
		map.put("result", infomation);
		return map;
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(method=RequestMethod.POST)
	public Map<String, Object> addInfomation(HttpServletRequest request,@Valid Infomation infomation,BindingResult res) throws Exception{
		if(res.hasErrors()){
			throw new BusinessException(RetMsg.paramError, RetMsg.paramErrorMsg);
		}
		
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		if(infomation.getId()==null){
			infomation.setClickLikeNum(0);
			infomation.setCreateTime(new Date());
			infomation.setCreateUser(user);
			infomation.setDiscussNum(0);
			infomation.setReadNum(0);
			
			infomationService.save(infomation);
		}else{
			Infomation infomation2 = infomationService.getById(Infomation.class, infomation.getId());
			
			infomation2.setClickLikeNum(infomation.getClickLikeNum());
			infomation2.setCreateUser(user);
			infomation2.setDiscussNum(infomation.getDiscussNum());
			infomation2.setReadNum(infomation.getReadNum());
			
			infomationService.update(infomation2);
		}		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);
		map.put("result", infomation);

		return map;
	}
		
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value="/infomationDiscuss",method=RequestMethod.POST)
	public Map<String,Object> infomationDiscuss(HttpServletRequest request,@Valid InfomationDiscuss infomationDiscuss,BindingResult res) throws Exception{
		
		if(res.hasErrors()){
			throw new BusinessException(RetMsg.paramError,"点评内容不能为空");
		}
		
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		infomationDiscuss.setDiscussTime(new Date());
		infomationDiscuss.setUser(user);
		
		infomationDiscussService.save(infomationDiscuss); 
		
		Infomation infomation = infomationService.getById(Infomation.class, infomationDiscuss.getInfomation().getId());
		infomation.setDiscussNum(infomation.getDiscussNum()+1);
		infomationService.update(infomation);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);
		return map;
	}
	
	@RequiresAuthentication
	@RequestMapping(value="/clickLike",method=RequestMethod.POST)
	public Model clickLike(Model model,HttpServletRequest request,Long infomationId) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		List<QueryCondition>  queryConditionList = new ArrayList<QueryCondition>();
		queryConditionList.add(new QueryCondition("user.id",QueryCondition.EQ,user.getId()));
		queryConditionList.add(new QueryCondition("infomation.id",QueryCondition.EQ,infomationId));
		
		long count = infomationClickLikeLogService.getRecordCount(InfomationClickLikeLog.class, queryConditionList);
		
		if(count==0){
			Infomation infomation = infomationService.getById(Infomation.class,infomationId);
			infomation.setClickLikeNum(infomation.getClickLikeNum()+1);
			infomationService.update(infomation);
			
			InfomationClickLikeLog infomationClickLikeLog = new InfomationClickLikeLog();
			infomationClickLikeLog.setClickLikeTime(new Date());
			infomationClickLikeLog.setInfomation(infomation);
			infomationClickLikeLog.setUser(user);
			
			infomationClickLikeLogService.save(infomationClickLikeLog);
		}else{
			model.addAttribute("status", RetMsg.repeatAddError);
			model.addAttribute("msg", RetMsg.repeatAddErrorMsg);
		}
		
		return model;
	}
	
}
