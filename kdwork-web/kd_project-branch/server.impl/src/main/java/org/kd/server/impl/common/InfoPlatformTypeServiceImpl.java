package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.impl.base.DefultBaseService;
import org.kd.server.service.InfoPlatformTypeService;
import org.kd.server.service.base.IBaseService;
import org.springframework.stereotype.Service;

@Service("infoPlatformTypeService")
public class InfoPlatformTypeServiceImpl extends DefultBaseService implements
		InfoPlatformTypeService {
	@Resource(name = "baseService")
	private IBaseService baseService;
}
