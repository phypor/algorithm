package org.kd.server.web.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.entity.ValidateUserEmailLog;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.*;
import org.kd.server.service.UserService;
import org.kd.server.service.ValidateUserEmailLogService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Transactional
@Controller
@RequestMapping(value = "/user")
public class UserSecondaryController {
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="validateUserEmailLogService")
	private ValidateUserEmailLogService validateUserEmailLogService;
	
	@Inject
	private MailSender mailSender;

	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value="/changeUserInfo",method=RequestMethod.POST)
	public Model changeUserInfo(
			@RequestParam(required=false,defaultValue = "") String changeContent,
			@RequestParam(required=false,defaultValue = "true") boolean isOpen,
			int type,Model model,HttpServletRequest request) throws Exception{
	    
		User currentUser = (User)request.getSession().getAttribute(PublicInfoConfig.currentUser);

		if(changeContent==null
				||((type>=2&&type<=4)&&!changeContent.toString().matches("^[\\s\\S]{0,300}$"))){
			throw new BusinessException(RetMsg.paramError,"要修改的内容不能超出大小");
		}
		
		User user = userService.getById(User.class, currentUser.getId());
		
		switch (type) {
		case 1:   //修改用户昵称
			if(!changeContent.toString().matches("^[\\s\\S]{0,30}$"))
		    	throw new BusinessException(RetMsg.paramError,"要修改的内容不能超出30个字符");
			
			user.setNickName(changeContent.toString());
			userService.update(user);
			
			String retContent = EasemobUtil.resetUserNickName(user.getEasemobName(), user.getPassword());
	         if(JsonUtil.json2Bean(retContent,Map.class).get("error")!=null){
	        	 throw new BusinessException(RetMsg.updateEasemobUserNickNameError, RetMsg.updateEasemobUserNickNameErrorMsg);
	         }
	         return model;
		case 2: //修改个性签名		
			user.setIndiviSign(changeContent.toString());
			break;
		case 3:  //修改用户头像			
			user.setHeadImage(changeContent.toString());
			break;
		case 4:  //修改个人中心背景图
			user.setBackgroundImage(changeContent.toString());
			break;
		case 5: //修改 是否有消息震动
			user.setMessageShock(isOpen);
			break;
		case 6: //修改 是否有消息声音
			user.setMessageVoice(isOpen);
			break;
		case 7: //修改 是否显示消息通知详情
			user.setMessageNoticeShow(isOpen);
			break;
		case 8: //修改  是否朋友圈消息提醒
			user.setFirnedCircleRenewRemind(isOpen);
			break;
		case 9: //修改  是否退出后推送
			user.setLogoutStillPush(isOpen);
			break;
		case 10: //修改  添加好友 是否需要验证
			user.setAddMeNeedValid(isOpen);
			break;
		case 11: //修改 是否对附近的人 可见
			user.setShowMeVicinity(isOpen);
			break; 
		case 12: //修改  是否允许陌生人查看我的简历
			user.setStrangerLookResume(isOpen);
			break;
		case 13: //修改 是否以省流量方式显示图片
			user.setMinTrafficImg(isOpen);
			break;
		case 14: //修改 用户密码
			user.setPassword(MD5.crypt(changeContent.toString().trim()));
			user.setEasemobKey(MD5.crypt(user.getPassword() + "ujob"	+ user.getId()));
			userService.update(user);
			
			String retC = EasemobUtil.resetUserPaswd(user.getEasemobName(), user.getEasemobKey());
	         if(JsonUtil.json2Bean(retC, Map.class).get("error")!=null){
	        	 throw new BusinessException(RetMsg.updateEasemobUserPasswdError, RetMsg.updateEasemobUserPasswdErrorMsg);
	         }
	         return model;
		default:
			break;
		}
		userService.update(user);
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="/validateParam" ,method=RequestMethod.POST)
	public Map<String,Object> validateParam(String validParam,int type) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg",PublicInfoConfig.SUCCESS_MSG);
		
		if(StringUtils.isEmpty(validParam)){
			map.put("status",RetMsg.paramIsEmpty);
			map.put("msg",RetMsg.paramIsEmptyMsg);
			return map;
		}
		
		User user = null;
		
		switch (type) {
		case 1:	 
			user = userService.getByLoginName(validParam);
			 if(user!=null){
				 map.put("status",RetMsg.registerExistsLoginName);
				 map.put("msg",RetMsg.registerExistsLoginNameMsg);
				 return map;
			 }
			break;
        case 2:
        	 user = userService.getByEmail(validParam);
			 if(user!=null){
				 map.put("status",RetMsg.registerExistsEmail);
				 map.put("msg",RetMsg.registerExistsEmailMsg);
				 return map;
			 }
			break;
		default:
			break;
		}
		
		return map;
	}
	
	@Transactional
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value="/bindingEmail",method=RequestMethod.POST)
	public Model bindingEmail(String email,Model model,HttpServletRequest request) throws Exception{
		if(StringUtils.isEmpty(email)||
				!email.matches(PublicInfoConfig.emailRegex)){
			throw new BusinessException(RetMsg.emailError, RetMsg.emailErrorMsg);
		}
		User currentUser = (User)request.getSession().getAttribute(PublicInfoConfig.currentUser);
		
		String secretKey = UUID.randomUUID().toString(); // 秘钥
		
		long outTime = DateUtil.addMinutesToDate(new Date(), 30).getTime(); //过期时间
		
		String sid =  MD5.crypt(currentUser.getId()+outTime+secretKey);  //数字签名
		
		int validateCode = (int)((Math.random()*9+1)*100000);
		
		ValidateUserEmailLog validateUserEmailLog = validateUserEmailLogService.findOneByTypeUserId(PublicInfoConfig.BINDING_EMAIL,currentUser.getId());
		if(validateUserEmailLog==null){
			validateUserEmailLog = new ValidateUserEmailLog();
		}

		validateUserEmailLog.setOutTime(outTime);
		validateUserEmailLog.setSendTime(new Date());
		validateUserEmailLog.setUser(currentUser);
		validateUserEmailLog.setValidateCode(validateCode);
		validateUserEmailLog.setValidateKey(secretKey);
		validateUserEmailLog.setEmail(email);
		validateUserEmailLog.setType(PublicInfoConfig.BINDING_EMAIL);
		validateUserEmailLogService.save(validateUserEmailLog);
		
		String url = "http://"+request.getServerName()+request.getContextPath()+"/user/validateBindingEmail?sid="+sid+"&uid="+currentUser.getId();
		
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("nickName", currentUser.getNickName());
        map.put("validateCode",validateCode);
		map.put("url", url);
		map.put("sendTime", DateUtil.formatDateByString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
	    mailSender.send(map, "邮箱绑定验证<UJOB快点优职服务平台>", "bindingUserEmail.ftl",email);
		
		return model;
	}
	
	@RequestMapping("/validateBindingEmail")
	public String validateBindingEmail(String sid,Long uid,HttpServletRequest request) throws Exception{

		ValidateUserEmailLog validateUserEmailLog = validateUserEmailLogService.findOneByTypeUserId(PublicInfoConfig.BINDING_EMAIL,uid);
		if(validateUserEmailLog!=null&&validateUserEmailLog.getOutTime()>new Date().getTime()&&MD5.crypt(uid+validateUserEmailLog.getOutTime()+validateUserEmailLog.getValidateKey()).equals(sid)){
			User user = userService.getById(User.class, validateUserEmailLog.getUser().getId());
			user.setEmail(validateUserEmailLog.getEmail());
			user.setIsValidateEmail(true);
			userService.update(user);
			return "validateEmail/validBindingSucc";
		}else{
			return "validateEmail/validateBindingEmailfail";
		}

	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="/resetPasswordByEmail",method=RequestMethod.POST)
	public Model resetPasswordByEmail(String email,Model model,HttpServletRequest request) throws Exception{
		if(StringUtils.isEmpty(email)||
				!email.matches(PublicInfoConfig.emailRegex)){
			throw new BusinessException(RetMsg.emailError, RetMsg.emailErrorMsg);
		}
		
		User user = userService.getByEmail(email);
		if(user==null){
			throw new BusinessException(RetMsg.emailNoBindingError, RetMsg.emailNoBindingErrorMsg);
		}
		
		int validateCode = (int)((Math.random()*9+1)*100000);
		
		String secretKey = UUID.randomUUID().toString(); // 秘钥
		
		long outTime = DateUtil.addMinutesToDate(new Date(), 30).getTime(); //过期时间
		
		String sid =  MD5.crypt(validateCode+outTime+secretKey);  //数字签名
		
		ValidateUserEmailLog validateUserEmailLog = new ValidateUserEmailLog();
		validateUserEmailLog.setOutTime(outTime);
		validateUserEmailLog.setSendTime(new Date());
		validateUserEmailLog.setValidateCode(validateCode);
		validateUserEmailLog.setValidateKey(secretKey);
		validateUserEmailLog.setEmail(email);
		validateUserEmailLog.setType(PublicInfoConfig.RESET_PASSWD_BY_EMAIL);
		validateUserEmailLogService.save(validateUserEmailLog);
		
		String url = "http://"+request.getServerName()+request.getContextPath()+"/user/resetPasswordByEmail?sid="+sid+"&uid="+validateUserEmailLog.getId();
		
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("validateCode",validateCode);
		map.put("url", url);
		map.put("sendTime", DateUtil.formatDateByString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
	    mailSender.send(map, "重置用户密码验证<UJOB快点优职服务平台>", "resetPasswdByEmail.ftl",email);
		
		return model; 
	}
	
	@RequestMapping("/resetPasswordByEmail")
	public String resetPasswordByEmail(String sid,Long uid,HttpServletRequest request,Model model) throws Exception{

		ValidateUserEmailLog validateUserEmailLog = validateUserEmailLogService.getById(ValidateUserEmailLog.class, uid);
		if(validateUserEmailLog!=null&&validateUserEmailLog.getOutTime()>new Date().getTime()){
			model.addAttribute("sid", sid);
			model.addAttribute("uid", uid);
			return "resetPasswd/resetPasswd";
		}else{
			return "resetPasswd/resetPasswdFail";
		}

	}
	
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	public String resetPassword(String sid,Long uid,int validateCode,String password,Model model,HttpServletRequest request) throws Exception{
		ValidateUserEmailLog validateUserEmailLog = validateUserEmailLogService.getById(ValidateUserEmailLog.class, uid);
		if(validateUserEmailLog!=null&&validateUserEmailLog.getOutTime()>new Date().getTime()
				&&MD5.crypt(validateUserEmailLog.getValidateCode()+validateUserEmailLog.getOutTime()+validateUserEmailLog.getValidateKey()).equals(sid)&&validateUserEmailLog.getValidateCode()==validateCode){
			
			User user = userService.getByEmail(validateUserEmailLog.getEmail());
			user.setPassword(MD5.crypt(password));
			userService.update(user);
			model.addAttribute("newPasswd", password);
			return "resetPasswd/resetPasswdSucc";
		}else{
			return "resetPasswd/resetPasswdFail";
		}
				
	}
}
