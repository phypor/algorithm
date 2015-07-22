package org.kd.server.service;

import org.kd.server.beans.entity.MessageTalk;
import org.kd.server.service.base.IBaseService;

public interface MessageTalkService extends IBaseService{
    
	    public MessageTalk findBySendTakeUserId(Long sendUserId,Long takeUserId) throws Exception ;
}
