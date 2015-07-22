package org.kd.server.web.interceptor;

import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.common.util.DesEncrypt;
import org.kd.server.service.UserService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BindingInterceptor extends HandlerInterceptorAdapter{
	@Resource(name="userService")
	private UserService userService;

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle");

		Cookie[] cookies = request.getCookies();
		Collection<String> ckied = response.getHeaders("cookie");
		User currentUser = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);

		if (currentUser==null&&cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if ((cookie.getPath() == null || cookie.getPath().equals("/" + request.getContextPath()))&& cookie.getName().equals(User.ACCESS_TOKEN_NAME)) {
					String accessToken = DesEncrypt.decrypt(DesEncrypt.USER_ACCESS_TOKEN_KEY,cookie.getValue());
					Subject userSub = SecurityUtils.getSubject();

					String name = accessToken.split("\\|")[0];
					String password = accessToken.split("\\|")[1];
										
					UsernamePasswordToken token = new UsernamePasswordToken(name, password);
					token.setRememberMe(true);

					try {
						userSub.login(token);

						User user = (User) userService.findByLoginNamePaswd(name, password);
						if(user==null){
						     user = (User) userService.findByEmailPaswd(name,password);
				    	}
						user.setLastLoginTime(new Date());
						userService.update(user);
						request.getSession().setAttribute(PublicInfoConfig.currentUser,user);

						Cookie cookie2 = new Cookie(User.ACCESS_TOKEN_NAME, DesEncrypt.encrypt(DesEncrypt.USER_ACCESS_TOKEN_KEY, user.getLoginName() + "|" + user.getPassword()));
						cookie2.setMaxAge(3600 * 24 * 365);
						cookie2.setPath("/" + request.getContextPath());
						response.addCookie(cookie2);

					} catch (AuthenticationException e) {
						token.clear();
						throw e;
					}
				}
				
				if ((cookie.getPath() == null || cookie.getPath().equals("/" + request.getContextPath()))
						&& cookie.getName().equals(User.ACCESS_TOKEN_NAME)
						&& !ckied.isEmpty()
						&& !ckied.contains(cookie.getName())) {
					response.addCookie(cookie);
				}
			}
		}
		return true;
	}
  
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object o, ModelAndView mav)  
            throws Exception {  

    	if(mav!=null&&mav.getModel().get("status")==null){
    		mav.getModel().put("status", PublicInfoConfig.SUCCESS_CODE);
    		mav.getModel().put("msg", PublicInfoConfig.SUCCESS_MSG);
    	}

        System.out.println("postHandle");  
    }  
  
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object o, Exception excptn)  
            throws Exception {  
            System.out.println("afterCompletion");  
    }
}
