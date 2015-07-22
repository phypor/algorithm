package org.kd.server.service;

import org.kd.server.beans.entity.ValidateUserEmailLog;
import org.kd.server.service.base.IBaseService;

public interface ValidateUserEmailLogService extends IBaseService{

	public ValidateUserEmailLog findOneByTypeUserId(int type,Long userId)throws Exception ;
	
}
