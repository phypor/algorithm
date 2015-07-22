package org.kd.server.web.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.CircleInfoClickLikeLog;
import org.kd.server.beans.entity.FriendCircle;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.entity.UserCircleInfo;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/userCircleInfo")
public class UserCircleInfoController {
	
	@Resource(name="userCircleInfoService")
	private UserCircleInfoService userCircleInfoService;
	
	@Resource(name="friendCircleService")
	private FriendCircleService friendCircleService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="circleInfoDiscussService")
	private CircleInfoDiscussService  circleInfoDiscussService;
	
	@Resource(name="circleInfoClickLikeLogService")
	private CircleInfoClickLikeLogService circleInfoClickLikeLogService;

	@RequiresAuthentication
	@RequestMapping("/userCircleInfoList")
	public String userCircleInfo(Model model,HttpServletRequest request,
			@RequestParam(required=false,defaultValue = "1") int page,
			@RequestParam(required=false,defaultValue = "10") int pageSize) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		List<FriendCircle> friendCircleList = userService.findFriendCircleByUserId(user.getId());
		
		Pagination<UserCircleInfo> userCircleInfoPaginationRet = new Pagination<UserCircleInfo>();
		
		if(!friendCircleList.isEmpty()){
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			
			StringBuffer ids = new StringBuffer();
			for (int i = 0; i < friendCircleList.size(); i++) {
				ids.append(friendCircleList.get(i).getId());
				if(i<(friendCircleList.size()-1)){
					ids.append(",");
				}
			}
			
			queryConditions.add(new QueryCondition("friendCircle.id in ("+ids.toString()+")"));

			userCircleInfoPaginationRet =	userCircleInfoService.getPagination(UserCircleInfo.class, queryConditions, "order by id desc", page, pageSize);
		    
			List<UserCircleInfo> userCircleInfoNewList = new ArrayList<UserCircleInfo>();
			if(userCircleInfoPaginationRet.getRecordCount()>0){
		    	List<UserCircleInfo> userCircleInfoList = userCircleInfoPaginationRet.getRecordList();
		    	for (UserCircleInfo userCircleInfo:userCircleInfoList) {
		    		List<QueryCondition> clickLikeConditions = new ArrayList<QueryCondition>();
		    		clickLikeConditions.add(new QueryCondition("userCircleInfo.id = "+userCircleInfo.getId()));
		    		clickLikeConditions.add(new QueryCondition("user.id = "+user.getId()));
		    		
		    		long count = circleInfoClickLikeLogService.getRecordCount(CircleInfoClickLikeLog.class, clickLikeConditions);
		    		
		    		userCircleInfo.setOriginalUserVo(new UserVo(userCircleInfo.getOriginalUser().getId(), userCircleInfo.getOriginalUser().getNickName(), userCircleInfo.getOriginalUser().getHeadImage(), userCircleInfo.getOriginalUser().getSex(), userCircleInfo.getOriginalUser().getEasemobName()));
		    		userCircleInfo.setPublishUserVo(new UserVo(userCircleInfo.getPublishUser().getId(), userCircleInfo.getPublishUser().getNickName(), userCircleInfo.getPublishUser().getHeadImage(), userCircleInfo.getPublishUser().getSex(),userCircleInfo.getPublishUser().getEasemobName()));
		    		
		    		userCircleInfo.setIsSelfClickLike(count>0?true:false);
		    		userCircleInfoNewList.add(userCircleInfo);
		    	}
		    }
			userCircleInfoPaginationRet.setRecordList(userCircleInfoNewList);
		}

        model.addAttribute("result", userCircleInfoPaginationRet);
		
		return "userCircleInfo/userCircleInfoList";
	}
	
	@RequiresAuthentication
	@RequestMapping("/selfUserCircleInfoList")
	public String selfUserCircleInfoList(Model model,HttpServletRequest request,
			@RequestParam(required=false,defaultValue = "1") int page,
			@RequestParam(required=false,defaultValue = "10") int pageSize) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		Pagination<UserCircleInfo> userCircleInfoPagination =  new Pagination<UserCircleInfo>();
	
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("publishUser.id = "+user.getId()));

		userCircleInfoPagination =	userCircleInfoService.getPagination(UserCircleInfo.class, queryConditions, "order by id desc", page, pageSize);

		List<UserCircleInfo> userCircleInfoNewList = new ArrayList<UserCircleInfo>();
		if(userCircleInfoPagination.getRecordCount()>0){
	    	List<UserCircleInfo> userCircleInfoList = userCircleInfoPagination.getRecordList();
	    	for (UserCircleInfo userCircleInfo:userCircleInfoList) {
	    		List<QueryCondition> clickLikeConditions = new ArrayList<QueryCondition>();
	    		clickLikeConditions.add(new QueryCondition("userCircleInfo.id = "+userCircleInfo.getId()));
	    		clickLikeConditions.add(new QueryCondition("user.id = "+user.getId()));
	    		
	    		long count = circleInfoClickLikeLogService.getRecordCount(CircleInfoClickLikeLog.class, clickLikeConditions);
	    		
	    		userCircleInfo.setOriginalUserVo(new UserVo(userCircleInfo.getOriginalUser().getId(), userCircleInfo.getOriginalUser().getNickName(), userCircleInfo.getOriginalUser().getHeadImage(), userCircleInfo.getOriginalUser().getSex(),userCircleInfo.getOriginalUser().getEasemobName()));	
	    		userCircleInfo.setPublishUserVo(new UserVo(userCircleInfo.getPublishUser().getId(), userCircleInfo.getPublishUser().getNickName(), userCircleInfo.getPublishUser().getHeadImage(), userCircleInfo.getPublishUser().getSex(),userCircleInfo.getPublishUser().getEasemobName()));
	    		
	    		userCircleInfo.setIsSelfClickLike(count>0?true:false);
	    		userCircleInfoNewList.add(userCircleInfo);
	    	}
			userCircleInfoPagination.setRecordList(userCircleInfoNewList);
	    }
		
        model.addAttribute("result", userCircleInfoPagination);
		
		return "";
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value="/addUserCircleInfo")
	public Model addUserCircleInfo(@RequestParam(required=false,defaultValue = "")String content,
			@RequestParam(required=false,defaultValue = "")String imagesJson,int contentType,Model model,HttpServletRequest request) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		UserCircleInfo userCircleInfo = new UserCircleInfo();
		userCircleInfo.setClickLikeNum(0);
		userCircleInfo.setContent(content);
		userCircleInfo.setImagesJson(imagesJson);
		userCircleInfo.setDiscussNum(0);
		
		FriendCircle friendCircle = userCircleInfoService.findFriendCircleByUserId(user.getId());
		if(friendCircle==null){
			friendCircle = new FriendCircle();
			
			Set<User> userSet = new HashSet<User>();
			userSet.add(user);
			userSet.addAll(user.getUserCircles());
			
			friendCircle.setUsers(userSet);
			friendCircleService.save(friendCircle);
		}else{
			if(!friendCircle.getUsers().containsAll(user.getUserCircles())){
				while(user.getUserCircles().iterator().hasNext()){
					User us = user.getUserCircles().iterator().next();
					if(!friendCircle.getUsers().contains(us)){
						friendCircle.getUsers().add(us);
					}	
				}
			}
			
			friendCircleService.update(friendCircle);
		}
		
		userCircleInfo.setFriendCircle(friendCircle);
		
		userCircleInfo.setOriginalUser(user);
		userCircleInfo.setPublishTime(new Date());
		userCircleInfo.setPublishType(1);
		userCircleInfo.setPublishUser(user);
		userCircleInfo.setRepeatNum(0);
		
		userCircleInfoService.save(userCircleInfo);
		
		userCircleInfo.setOriginalUserVo(new UserVo(userCircleInfo.getOriginalUser().getId(), userCircleInfo.getOriginalUser().getNickName(), userCircleInfo.getOriginalUser().getHeadImage(), userCircleInfo.getOriginalUser().getSex(),userCircleInfo.getOriginalUser().getEasemobName()));	
		userCircleInfo.setPublishUserVo(new UserVo(userCircleInfo.getPublishUser().getId(), userCircleInfo.getPublishUser().getNickName(), userCircleInfo.getPublishUser().getHeadImage(), userCircleInfo.getPublishUser().getSex(),userCircleInfo.getPublishUser().getEasemobName()));
		
		model.addAttribute("result", userCircleInfo);
		return model;
	}
	
}
