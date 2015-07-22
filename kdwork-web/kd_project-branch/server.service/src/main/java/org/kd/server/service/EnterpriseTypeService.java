package org.kd.server.service;

import org.kd.server.beans.entity.EnterpriseType;
import org.kd.server.service.base.IBaseService;

public interface EnterpriseTypeService extends IBaseService {
	public EnterpriseType findByName(String  name) throws Exception;
 
	public boolean saveOrUpdate(EnterpriseType enterprise) throws Exception;
}
