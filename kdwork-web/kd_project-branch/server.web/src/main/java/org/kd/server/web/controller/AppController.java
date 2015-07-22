package org.kd.server.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.AppChannelType;
import org.kd.server.beans.entity.AppClientInfo;
import org.kd.server.beans.entity.AppPlatformType;
import org.kd.server.beans.entity.FeedBackLog;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.DateUtil;
import org.kd.server.service.AppChannelTypeService;
import org.kd.server.service.AppClientInfoService;
import org.kd.server.service.AppPlatformTypeService;
import org.kd.server.service.FeedBackLogService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController { 
	@Resource(name="feedBackLogService")
	private FeedBackLogService feedBackLogService;
	
	@Resource(name="appChannelTypeService")
	private AppChannelTypeService appChannelTypeService;
	
	@Resource(name="appPlatformTypeService")
	private AppPlatformTypeService appPlatformTypeService;
	
	@Resource(name="appClientInfoService")
	private AppClientInfoService appClientInfoService;

	@RequestMapping("/appClient/download")  
	public ResponseEntity<Object> download(String fileName) throws IOException {  
	    HttpHeaders headers = new HttpHeaders();  
	    headers.setPragma("No-cache");
	    headers.setCacheControl("No-cache");
	    headers.setExpires(0);
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    headers.setContentDispositionFormData("attachment",fileName);  
	    
	    String filePath = "";
	    
        if(System.getProperty("kdServer.root").endsWith("/")){
        	filePath =  "/mnt/webapps/kd_upload/appClient/";
        }else{
        	filePath = "D:\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\kd_upload\\appClient\\";
        }
	    
	    return new ResponseEntity<Object>(FileUtils.readFileToByteArray(new File(filePath+fileName)),  
	                                      headers, HttpStatus.CREATED);  
	}

	@RequestMapping("/introduction")
	public String introduction(){
		return "introduction/introduction";
	}
	
	@RequestMapping("/story")
	public String story(){
		return "introduction/story";
	}
	
	@RequiresAuthentication
	@RequestMapping("/appClient/management")
	public String management(@RequestParam(required=false,defaultValue = "1") int page,@RequestParam(required=false,defaultValue = "10") int pageSize,
			Model model) throws Exception{
		List<AppChannelType> appChannelTypeList = appChannelTypeService.getAll(AppChannelType.class);
		
		List<AppPlatformType> appPlatformTypeList = appPlatformTypeService.getAll(AppPlatformType.class);
		
		model.addAttribute("appChannelTypeList", appChannelTypeList);
		model.addAttribute("appPlatformTypeList", appPlatformTypeList);
		
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		
		Pagination<AppClientInfo> recruitInfoPagination = appClientInfoService.getPagination(AppClientInfo.class, queryConditions, "order by id desc", page, pageSize);
		model.addAttribute("result", recruitInfoPagination);
		
		return "appClient/appClientManage";
	}
	
	 //删除招聘信息接口
    @ResponseBody
    @RequestMapping(value="/appClient/delAppClientInfo",method=RequestMethod.POST)
    public boolean delRecruitInfo(Long id) throws Exception{
    	appClientInfoService.delete(AppClientInfo.class, id);
    	return true;
    }
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value="/appClientInfo",method=RequestMethod.POST)
	public Map<String, Object> addAppClient(HttpServletRequest request,String pushTime,@Valid AppClientInfo appClientInfo,BindingResult res) throws Exception{
		User currentUser = (User)request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);
		
		appClientInfo.setCreateTime(new Date());
		appClientInfo.setCreateUser(currentUser);
		appClientInfo.setPushTime(DateUtil.parseDateByString(pushTime, "yyyy-MM-dd"));
		
		if(appClientInfo.getId()==null){
			appClientInfoService.save(appClientInfo);
		}else{
			appClientInfoService.update(appClientInfo);
		}
		
		map.put("result", appClientInfo);

		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/checkRenewAppClient",method=RequestMethod.POST)
	public Model checkRenewAppClient(@RequestParam(required=false,defaultValue = "1") Long appChannelTypeId,
			@RequestParam(required=false,defaultValue = "0")double versionCode,
			@RequestParam(required=false,defaultValue = "1") Long appPlatformTypeId,Model model) throws Exception{
		
		AppClientInfo appClientInfo = (AppClientInfo) appClientInfoService.findByChannelPlatformTypeVersion(appChannelTypeId,versionCode,appPlatformTypeId);

		model.addAttribute("result", appClientInfo);
		return model;
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value="/addFeedBackLog",method=RequestMethod.POST)
	public Map<String,Object> addFeedBackLog(Model model,HttpServletRequest request,@Valid FeedBackLog feedBackLog,BindingResult res) throws Exception{
		User currentUser = (User)request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
	    if(res.hasErrors())
	    	throw new BusinessException(RetMsg.paramError,"反馈内容不能为空");
	    
	    feedBackLog.setUser(currentUser);
	    feedBackLog.setTime(new Date());
	    
		feedBackLogService.save(feedBackLog);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg",PublicInfoConfig.SUCCESS_MSG);
		return map;
	}
}

