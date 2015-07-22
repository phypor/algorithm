package org.kd.server.service;

import org.kd.server.beans.entity.QuantityScale;
import org.kd.server.service.base.IBaseService;

public interface QuantityScaleService extends IBaseService {
	public QuantityScale getByName(String name) throws Exception;
}
