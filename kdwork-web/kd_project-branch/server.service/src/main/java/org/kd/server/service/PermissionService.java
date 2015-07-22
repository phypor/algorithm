package org.kd.server.service;

import org.kd.server.beans.entity.Permission;
import org.kd.server.service.base.IBaseService;

public interface PermissionService  extends IBaseService{
    
	  public void addPermission(Permission permission)throws Exception;
}
