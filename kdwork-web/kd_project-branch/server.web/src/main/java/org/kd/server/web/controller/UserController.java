package org.kd.server.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.kd.server.beans.entity.ClientPlatformType;
import org.kd.server.beans.entity.Enterprise;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.entity.UserResume;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.DesEncrypt;
import org.kd.server.common.util.LogRecord;
import org.kd.server.common.util.MD5;
import org.kd.server.common.util.RegexUtil;
import org.kd.server.service.ClientPlatformTypeService;
import org.kd.server.service.EnterpriseService;
import org.kd.server.service.ImageService;
import org.kd.server.service.InfomationService;
import org.kd.server.service.MessageTalkService;
import org.kd.server.service.SectionService;
import org.kd.server.service.UserResumeService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * zmy
 * 
 * 用户中心控制器
 * 
 */
@Transactional
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "sectionService")
	private SectionService sectionService;
	@Resource(name = "clientPlatformTypeService")
	private ClientPlatformTypeService clientPlatformTypeService;
	@Resource(name = "messageTalkService")
	private MessageTalkService messageTalkService;

	@Resource(name = "imageService")
	private ImageService imageService;

	@Resource(name = "enterpriseService")
	private EnterpriseService enterpriseService;

	@Resource(name = "userResumeService")
	private UserResumeService userResumeService;

	@Resource(name = "infomationService")
	private InfomationService infomationService;
	//测试用的，用于生成批量用户
	@RequestMapping(value = "/hatman")
	public void hatman() {
		for(int j=0;j!=1000;++j){
			String strpwd="";
			String strloginname="";
			String email="";
			for(int i=0;i<6;i++){
				strloginname= strloginname+(char) (Math.random ()*26+'a');
			}
			for(int i=0;i<6;i++){
				strpwd= strpwd+( (int) (Math.random() * 10));
				}
			for(int i=0;i<9;i++){
				email= email+( (int) (Math.random() * 10+1));
				}
			User user = new User();
			user.setNickName(strloginname);
			user.setEmail(email+"@qq.com");
			user.setLoginName(strloginname);
			user.setUserType(1);
			user.setPassword(MD5.crypt(strpwd));
			try {
				userService.register(user);
				LogRecord.info(strloginname + " 注册"+ "  密码："+ strpwd);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LogRecord.error(e.getMessage());
			}
		}
	}

	// 注册用户接口
	/**
	 * @author bojiehuang@163.com
	 * @param model
	 * @param user
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Map<String, Object> register(Model model, @Valid User user,
			@RequestParam(required = false, defaultValue = "0") String PROTO_VERSION,
			BindingResult res) throws Exception {
		LogRecord.info(user.getNickName() + " 注册");
		String password = user.getPassword();
		if (!RegexUtil.isEmail(user.getEmail())) {
			throw new BusinessException(RetMsg.paramError, "邮箱地址不符合规范");
		}
		user.setPassword(MD5.crypt(user.getPassword()));
		return userService.register(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@RequestParam(required = false, defaultValue = "0") String PROTO_VERSION,
			String loginName,
			String password,
			long clientPlatformType,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "") String validateCode,
			Model model) throws Exception {
		LogRecord.info("login....\n" + loginName + " " + " "
				+ clientPlatformType);
	
		if (!request.getRequestURI().endsWith(".json")) {
			String submitCode = WebUtils.getCleanParam(request, "validateCode");
			if (StringUtils.isEmpty(submitCode)
					|| !StringUtils.equals(validateCode.toLowerCase(),
							submitCode.toLowerCase())) {
				return "login";
			}
		}
		
		if(!PROTO_VERSION.equals("0")){
		password =  DesEncrypt.decrypt(DesEncrypt.USER_ACCESS_TOKEN_KEY, password);
		}
		
		password = MD5.crypt(password);

		Subject userSub = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginName,
				password);
		token.setRememberMe(true);
		System.out.println("用户名："+loginName+"用户密码："+password);
		try {
			userSub.login(token);

			User user = (User) userService.findByLoginNamePaswd(loginName,
					password);
			if (user == null) {
				user = (User) userService.findByEmailPaswd(loginName, password);
			}
			user.setClientPlatformType(clientPlatformTypeService.getById(
					ClientPlatformType.class, clientPlatformType));
			user.setLastLoginTime(new Date());
			userService.update(user);
			request.getSession().setAttribute(PublicInfoConfig.currentUser,
					user);

			Cookie cookie = new Cookie(User.ACCESS_TOKEN_NAME,
					DesEncrypt.encrypt(DesEncrypt.USER_ACCESS_TOKEN_KEY,
							user.getLoginName() + "|" + user.getPassword()));
			cookie.setMaxAge(3600 * 24 * 365);
			cookie.setPath("/" + request.getContextPath());
			response.addCookie(cookie);

			if (request.getRequestURI().endsWith(".json")) {
				Map<String, Object> map = new HashMap<String, Object>();

				if (user.getUserType() != PublicInfoConfig.consumerUser) {

					Enterprise enterprise = enterpriseService.findByUserId(user
							.getId());
					if (enterprise != null && enterprise.getUser() != null) {
						enterprise.setUserVo(new UserVo(enterprise.getUser()
								.getId(), enterprise.getUser().getNickName(),
								enterprise.getUser().getHeadImage(), enterprise
										.getUser().getSex(), enterprise
										.getUser().getEasemobName()));
					}
					map.put("enterprise", enterprise);
				} else {

					UserResume userResume = userResumeService.findByUserId(user
							.getId());
					map.put("userResume", userResume);
				}

				map.put("user", user);

				model.addAttribute("result", map);
				
			}
			
			return "PC/index";
		} catch (AuthenticationException e) {
			token.clear();
			throw e;
		}

	}
	@RequestMapping(value="/loginjsp")
	public String login(){
		return "login";
	}
	@RequestMapping(value="/registerjsp")
	public String register(){
		return "register";
	}
	//刷新用户信息
	@RequiresAuthentication
	@ResponseBody
	@RequestMapping(value = "/freshenUserInfo", method = RequestMethod.POST)
	public Model freshenUserInfo(Model model, HttpServletRequest request)
			throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		Map<String, Object> map = new HashMap<String, Object>();

		if (user.getUserType() != PublicInfoConfig.consumerUser) {

			Enterprise enterprise = enterpriseService
					.findByUserId(user.getId());
			if (enterprise != null && enterprise.getUser() != null) {
				enterprise.setUserVo(new UserVo(enterprise.getUser().getId(),
						enterprise.getUser().getNickName(), enterprise
								.getUser().getHeadImage(), enterprise.getUser()
								.getSex(), enterprise.getUser()
								.getEasemobName()));
			}
			map.put("enterprise", enterprise);
		} else {

			UserResume userResume = userResumeService
					.findByUserId(user.getId());
			map.put("userResume", userResume);
		}

		map.put("user", user);

		model.addAttribute("result", map);
		return model;
	}

	// 退出登录接口
	@ResponseBody
	@RequestMapping("/logout")
	public Model logout(Model model, HttpServletRequest request)
			throws Exception {
		Subject user = SecurityUtils.getSubject();
		user.logout();

		Cookie[] cookies = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getPath() != null
						&& cookie.getPath().equals(
								"/" + request.getContextPath())
						&& cookie.getName().equals(User.ACCESS_TOKEN_NAME)) {
					cookie.setValue(null);
					cookie.setMaxAge(0);
				}
			}
		}
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/searchUserVoListByEasemobNames", method = RequestMethod.POST)
	public Model searchUserVoListByEasemobNames(
			@RequestBody String[] easemobNames, Model model) throws Exception {
		List<UserVo> userVoList = new ArrayList<UserVo>();
		if (easemobNames != null && easemobNames.length > 0) {
			for (String easemobName : easemobNames) {
				User user = userService.getUserByEasemobName(easemobName);
				if (user != null) {
					userVoList.add(new UserVo(user.getId(), user.getNickName(),
							user.getHeadImage(), user.getSex(), user
									.getEasemobName()));
				}
			}
		}
		model.addAttribute("result", userVoList);
		return model;
	}

}
