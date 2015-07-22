package org.kd.server.impl.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.kd.server.beans.entity.EnterpriseType;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.service.EnterpriseTypeService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("enterpriseTypeService")
public class EnterpriseTypeServiceImpl extends DefultBaseService implements
		EnterpriseTypeService {
	@Resource(name = "baseService")
	private IBaseService baseService;

	public EnterpriseType findByName(String name) throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("name",
				QueryCondition.EQ, name);
		queryConditions.add(q);
		return (EnterpriseType) baseService.getSingleResult(EnterpriseType.class,
				queryConditions);
	}

	public boolean saveOrUpdate(EnterpriseType enterpriseType) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
