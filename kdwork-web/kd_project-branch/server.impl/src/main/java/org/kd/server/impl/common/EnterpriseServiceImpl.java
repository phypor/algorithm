package org.kd.server.impl.common;

/**
 * @author bojie
 */
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.kd.server.beans.entity.Enterprise;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.service.EnterpriseService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("enterpriseService")
public class EnterpriseServiceImpl extends DefultBaseService implements
		EnterpriseService {
	@Resource(name = "baseService")
	private IBaseService baseService;

	public Enterprise findByUserId(Long userId) throws Exception {
		QueryCondition q = new QueryCondition("user.id", QueryCondition.EQ,
				userId);
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(q);
		return (Enterprise) baseService.getSingleResult(Enterprise.class,
				queryConditions);
	}

 public List<Enterprise> getEnterpriseByJpql(String jqpl) throws Exception{
 return  baseService.getByJpql(jqpl, Enterprise.class); //"select p from Enterprise p where p.businessLicenseCheck=0"
 }
	
	public boolean saveOrUpdate(Enterprise enterprise) throws Exception {
		return true;
	}

	@Override
	public Enterprise findByHash(String hash) throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("hash", QueryCondition.EQ, hash);
		queryConditions.add(q);
		return (Enterprise) baseService.getSingleResult(Enterprise.class,
				queryConditions);
	}

}
