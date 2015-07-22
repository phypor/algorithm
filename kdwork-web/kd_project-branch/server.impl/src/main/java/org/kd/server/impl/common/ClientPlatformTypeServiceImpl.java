package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.impl.base.DefultBaseService;
import org.kd.server.service.ClientPlatformTypeService;
import org.kd.server.service.base.IBaseService;
import org.springframework.stereotype.Service;

@Service("clientPlatformTypeService")
public class ClientPlatformTypeServiceImpl extends DefultBaseService implements
		ClientPlatformTypeService {
	@Resource(name = "baseService")
	private IBaseService baseService;
}
