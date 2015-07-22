package org.kd.server.service;

import org.kd.server.beans.entity.FriendCircle;
import org.kd.server.service.base.IBaseService;

public interface UserCircleInfoService extends IBaseService{

	public FriendCircle findFriendCircleByUserId(Long userId) throws Exception;
	
	public int findCountByLastId(Long lastId,String ids)throws Exception;
}
