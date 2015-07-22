package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.beans.entity.Permission;
import org.kd.server.service.PermissionService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl extends DefultBaseService implements PermissionService{
	@Resource(name="baseService")
	private IBaseService baseService;

	public void addPermission(Permission permission) throws Exception {
		baseService.save(permission);
	}
  

}
