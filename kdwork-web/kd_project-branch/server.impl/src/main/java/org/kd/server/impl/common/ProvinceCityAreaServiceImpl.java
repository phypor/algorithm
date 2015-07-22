package org.kd.server.impl.common;
/**
 * 
 * @author bojie
 * 
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.kd.server.beans.entity.ProvinceCityArea;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.service.ProvinceCityAreaService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("provinceCityAreaService")
public class ProvinceCityAreaServiceImpl extends DefultBaseService implements
		ProvinceCityAreaService {
	@Resource(name = "baseService")
	private IBaseService baseService;

	public List<ProvinceCityArea> findByParentId(Long parentId)
			throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("provinceCityArea.id", QueryCondition.EQ,
				parentId);
		queryConditions.add(q);
		return (List<ProvinceCityArea>) baseService.get(ProvinceCityArea.class,
				queryConditions);
	}

	public ProvinceCityArea findByName(String city) throws Exception {
		String tmp = "select p from ProvinceCityArea p where p.name like '%"
				+ city + "%'";

		return (ProvinceCityArea) baseService.getUniqueResultByJpql(tmp);
	}

	public List<ProvinceCityArea> getProvince() throws Exception {
		// TODO Auto-generated method stub
		return baseService
				.getByJpql("select p from ProvinceCityArea p where p.provinceCityArea=null");
	}

}
