package org.kd.server.web.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.*;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.*;

@Controller
@RequestMapping("/recruitInfo")
public class RecruitInfoSecondaryController {
	@Resource(name="enterpriseService")
	private EnterpriseService enterpriseService;
	
	@Resource(name="positionService")
	private PositionService positionService;
	
	@Resource(name="provinceCityAreaService")
	private ProvinceCityAreaService provinceCityAreaService;
	
	@Resource(name="sectionService")
	private SectionService sectionService;
	
	@Resource(name="meteringModeService")
	private MeteringModeService meteringModeService;
	
	@Resource(name="recruitInfoService")
	private RecruitInfoService recruitInfoService;
	
	@Resource(name="dateIntervalService")
	private DateIntervalService dateIntervalService;
	
	@Resource(name="recruitInfoDiscussService")
	private RecruitInfoDiscussService recruitInfoDiscussService;

	@RequestMapping("/addRecruitInfoPage")
	public String addRecruitInfoPage(Model model) throws Exception{
		List<Enterprise> enterpriseList = enterpriseService.getAll(Enterprise.class);
		
		List<Enterprise> enterpriseNewList = new ArrayList<Enterprise>();
		if(!enterpriseList.isEmpty()){
	    	for (Enterprise enterprise:enterpriseList) {	
	    		
	    		if(enterprise.getUser()!=null){
	    			enterprise.setUserVo(new UserVo(enterprise.getUser().getId(), enterprise.getUser().getNickName(), enterprise.getUser().getHeadImage(), enterprise.getUser().getSex(), enterprise.getUser().getEasemobName()));
	    		}
	    		
	    		enterpriseNewList.add(enterprise);
	    	}
	    }
		
		List<Position> positionList = positionService.getAll(Position.class);
		
		List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.getProvince();
		model.addAttribute("provinceCityAreaList", provinceCityAreaList);
		
		List<Section> sectionList = sectionService.getAll(Section.class);
		model.addAttribute("sectionList", sectionList);
		
		List<MeteringMode> meteringModeList = meteringModeService.getAll(MeteringMode.class);
    	model.addAttribute("meteringModeList", meteringModeList);

		model.addAttribute("enterpriseList", enterpriseNewList);
		model.addAttribute("positionList", positionList);
		return "recruitInfo/recruitInfo";
	}
	
	@RequestMapping("/editRecruitInfoPage")
	public String editRecruitInfoPage(Long recruitInfoId,Model model) throws Exception{
		RecruitInfo recruitInfo = recruitInfoService.getById(RecruitInfo.class, recruitInfoId);
	    		
		if(recruitInfo.getEnterprise()!=null&&recruitInfo.getEnterprise().getUser()!=null){
			Enterprise enterprise = recruitInfo.getEnterprise();
			enterprise.setUserVo(new UserVo(enterprise.getUser().getId(), enterprise.getUser().getNickName(), enterprise.getUser().getHeadImage(), enterprise.getUser().getSex(), enterprise.getUser().getEasemobName()));
			recruitInfo.setEnterprise(enterprise);
		}
		
        List<Position> positionList = positionService.getAll(Position.class);
        
		List<Section> sectionList = sectionService.getAll(Section.class);
		model.addAttribute("sectionList", sectionList);
		
		List<MeteringMode> meteringModeList = meteringModeService.getAll(MeteringMode.class);
    	model.addAttribute("meteringModeList", meteringModeList);
		
		List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.getProvince();
		model.addAttribute("provinceCityAreaList", provinceCityAreaList);
		model.addAttribute("recruitInfo", recruitInfo);
		model.addAttribute("positionList", positionList);
		return "recruitInfo/recruitInfoEdit";
	}
	
	@ResponseBody
	@RequestMapping("/dateIntervalList")
	public Model dateIntervalList(Model model) throws Exception{
		List<DateInterval> dateIntervalList = dateIntervalService.getAll(DateInterval.class);
		
		model.addAttribute("result",dateIntervalList);
		return model;
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value="/recruitInfoDiscuss",method=RequestMethod.POST)
	public Map<String,Object> recruitInfoDiscuss(HttpServletRequest request,@Valid RecruitInfoDiscuss recruitInfoDiscuss,BindingResult res) throws Exception{
		
		if(res.hasErrors()){
			throw new BusinessException(RetMsg.paramError,"点评的招聘信息不能为空");
		}
		
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		recruitInfoDiscuss.setDiscussTime(new Date());
		recruitInfoDiscuss.setUser(user);
		
		recruitInfoDiscussService.save(recruitInfoDiscuss); 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);
		return map;
	}
	
	@RequestMapping("/recruitInfoDiscussList")
	public String recruitInfoDiscussList(Long recruitInfoId,Model model,
			@RequestParam(required=false,defaultValue = "1") int page,
			@RequestParam(required=false,defaultValue = "10") int pageSize) throws Exception{
		List<QueryCondition>  queryConditionList = new ArrayList<QueryCondition>();
		queryConditionList.add(new QueryCondition("recruitInfo.id", QueryCondition.EQ,recruitInfoId));

		Pagination<RecruitInfoDiscuss> recruitInfoDiscussPagination = recruitInfoDiscussService.getPagination(RecruitInfoDiscuss.class, queryConditionList, "order by id desc", page, pageSize);
		
		List<RecruitInfoDiscuss> recruitInfoDiscussNewList = new ArrayList<RecruitInfoDiscuss>();
		if(recruitInfoDiscussPagination.getRecordCount()>0){
	    	List<RecruitInfoDiscuss> recruitInfoDiscussList = recruitInfoDiscussPagination.getRecordList();
	    	for (RecruitInfoDiscuss recruitInfoDiscuss:recruitInfoDiscussList) {	
	    		
	    		recruitInfoDiscuss.setUserVo(new UserVo(recruitInfoDiscuss.getUser().getId(),recruitInfoDiscuss.getUser().getNickName(),recruitInfoDiscuss.getUser().getHeadImage(),recruitInfoDiscuss.getUser().getSex(),recruitInfoDiscuss.getUser().getEasemobName()));

	    		recruitInfoDiscussNewList.add(recruitInfoDiscuss);
	    	}
	    	recruitInfoDiscussPagination.setRecordList(recruitInfoDiscussNewList);
	    }
		model.addAttribute("result", recruitInfoDiscussPagination);

		return "";
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value="/delMyRecruitInfo",method=RequestMethod.POST)
	public Model delCollection(@RequestBody Long[] ids,Model model,HttpServletRequest request) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);

		if(ids!=null&&ids.length>0){
			for (Long id:ids) {
				RecruitInfo recruitInfo = recruitInfoService.getById(RecruitInfo.class, id);
				if(recruitInfo!=null&&recruitInfo.getEnterprise()!=null&&recruitInfo.getEnterprise().getUser()!=null&&recruitInfo.getEnterprise().getUser().getId().equals(user.getId())){
					recruitInfoService.delete(RecruitInfo.class, id);
				}else{
					throw new BusinessException(RetMsg.authorizedOverError, "只能删除自己发布的招聘信息");
				}
			}
		}else{
			throw new BusinessException(RetMsg.paramError, "要删除的招聘信息的ID不能为空");
		}
		
		return model;
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value="/refreshMyRecruitInfo",method=RequestMethod.POST)
	public Model refreshMyRecruitInfo(@RequestBody Long[] recruitInfoIds,Model model,HttpServletRequest request) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);

		if(recruitInfoIds!=null&&recruitInfoIds.length>0){
			for (Long recruitInfoId : recruitInfoIds) {
				RecruitInfo recruitInfo = recruitInfoService.getById(RecruitInfo.class, recruitInfoId);
				if(recruitInfo!=null&&recruitInfo.getEnterprise()!=null&&recruitInfo.getEnterprise().getUser()!=null&&recruitInfo.getEnterprise().getUser().getId().equals(user.getId())){
					recruitInfo.setRefreshTime(new Date());
					recruitInfoService.update(recruitInfo);
				}else{
					throw new BusinessException(RetMsg.authorizedOverError, "只能修改自己发布的招聘信息");
				}
			}
		}else{
			throw new BusinessException(RetMsg.paramError,"要刷新的招聘信息不能为空");
		}
		return model;
	}
	
}
