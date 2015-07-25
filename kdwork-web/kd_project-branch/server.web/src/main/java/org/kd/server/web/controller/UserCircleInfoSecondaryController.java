package org.kd.server.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.CircleInfoClickLikeLog;
import org.kd.server.beans.entity.CircleInfoDiscuss;
import org.kd.server.beans.entity.FriendCircle;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.entity.UserCircleInfo;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.LogRecord;
import org.kd.server.service.CircleInfoClickLikeLogService;
import org.kd.server.service.CircleInfoDiscussService;
import org.kd.server.service.UserCircleInfoService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userCircleInfo")
public class UserCircleInfoSecondaryController {
	
	@Resource(name="userCircleInfoService")
	private UserCircleInfoService userCircleInfoService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="circleInfoClickLikeLogService")
	private CircleInfoClickLikeLogService circleInfoClickLikeLogService;
	
	@Resource(name="circleInfoDiscussService")
	private CircleInfoDiscussService  circleInfoDiscussService;

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/newInfoCount")
	public Model newInfoCount(Long lastId,Model model,HttpServletRequest request) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		StringBuffer ids = new StringBuffer();
		
		int count = 0;
		
		if(user.getUserCircles()!=null&&user.getUserCircles().size()>0){
			Iterator<FriendCircle> friendCircleIt = user.getFriendCircles().iterator();
			while(friendCircleIt.hasNext()){
				FriendCircle friendCircle = friendCircleIt.next();
				ids.append(friendCircle.getId());
				if(friendCircleIt.hasNext()){
					ids.append(",");
				}
			}
			  
			count = userCircleInfoService.findCountByLastId(lastId, ids.toString());
		}

		model.addAttribute("result", count);
		
		return model;
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value="/circleInfoDiscuss",method=RequestMethod.POST)
	public Map<String,Object> infomationDiscuss(HttpServletRequest request,@Valid CircleInfoDiscuss circleInfoDiscuss,BindingResult res) throws Exception{
		
		if(res.hasErrors()){
			throw new BusinessException(RetMsg.paramError,"对话内容为空或超出大小,关联对话人不能为空");
		}
		
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		circleInfoDiscuss.setDiscussTime(new Date());
		circleInfoDiscuss.setUserActive(user);
		
		circleInfoDiscuss.setActiveUserVo(new UserVo(user.getId(), user.getNickName(), user.getHeadImage(), user.getSex(),user.getEasemobName()));
		
		User userQuilt = userService.getById(User.class, circleInfoDiscuss.getUserQuilt().getId());
		circleInfoDiscuss.setQuiltUserVo(new UserVo(userQuilt.getId(), userQuilt.getNickName(), userQuilt.getHeadImage(), userQuilt.getSex(),userQuilt.getEasemobName()));
		
		circleInfoDiscussService.save(circleInfoDiscuss); 
		
		UserCircleInfo userCircleInfo = userCircleInfoService.getById(UserCircleInfo.class,circleInfoDiscuss.getUserCircleInfo().getId());
		userCircleInfo.setDiscussNum(userCircleInfo.getDiscussNum()+1);
		userCircleInfoService.update(userCircleInfo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);
		return map;
	}
	
	@RequiresAuthentication
	@RequestMapping(value="/clickLike",method=RequestMethod.POST)
	public Model clickLike(Model model,HttpServletRequest request,Long userCircleInfoId) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		LogRecord.info(user.getNickName()+" 点赞+1");
		List<QueryCondition>  queryConditionList = new ArrayList<QueryCondition>();
		queryConditionList.add(new QueryCondition("user.id",QueryCondition.EQ,user.getId()));
		queryConditionList.add(new QueryCondition("userCircleInfo.id",QueryCondition.EQ,userCircleInfoId));
		
		long count = circleInfoClickLikeLogService.getRecordCount(CircleInfoClickLikeLog.class, queryConditionList);
		
		if(count==0){
			UserCircleInfo userCircleInfo = userCircleInfoService.getById(UserCircleInfo.class,userCircleInfoId);
			if(userCircleInfo!=null){
				userCircleInfo.setClickLikeNum(userCircleInfo.getClickLikeNum()+1);
				userCircleInfoService.update(userCircleInfo);
				
				CircleInfoClickLikeLog circleInfoClickLikeLog = new CircleInfoClickLikeLog();
				circleInfoClickLikeLog.setClickLikeTime(new Date());
				circleInfoClickLikeLog.setUserCircleInfo(userCircleInfo);
				circleInfoClickLikeLog.setUser(user);
				
				circleInfoClickLikeLogService.save(circleInfoClickLikeLog);
			}else{
				throw new BusinessException(RetMsg.paramError,"要点赞的朋友圈消息不能为空");
			}
		}else{
			model.addAttribute("status", RetMsg.repeatAddError);
			model.addAttribute("msg", RetMsg.repeatAddErrorMsg);
		}
		return model;
	}

	@RequiresAuthentication
	@RequestMapping(value="/cancelClickLike",method=RequestMethod.POST)
	public Model cancelClickLike(Model model,HttpServletRequest request,Long userCircleInfoId) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		LogRecord.info(user.getNickName()+" 取消赞！");
		List<QueryCondition>  queryConditionList = new ArrayList<QueryCondition>();
		queryConditionList.add(new QueryCondition("user.id",QueryCondition.EQ,user.getId()));
		queryConditionList.add(new QueryCondition("userCircleInfo.id",QueryCondition.EQ,userCircleInfoId));
		long count = circleInfoClickLikeLogService.getRecordCount(CircleInfoClickLikeLog.class, queryConditionList);
		userCircleInfoService.getById(UserCircleInfo.class,userCircleInfoId).setClickLikeNum((int) (count-1));
		if(count>0){
			List<CircleInfoClickLikeLog> circleInfoClickLikeLogList = circleInfoClickLikeLogService.get(CircleInfoClickLikeLog.class, queryConditionList);
		    if(!circleInfoClickLikeLogList.isEmpty())
		    {
		    	for (CircleInfoClickLikeLog circleInfoClickLikeLog : circleInfoClickLikeLogList) {
		    		circleInfoClickLikeLogService.delete(CircleInfoClickLikeLog.class,circleInfoClickLikeLog.getId());
				}
		    }
		}else{
			model.addAttribute("status", RetMsg.repeatAddError);
			model.addAttribute("msg", RetMsg.repeatAddErrorMsg);
		}
		return model;
	}
	
	@RequiresAuthentication
	@RequestMapping("/circleInfoDiscussList")
	public Model infomationList(@RequestParam(required=false,defaultValue = "1") int page,
			@RequestParam(required=false,defaultValue = "10") int pageSize,Model model,
			HttpServletRequest request,Long userCircleInfoId) throws Exception{
		
		User currentUser = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		UserCircleInfo userCircleInfo = userCircleInfoService.getById(UserCircleInfo.class,userCircleInfoId);
		
		if(!currentUser.getFriendCircles().contains(userCircleInfo.getFriendCircle())){
			model.addAttribute("status", RetMsg.authorizedOverError);
			model.addAttribute("msg", RetMsg.authorizedOverErrorMsg);
			return model;
		}
		
		List<QueryCondition>  queryConditionList = new ArrayList<QueryCondition>();
		queryConditionList.add(new QueryCondition("userCircleInfo.id",QueryCondition.EQ,userCircleInfoId));

		Pagination<CircleInfoDiscuss> circleInfoDiscussPagination = circleInfoDiscussService.getPagination(CircleInfoDiscuss.class, queryConditionList, "order by id desc", page, pageSize);
		
		List<CircleInfoDiscuss> circleInfoDiscussNewList = new ArrayList<CircleInfoDiscuss>();
		if(circleInfoDiscussPagination.getRecordCount()>0){
	    	List<CircleInfoDiscuss> circleInfoDiscussList = circleInfoDiscussPagination.getRecordList();
	    	for (CircleInfoDiscuss circleInfoDiscuss:circleInfoDiscussList) {	
	    		
	    		circleInfoDiscuss.setActiveUserVo(new UserVo(circleInfoDiscuss.getUserActive().getId(), circleInfoDiscuss.getUserActive().getNickName(), circleInfoDiscuss.getUserActive().getHeadImage(), circleInfoDiscuss.getUserActive().getSex(),circleInfoDiscuss.getUserActive().getEasemobName()));
	    		
	    		circleInfoDiscuss.setQuiltUserVo(new UserVo(circleInfoDiscuss.getUserQuilt().getId(),circleInfoDiscuss.getUserQuilt().getNickName(), circleInfoDiscuss.getUserQuilt().getHeadImage(),circleInfoDiscuss.getUserQuilt().getSex(),circleInfoDiscuss.getUserQuilt().getEasemobName()));

	    		circleInfoDiscussNewList.add(circleInfoDiscuss);
	    	}
			circleInfoDiscussPagination.setRecordList(circleInfoDiscussNewList);
	    }
		model.addAttribute("result", circleInfoDiscussPagination);
		
		return model;
	}
	
	@RequiresAuthentication
	@RequestMapping(value="/delUserCircleInfo",method=RequestMethod.POST)
	public Model delCollection(@RequestBody Long[] ids,Model model,HttpServletRequest request) throws Exception{
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);

		if(ids!=null&&ids.length>0){
			for (Long id:ids) {
				UserCircleInfo userCircleInfo = userCircleInfoService.getById(UserCircleInfo.class, id);
				if(userCircleInfo!=null&&userCircleInfo.getPublishUser().getId().equals(user.getId())){
					userCircleInfoService.delete(UserCircleInfo.class, id);
				}else{
					throw new BusinessException(RetMsg.authorizedOverError, "只能删除自己发送的朋友圈消息");
				}
			}
		}else{
			throw new BusinessException(RetMsg.paramError,"要删除的朋友圈消息的ID不能为空");
		}
		
		return model;
	}
	
}
