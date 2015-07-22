package org.kd.server.impl.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.kd.server.service.CollectionService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.beans.entity.Collection;
import org.kd.server.beans.entity.CompanyNature;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("collectionService")
public class CollectionServiceImpl extends DefultBaseService implements CollectionService{
	@Resource(name = "baseService")
	private IBaseService baseService;
	@Override
	public List<Collection> findByUserId(Long id) throws Exception {
	return null;
		/*	List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("user.id", QueryCondition.EQ, id);
		queryConditions.add(q);*/
	}

}
