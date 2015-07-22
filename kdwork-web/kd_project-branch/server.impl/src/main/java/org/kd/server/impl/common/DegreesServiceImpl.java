package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.service.DegreesService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("degreesService")
public class DegreesServiceImpl extends DefultBaseService implements
		DegreesService {
	@Resource(name = "baseService")
	private IBaseService baseService;
}
