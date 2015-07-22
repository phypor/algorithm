package org.kd.server.impl.common;

import javax.annotation.Resource;

import org.kd.server.service.TrainingInfoService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;
@Service("trainingInfoService")
public class TrainingInfoServiceImpl extends DefultBaseService implements TrainingInfoService{

	@Resource(name="baseService")
	private IBaseService baseService;
}
