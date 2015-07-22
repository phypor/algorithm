package org.kd.server.service;

import java.util.List;
import java.util.Map;

import org.kd.server.beans.entity.FriendCircle;
import org.kd.server.beans.entity.User;
import org.kd.server.service.base.IBaseService;

public interface UserService extends IBaseService{
	
	public User getByLoginName(String loginName)throws Exception;
	
	public User getByEmail(String email)throws Exception;

	public Map<String, Object> register(User user)throws Exception;
	
	public void addUser(User user)throws Exception;
	
	public User findByLoginNamePaswd(String loginName,String password)throws Exception;
	
	public User findByEmailPaswd(String email,String password)throws Exception;
	
	public List<User> findEnterpriseAdmissionUser() throws Exception ;
	
	public List<FriendCircle> findFriendCircleByUserId(Long userId)throws Exception ;

	public User getUserByEasemobName(String easemobName)throws Exception ;
}
