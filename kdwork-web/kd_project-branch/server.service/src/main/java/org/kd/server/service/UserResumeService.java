package org.kd.server.service;

import org.kd.server.beans.entity.UserResume;
import org.kd.server.service.base.IBaseService;

public interface UserResumeService extends IBaseService{

	public UserResume findByUserId(Long userId)throws Exception;
}
