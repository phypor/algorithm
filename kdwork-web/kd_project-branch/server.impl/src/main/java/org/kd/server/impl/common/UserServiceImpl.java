package org.kd.server.impl.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.kd.server.beans.entity.FriendCircle;
import org.kd.server.beans.entity.Role;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.EasemobUtil;
import org.kd.server.common.util.JsonUtil;
import org.kd.server.common.util.MD5;
import org.kd.server.service.RoleService;
import org.kd.server.service.UserService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends DefultBaseService implements UserService{

	@Resource(name="baseService")
	private IBaseService baseService;
	
	@Resource(name="roleService")
	private RoleService roleService;
	
	public Map<String, Object> register(User user)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RetMsg.successStatus);
		map.put("msg", RetMsg.successStatusMsg);

    	user.setEasemobName(MD5.crypt(user.getLoginName() + "ujob" + new Date().getTime()));
    	user.setPushEnable(true);
    	user.setUserType((user.getUserType()>0&&user.getUserType()<5)?user.getUserType():1);
    	
		User us = (User) baseService.getUniqueResultByJpql("from User as o where o.loginName=?0", user.getLoginName());
		if(us!=null){
			map.put("status", String.valueOf(RetMsg.registerExistsLoginName));
			map.put("msg", RetMsg.registerExistsLoginNameMsg);
			return map;
		}
		
		us = (User) baseService.getUniqueResultByJpql("from User as o where o.email=?0", user.getEmail());
		if(us!=null){
			map.put("status", String.valueOf(RetMsg.registerExistsEmail));
			map.put("msg", RetMsg.registerExistsEmailMsg);
			return map;
		}
		
		us = (User) baseService.getUniqueResultByJpql("from User as o where o.nickName=?0", user.getNickName());
		if(us!=null){
			map.put("status", String.valueOf(RetMsg.registerExistsNickNameError));
			map.put("msg", RetMsg.registerExistsNickNameErrorMsg);
			return map;
		}

		String roleName = "";
		switch (user.getUserType()) {
		case 1:
			roleName = "普通用户角色";
			break;
		case 2:
			roleName = "企业用户角色";					
			break;
		case 3:
			roleName = "招生办用户角色";
			break;
		default:
			roleName = "普通用户角色";
			break;
		}
		
		List<Role> roleList = roleService.getByJpql("select r from Role r where r.name=?0", roleName);
		user.setRoles(roleList);
		user.setRegisterTime(new Date());
		user.setIsValidateEmail(false);
		user.setEasemobKey(MD5.crypt(user.getPassword()+"ujob"+user.getId()));
         baseService.save(user);
         
         if(StringUtils.isEmpty(user.getNickName())){
              user.setNickName("游客"+user.getId());
         }
         baseService.update(user);
         
         //同步用户到环信
         String retContent = EasemobUtil.registerEasemobUser(user.getEasemobName(), user.getEasemobKey());
         
         System.out.println(retContent);
         if(JsonUtil.json2Bean(retContent, Map.class).get("error")!=null){
        	 throw new BusinessException(RetMsg.registerEasemobUserError, RetMsg.registerEasemobUserErrorMsg);
         }
         
		return map;
	}

	public void addUser(User user)throws Exception{
		baseService.save(user);
	}

	public User findByLoginNamePaswd(String loginName, String password)
			throws Exception {
		return (User) baseService.getUniqueResultByJpql("from User as o where o.loginName=?0 and o.password=?1",loginName,password);
	}

	public User getByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return (User) baseService.getUniqueResultByJpql("from User as o where o.email=?0", email);
	}

	public List<User> findEnterpriseAdmissionUser() throws Exception {
		// TODO Auto-generated method stub
		return baseService.getByJpql("select u from User u where u.userType="+PublicInfoConfig.employMentUser+" or u.userType="+PublicInfoConfig.enterpriseUser);
	}

	public List<FriendCircle> findFriendCircleByUserId(Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		return  baseService.getByJpql("select u.friendCircles from User u where u.id=?0",userId);
	}

	public User getByLoginName(String loginName) throws Exception {
		// TODO Auto-generated method stub
		return (User) baseService.getUniqueResultByJpql("from User as o where o.loginName=?0",loginName);
	}

	@Override
	public User findByEmailPaswd(String email, String password)
			throws Exception {
		// TODO Auto-generated method stub
		return (User) baseService.getUniqueResultByJpql("from User as o where o.email=?0 and o.password=?1",email,password);
	}
	@Override
	public User getUserByEasemobName(String easemobName) throws Exception {
		// TODO Auto-generated method stub
		return  (User) baseService.getUniqueResultByJpql("from User as o where o.easemobName=?0",easemobName);
	}
	
}
