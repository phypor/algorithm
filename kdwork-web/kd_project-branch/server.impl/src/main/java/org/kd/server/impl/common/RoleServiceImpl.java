package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.beans.entity.Role;
import org.kd.server.service.RoleService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl  extends DefultBaseService implements RoleService{
	@Resource(name="baseService")
	private IBaseService baseService;

	public void addRole(Role role) throws Exception {
		baseService.save(role);
	}

}
