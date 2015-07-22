package org.kd.server.service;

import org.kd.server.beans.entity.AppClientInfo;
import org.kd.server.service.base.IBaseService;

public interface AppClientInfoService extends IBaseService{
	
	//查询公共包 或 指定渠道的最新版本包
		public AppClientInfo findByChannelPlatformTypeVersion(Long appChannelTypeId,double versionCode,Long appPlatformTypeId)throws Exception;

}
