package org.kd.server.service;

import org.kd.server.beans.entity.Role;
import org.kd.server.service.base.IBaseService;

public interface RoleService extends IBaseService{

	public void addRole(Role role)throws Exception;
	
}
