package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.beans.entity.ValidateUserEmailLog;
import org.kd.server.service.ValidateUserEmailLogService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("validateUserEmailLogService")
public class ValidateUserEmailLogServiceImpl extends DefultBaseService implements ValidateUserEmailLogService{
	@Resource(name="baseService")
	private IBaseService baseService;

	@Override
	public ValidateUserEmailLog findOneByTypeUserId(int type,Long userId) throws Exception {
		return (ValidateUserEmailLog) baseService.getUniqueResultByJpql("select v from ValidateUserEmailLog v where v.user.id = ?0 and v.type = ?1",userId,type);
	}
}
