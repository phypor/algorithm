package org.kd.server.web.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.Message;
import org.kd.server.beans.entity.MessageTalk;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.service.MessageService;
import org.kd.server.service.MessageTalkService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 
 * zmy
 * 
 * 用户消息控制器
 */

@Controller
@RequestMapping("/message")
public class MessageController {
	@Resource(name="messageTalkService")
    private MessageTalkService messageTalkService;
	
	@Resource(name="userService")
    private UserService userService;
	
	@Resource(name="messageService")
    private MessageService messageService;
	
	//获取指定会话过程(messagetalk)的分页的对话内容
	@RequiresAuthentication
	@RequestMapping("/findMessageByTalkId")
	public String findMessageByTalkId(@RequestParam(required=false,defaultValue = "1") int page,@RequestParam(required=false,defaultValue = "10") int pageSize
			,@RequestParam(required=false,defaultValue = "0")Long lastMessageId,Long messageTalkId,Model model) throws Exception{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("messageTalk.id="+messageTalkId));
		if(lastMessageId>0L){
		   queryConditions.add(new QueryCondition("id>"+lastMessageId));
		}
		Pagination<Message> messageList = messageService.getPagination(Message.class, queryConditions, "order by id desc", page, pageSize);
		
		List<Message> messageNewList = new ArrayList<Message>();
		if(messageList.getRecordCount()>0){
	    	List<Message> messages = messageList.getRecordList();
	    	for (Message message:messages) {	
	    		
	    		message.setTakeUserVo(new UserVo(message.getTakeUser().getId(), message.getTakeUser().getNickName(), message.getTakeUser().getHeadImage(), message.getTakeUser().getSex(), message.getTakeUser().getEasemobName()));
	    		message.setSendUserVo(new UserVo(message.getSendUser().getId(), message.getSendUser().getNickName(), message.getSendUser().getHeadImage(), message.getSendUser().getSex(), message.getSendUser().getEasemobName()));
				
	    		messageNewList.add(message);
	    	}
	    	messageList.setRecordList(messageNewList);
	    }
		
		model.addAttribute("result", messageList);
		return "";
	}
	
	//获取私信列表接口
	@RequiresAuthentication
	@RequestMapping("/messageTalkList")
	public String messageTalkList(@RequestParam(required=false,defaultValue = "1") int page,
			@RequestParam(required=false,defaultValue = "10") int pageSize,HttpServletRequest request,Model model) throws Exception{
		User currentUser = (User)request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("(user1Bean.id="+currentUser.getId()+" or user2Bean.id="+currentUser.getId()+")"));
		Pagination<MessageTalk> messageTalkList = messageTalkService.getPagination(MessageTalk.class, queryConditions, "order by id desc", page, pageSize);
		
		List<MessageTalk> messageTalkNewList = new ArrayList<MessageTalk>();
		if(messageTalkList.getRecordCount()>0){
	    	List<MessageTalk> messageTalks = messageTalkList.getRecordList();
	    	for (MessageTalk messageTalk:messageTalks) {	
	    		
	    		messageTalk.setUser1Vo(new UserVo(messageTalk.getUser1Bean().getId(),messageTalk.getUser1Bean().getNickName(),messageTalk.getUser1Bean().getHeadImage(),messageTalk.getUser1Bean().getSex(), messageTalk.getUser1Bean().getEasemobName()));

	    		messageTalk.setUser2Vo(new UserVo(messageTalk.getUser2Bean().getId(),messageTalk.getUser2Bean().getNickName(),messageTalk.getUser2Bean().getHeadImage(),messageTalk.getUser2Bean().getSex(), messageTalk.getUser2Bean().getEasemobName()));

	    		messageTalkNewList.add(messageTalk);
	    	}
			messageTalkList.setRecordList(messageTalkNewList);
	    }
		
		model.addAttribute("result", messageTalkList);
		return "";
	}
	
	//发送消息接口
	@RequestMapping("/chatPage")
	public String chatPage(Model model,HttpServletRequest request) throws Exception{
		
		List<User> userList = userService.getAll(User.class);
		
		Long talkUserId = userList.get(1).getId();
		Long sendUserId = userList.get(0).getId();
		
		MessageTalk messageTalk = messageTalkService.findBySendTakeUserId(sendUserId, talkUserId);
		
		Pagination<Message> messagePagination = null;
		
		if(messageTalk!=null){
			if(messageTalk.getUser1Bean().getId().equals(sendUserId)){
				messageTalk.setUser1UnreadCount(0);
				User user = messageTalk.getUser1Bean();
				
				int count = user.getUnreadMessageNum()-1;
				user.setUnreadMessageNum(count>=0?count:0);
			}else{
				messageTalk.setUser2UnreadCount(0);	
	            User user = messageTalk.getUser2Bean();
				
				int count = user.getUnreadMessageNum()-1;
				user.setUnreadMessageNum(count>=0?count:0);
			}
			
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("messageTalk.id", QueryCondition.EQ,messageTalk.getId()));
			
			messagePagination = messageService.getPagination(Message.class, queryConditions, "order by id desc", 1, 10);
			
			List<Message> messageNewList = new ArrayList<Message>();
			if(messagePagination.getRecordCount()>0){
		    	List<Message> messages = messagePagination.getRecordList();
		    	for (Message message:messages) {	
		    		
		    		message.setTakeUserVo(new UserVo(message.getTakeUser().getId(), message.getTakeUser().getNickName(), message.getTakeUser().getHeadImage(), message.getTakeUser().getSex(), message.getTakeUser().getEasemobName()));
		    		message.setSendUserVo(new UserVo(message.getSendUser().getId(), message.getSendUser().getNickName(), message.getSendUser().getHeadImage(), message.getSendUser().getSex(), message.getSendUser().getEasemobName()));
					
		    		messageNewList.add(message);
		    	}
		    	messagePagination.setRecordList(messageNewList);
		    }		
			messageTalkService.update(messageTalk);
		}
		
		model.addAttribute("userList", userList);
		model.addAttribute("content", messagePagination);
		model.addAttribute("talkUserId", talkUserId);	
	    model.addAttribute("sendUserId", sendUserId);
		return "message/chatMessage";
	}
	
	@RequiresAuthentication
	@RequestMapping(method=RequestMethod.POST )
	public String addMessage(@RequestParam(required=false,defaultValue = "1") int page,@RequestParam(required=false,defaultValue = "10") int pageSize,
			Long talkUserId,String content,Model model,HttpServletRequest request) throws Exception{
		User sendUser = (User)request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		MessageTalk messageTalk = messageTalkService.findBySendTakeUserId(sendUser.getId(), talkUserId);
		
		User takeUser = userService.getById(User.class, talkUserId);

		if(messageTalk==null){
			messageTalk = new MessageTalk();
			messageTalk.setUser1UnreadCount(1);
			messageTalk.setUser2UnreadCount(0);
			messageTalk.setUser1Bean(takeUser);
			messageTalk.setUser2Bean(sendUser);
			
			messageTalkService.save(messageTalk);
		}else{
			messageTalk.setUser1UnreadCount(messageTalk.getUser1UnreadCount()+1);
			
			if(messageTalk.getUser1Bean().getId().equals(sendUser.getId())){
				messageTalk.setUser1UnreadCount(0);
				User user = messageTalk.getUser1Bean();
				
				int count = user.getUnreadMessageNum()-1;
				user.setUnreadMessageNum(count>=0?count:0);
			}else{
				messageTalk.setUser2UnreadCount(0);	
	            User user = messageTalk.getUser2Bean();
				
				int count = user.getUnreadMessageNum()-1;
				user.setUnreadMessageNum(count>=0?count:0);
			}
		}
		
		if(!StringUtils.isEmpty(content)){
			   takeUser.setUnreadMessageNum(takeUser.getUnreadMessageNum()+1);
			
				Message message = new Message();
				message.setContent(content);
				message.setExistsRead(false);
				message.setMessageTalk(messageTalk);
				message.setSendShow(true);
				message.setSendTime(new Date());
				message.setSendUser(sendUser);
				message.setTakeUser(takeUser);
				message.setTakeShow(true);
				
				messageService.save(message);
				
				messageTalk.setMessage(message);
				messageTalkService.update(messageTalk);
		}
		
		 if(!request.getRequestURI().endsWith(".json")){
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("messageTalk.id", QueryCondition.EQ,messageTalk.getId()));
			
			Pagination<Message> messagePagination = messageService.getPagination(Message.class, queryConditions, "order by id desc", page, pageSize);
			
			if(!request.getRequestURI().endsWith(".json")){
			        List<User> userList = userService.getAll(User.class);
					model.addAttribute("userList", userList);
					
					model.addAttribute("content", messagePagination);
					model.addAttribute("talkUserId", talkUserId);	
				    model.addAttribute("sendUserId", sendUser.getId());
			}
			
			List<Message> messageNewList = new ArrayList<Message>();
			if(messagePagination.getRecordCount()>0){
		    	List<Message> messages = messagePagination.getRecordList();
		    	for (Message message:messages) {	
		    		
		    		message.setTakeUserVo(new UserVo(message.getTakeUser().getId(), message.getTakeUser().getNickName(), message.getTakeUser().getHeadImage(), message.getTakeUser().getSex(), message.getTakeUser().getEasemobName()));
		    		message.setSendUserVo(new UserVo(message.getSendUser().getId(), message.getSendUser().getNickName(), message.getSendUser().getHeadImage(), message.getSendUser().getSex(), message.getSendUser().getEasemobName()));
					
		    		messageNewList.add(message);
		    	}
		    	messagePagination.setRecordList(messageNewList);
		    }
			
			model.addAttribute("result", messagePagination);
		 }
		return "message/chatMessage";
	}
		
	
}
