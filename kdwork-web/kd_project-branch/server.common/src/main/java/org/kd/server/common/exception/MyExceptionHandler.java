package org.kd.server.common.exception;

import java.net.BindException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.common.util.LogRecord;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionHandler implements HandlerExceptionResolver{

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex) {
		
		LogRecord.error(ex.getMessage());
		
		 Map<String, Object> model = new HashMap<String, Object>();  
		   if(!request.getRequestURI().endsWith(".json")){
		      model.put("exception", ex);
		   }
  
	        // 根据不同错误转向不同页面  
		     if(ex instanceof UnauthenticatedException){
		    	 model.put("status", RetMsg.haveNotLogined);
		    	 model.put("msg", RetMsg.haveNotLoginedMsg);
		    	 return new ModelAndView("errors/error", model); 
		    	 
		     }else if(ex instanceof UnauthorizedException){
		    	 model.put("status", RetMsg.haveNotRoleAuthorized);
		    	 model.put("msg", RetMsg.haveNotRoleAuthorizedMsg);
		    	 return new ModelAndView("errors/error", model); 
		    	 
		     }else if(ex instanceof UnknownAccountException) {  
		    	 model.put("status", RetMsg.loginNameUnknow);
		    	 model.put("msg", RetMsg.loginNameUnknowMsg);
	        	return new ModelAndView("errors/error", model);  
	        	
	        }else if(ex instanceof IncorrectCredentialsException) {  
	        	model.put("status", RetMsg.passwordError);
		    	 model.put("msg", RetMsg.passwordErrorMsg);
	        	return new ModelAndView("errors/error", model);  
	        	
	        }else if(ex instanceof LockedAccountException) {  
	        	model.put("status", RetMsg.lockedUser);
		    	 model.put("msg", RetMsg.lockedUserMsg);
	        	return new ModelAndView("errors/error", model);  
	        	
	        }else if(ex instanceof ExcessiveAttemptsException) {  
	        	model.put("status", RetMsg.excessiveError);
		    	 model.put("msg", RetMsg.excessiveErrorMsg);
	        	return new ModelAndView("errors/error", model);  
	        	
	        }else if(ex instanceof AuthenticationException) {  
	        	model.put("status", RetMsg.haveNotRoleAuthorized);
		    	model.put("msg", RetMsg.haveNotRoleAuthorizedMsg);
	        	return new ModelAndView("errors/error", model);  
	        	
	        }else if(ex instanceof BindException){
	        	 model.put("status",RetMsg.registerBeanRegError);
				 model.put("msg",RetMsg.registerBeanRegErrorMsg);
			    return new ModelAndView("errors/error", model);  
				  
	        } else if(ex instanceof BusinessException){
	        	 model.put("status",((BusinessException) ex).getCode());
				 model.put("msg",ex.getMessage());
			    return new ModelAndView("errors/error", model);  
	        	
	        }else {  
	        	 model.put("status", RetMsg.unknowException);
		    	 model.put("msg", ex.getMessage());
	            return new ModelAndView("errors/error", model);  
	        }  

	}

}
