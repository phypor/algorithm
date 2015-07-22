package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.beans.entity.MessageTalk;
import org.kd.server.service.MessageTalkService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("messageTalkService")
public class MessageTalkServiceImpl  extends DefultBaseService implements MessageTalkService{
	@Resource(name="baseService")
	private IBaseService baseService;

	public MessageTalk findBySendTakeUserId(Long sendUserId, Long takeUserId) throws Exception {
		 	return (MessageTalk) baseService.getUniqueResultByJpql("select m from MessageTalk m where (m.user1Bean.id=?0 and m.user2Bean.id=?1) or (m.user1Bean.id=?2 and m.user2Bean.id=?3)", sendUserId,takeUserId,takeUserId,sendUserId);
	}

}
