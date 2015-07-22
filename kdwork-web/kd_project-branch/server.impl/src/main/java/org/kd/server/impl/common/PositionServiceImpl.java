package org.kd.server.impl.common;

/**
 * 
 * @author bojie
 * 
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.kd.server.beans.entity.Position;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.service.PositionService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("positionService")
public class PositionServiceImpl extends DefultBaseService implements
		PositionService {
	@Resource(name = "baseService")
	private IBaseService baseService;

	public List<Position> findByParentId(Long parentId) throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("provinceCityArea.id",
				QueryCondition.EQ, parentId);
		queryConditions.add(q);
		return (List<Position>) baseService
				.get(Position.class, queryConditions);
	}

	public Position getByName(String name) throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("name", QueryCondition.EQ, name);
		queryConditions.add(q);
		return (Position) baseService.getSingleResult(Position.class,
				queryConditions);
	}

	@Override
	public Position getByNameAndSessionId(String name, long sessionid)
			throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q1 = new QueryCondition("name", QueryCondition.EQ, name);
		QueryCondition q2 = new QueryCondition("section.id", QueryCondition.EQ,
				sessionid);
		queryConditions.add(q1);
		queryConditions.add(q2);
		return (Position) baseService.getSingleResult(Position.class,
				queryConditions);
	}

}
