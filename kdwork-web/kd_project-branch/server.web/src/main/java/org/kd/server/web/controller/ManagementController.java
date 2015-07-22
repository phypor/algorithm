package org.kd.server.web.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.common.util.DesEncrypt;
import org.kd.server.common.util.MD5;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class ManagementController {
	
	@Resource(name="userService")
	private UserService userService;

	@RequestMapping(value="/user/management",method=RequestMethod.POST)
	public String managementLogin(HttpServletRequest request,HttpServletResponse response,Model model,String loginName,String password) throws Exception{
		
		String code = (String) request.getSession()
				.getAttribute("validateCode");
		String submitCode = WebUtils.getCleanParam(request, "validateCode");
		if (StringUtils.isEmpty(submitCode)
				|| !StringUtils.equals(code.toLowerCase(),
						submitCode.toLowerCase())) {
			model.addAttribute("error", "验证码错误");
			return "management";
		}
		
		password = MD5.crypt(password);

		Subject userSub = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginName,
				password);
		token.setRememberMe(true);

		try {
			userSub.login(token);

			User user = (User) userService.findByLoginNamePaswd(loginName,password);
			if(user==null){
				     user = (User) userService.findByEmailPaswd(loginName,password);
			}
			user.setLastLoginTime(new Date());
			userService.update(user);
			request.getSession().setAttribute(PublicInfoConfig.currentUser, user);
			
			Cookie cookie = new Cookie(User.ACCESS_TOKEN_NAME,  DesEncrypt.encrypt(DesEncrypt.USER_ACCESS_TOKEN_KEY, user.getLoginName() + "|" + user.getPassword()));
			cookie.setMaxAge(3600*24*365);
			cookie.setPath("/"+request.getContextPath());
			response.addCookie(cookie);

		} catch (AuthenticationException e) {
			token.clear();
			throw e;
		}

		return "manageIndex";
	}
	
	@RequestMapping("/user/management")
	public String management(){
		
		return "management";
	}
}
