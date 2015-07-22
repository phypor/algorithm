package org.kd.server.service;

import org.kd.server.beans.entity.RecruitInfo;
import org.kd.server.service.base.IBaseService;

public interface RecruitInfoService extends IBaseService {

	public RecruitInfo findByHash(String hash) throws Exception;
}
