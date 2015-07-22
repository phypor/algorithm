package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.service.ApplyForFriendService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.beans.entity.ApplyForFriend;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("applyForFriendService")
public class ApplyForFriendServiceImpl extends DefultBaseService implements ApplyForFriendService{
	@Resource(name="baseService")
	private IBaseService baseService;

	@Override
	public ApplyForFriend findByApplyExaminationUserId(Long applyUserId,
			Long examinationUserId) throws Exception {
		// TODO Auto-generated method stub
		return (ApplyForFriend) baseService.getUniqueResultByJpql("select a from ApplyForFriend a where a.applyUser.id=?0 and a.examinationUser.id=?1 and a.approval=0" ,applyUserId,examinationUserId);
		
	}
}
