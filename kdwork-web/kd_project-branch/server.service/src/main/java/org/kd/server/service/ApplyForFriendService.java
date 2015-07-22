package org.kd.server.service;

import org.kd.server.beans.entity.ApplyForFriend;
import org.kd.server.service.base.IBaseService;

public interface ApplyForFriendService extends IBaseService{
	
	//通过申请人和审批人ID查找好友申请信息
	public ApplyForFriend findByApplyExaminationUserId(Long applyUserId,Long examinationUserId)throws Exception ;

}
