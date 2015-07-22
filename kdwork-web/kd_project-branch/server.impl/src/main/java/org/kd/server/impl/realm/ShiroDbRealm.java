package org.kd.server.impl.realm;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.kd.server.beans.entity.Permission;
import org.kd.server.beans.entity.Role;
import org.kd.server.beans.entity.User;
import org.kd.server.service.UserService;

public class ShiroDbRealm extends AuthorizingRealm{

	@Resource(name="userService")
	private UserService userService;

	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals){
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取当前登录的用户名
		String account = (String) super.getAvailablePrincipal(principals);
		
		List<String> roles = new ArrayList<String>();  
		List<String> permissions = new ArrayList<String>();
		User user = null;
		try {
			user = userService.getByLoginName(account);
			if(user==null){
				user = userService.getByEmail(account);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user != null){
			if (user.getRoles() != null && user.getRoles().size() > 0) {
				for (Role role : user.getRoles()) {
					roles.add(role.getRole());
					if (role.getPermissions() != null && role.getPermissions().size() > 0) {
						for (Permission pmss : role.getPermissions()) {
							if(!StringUtils.isEmpty(pmss.getPermission())){
								permissions.add(pmss.getPermission());
							}
						}
					}
				}
			}
		}else{
			throw new AuthorizationException();
		}
		//给当前用户设置角色
		info.addRoles(roles);
		//给当前用户设置权限
        info.addStringPermissions(permissions); 
		return info;
		
	}

	/**
	 *  认证回调函数,登录时调用.
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = null;
		try {
			user = userService.getByLoginName(token.getUsername());
			if(user==null){
				user = userService.getByEmail(token.getUsername());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getLoginName(), user
					.getPassword(), getName());
		} else {
			return null;
		}
	}
}

