package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.service.AppClientInfoService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.beans.entity.AppClientInfo;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("appClientInfoService")
public class AppClientInfoServiceImpl extends DefultBaseService implements AppClientInfoService{
	@Resource(name="baseService")
	private IBaseService baseService;

	@Override
	public AppClientInfo findByChannelPlatformTypeVersion(Long appChannelTypeId, double versionCode,Long appPlatformTypeId) throws Exception {
		return (AppClientInfo) baseService.getUniqueResultByJpql("select a from AppClientInfo a where (a.appChannelType.id=1 or a.appChannelType.id=?0) and a.versionCode>?1 and a.appPlatformType.id=?2 order by a.appChannelType.id desc",appChannelTypeId,versionCode,appPlatformTypeId);
	}
}
