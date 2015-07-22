package org.kd.server.service;

import java.util.List;

import org.kd.server.beans.entity.ProvinceCityArea;
import org.kd.server.service.base.IBaseService;

public interface ProvinceCityAreaService extends IBaseService{

	public List<ProvinceCityArea> findByParentId(Long parentId) throws Exception;
	public ProvinceCityArea findByName(String name) throws Exception;
	public List<ProvinceCityArea> getProvince() throws Exception;
}
