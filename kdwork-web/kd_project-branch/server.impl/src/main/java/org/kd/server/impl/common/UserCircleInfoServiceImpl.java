package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.beans.entity.FriendCircle;
import org.kd.server.service.UserCircleInfoService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("userCircleInfoService")
public class UserCircleInfoServiceImpl extends DefultBaseService implements UserCircleInfoService{
	@Resource(name="baseService")
	private IBaseService baseService;

	public FriendCircle findFriendCircleByUserId(Long userId) throws Exception {
		return (FriendCircle) baseService.getUniqueResultByJpql("select u.friendCircle from UserCircleInfo u where u.originalUser.id=?0", userId);
	}

	public int findCountByLastId(Long lastId,String ids) throws Exception {
		// TODO Auto-generated method stub
		Long count =  (Long) baseService.getUniqueResultByJpql("select count(u.id) from UserCircleInfo u where u.id>?0 and u.friendCircle.id in ("+ids+")", lastId);
		return count==null?0:count.intValue();
	}

}
