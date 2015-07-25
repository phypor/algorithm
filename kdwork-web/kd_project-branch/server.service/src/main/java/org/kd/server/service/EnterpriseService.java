package org.kd.server.service;

import java.util.List;

import org.kd.server.beans.entity.Enterprise;
import org.kd.server.service.base.IBaseService;

public interface EnterpriseService extends IBaseService {
	public List<Enterprise> getEnterpriseByJpql(String jqpl) throws Exception;
	public Enterprise findByUserId(Long userId) throws Exception;
	public Enterprise findByHash(String hash) throws Exception;
	public boolean saveOrUpdate(Enterprise enterprise) throws Exception;
}
