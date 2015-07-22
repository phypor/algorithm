package org.kd.server.impl.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.kd.server.beans.entity.CompanyNature;
import org.kd.server.beans.entity.EnterpriseType;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.impl.base.DefultBaseService;
import org.kd.server.service.CompanyNatureService;
import org.kd.server.service.base.IBaseService;
import org.springframework.stereotype.Service;

@Service("companyNatureService")
public class CompanyNatureServiceImpl extends DefultBaseService implements
		CompanyNatureService {
	@Resource(name = "baseService")
	private IBaseService baseService;

	@Override
	public CompanyNature findByName(String name) throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("name", QueryCondition.EQ, name);
		queryConditions.add(q);
		return (CompanyNature) baseService.getSingleResult(CompanyNature.class,
				queryConditions);
	}

}
