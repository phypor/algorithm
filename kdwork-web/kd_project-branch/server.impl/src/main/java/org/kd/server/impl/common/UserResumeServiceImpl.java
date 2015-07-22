package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.beans.entity.UserResume;
import org.kd.server.service.UserResumeService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("userResumeService")
public class UserResumeServiceImpl  extends DefultBaseService implements UserResumeService{
	
	@Resource(name="baseService")
	private IBaseService baseService;

	public UserResume findByUserId(Long userId) throws Exception {
	 return (UserResume) baseService.getUniqueResultByJpql("select u from UserResume u where u.user.id=?0", userId);
	}

}
