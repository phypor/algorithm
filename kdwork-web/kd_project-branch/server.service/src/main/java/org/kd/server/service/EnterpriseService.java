package org.kd.server.service;

import org.kd.server.beans.entity.Enterprise;
import org.kd.server.service.base.IBaseService;

public interface EnterpriseService extends IBaseService {

	public Enterprise findByUserId(Long userId) throws Exception;
	public Enterprise findByHash(String hash) throws Exception;
	public boolean saveOrUpdate(Enterprise enterprise) throws Exception;
}
