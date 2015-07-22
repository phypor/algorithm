package org.kd.server.impl.common;
/**
 * 
 * @author bojie
 * 
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.kd.server.beans.entity.QuantityScale;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.service.QuantityScaleService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("quantityScaleService")
public class QuantityScaleServiceImpl extends DefultBaseService implements
		QuantityScaleService {
	@Resource(name = "baseService")
	private IBaseService baseService;

	public QuantityScale getByName(String name) throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("name", QueryCondition.EQ, name);
		queryConditions.add(q);
		return (QuantityScale) baseService.getSingleResult(QuantityScale.class,
				queryConditions);
	}

}
