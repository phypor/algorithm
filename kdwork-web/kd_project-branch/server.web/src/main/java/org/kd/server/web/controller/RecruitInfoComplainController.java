package org.kd.server.web.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.RecruitInfoComplain;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.service.RecruitInfoComplainService;
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
@RequestMapping("/recruitInfoComplain")
public class RecruitInfoComplainController {
	
	@Resource(name="recruitInfoComplainService")
	private RecruitInfoComplainService recruitInfoComplainService;

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(method=RequestMethod.POST)
	public Map<String,Object> recruitInfoDiscuss(HttpServletRequest request,@Valid RecruitInfoComplain recruitInfoComplain,BindingResult res) throws Exception{
		
		if(res.hasErrors()){
			throw new BusinessException(RetMsg.paramError,"要投诉的内容不能为空");
		}
		
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		recruitInfoComplain.setComplainTime(new Date());
		recruitInfoComplain.setUser(user);
		
		recruitInfoComplainService.save(recruitInfoComplain); 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);
		return map;
	}
	
	@RequestMapping("/recruitInfoComplainList")
	public String recruitInfoDiscussList(Long recruitInfoId,Model model,
			@RequestParam(required=false,defaultValue = "1") int page,
			@RequestParam(required=false,defaultValue = "10") int pageSize) throws Exception{
		
		List<QueryCondition>  queryConditionList = new ArrayList<QueryCondition>();
		queryConditionList.add(new QueryCondition("recruitInfo.id",QueryCondition.EQ,recruitInfoId));

		Pagination<RecruitInfoComplain> recruitInfoComplainPagination = recruitInfoComplainService.getPagination(RecruitInfoComplain.class, queryConditionList, "order by id desc", page, pageSize);
		
		List<RecruitInfoComplain> recruitInfoComplainNewList = new ArrayList<RecruitInfoComplain>();
		if(recruitInfoComplainPagination.getRecordCount()>0){
	    	List<RecruitInfoComplain> recruitInfoDiscussList = recruitInfoComplainPagination.getRecordList();
	    	for (RecruitInfoComplain recruitInfoComplain:recruitInfoDiscussList) {	
	    		
	    		recruitInfoComplain.setUserVo(new UserVo(recruitInfoComplain.getUser().getId(),recruitInfoComplain.getUser().getNickName(),recruitInfoComplain.getUser().getHeadImage(),recruitInfoComplain.getUser().getSex(),recruitInfoComplain.getUser().getEasemobName()));

	    		recruitInfoComplainNewList.add(recruitInfoComplain);
	    	}
	    	recruitInfoComplainPagination.setRecordList(recruitInfoComplainNewList);
	    }
		model.addAttribute("result", recruitInfoComplainPagination);

		return "";
	}
}
