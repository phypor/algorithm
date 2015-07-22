package org.kd.server.service;

import java.util.List;

import org.kd.server.beans.entity.Position;
import org.kd.server.service.base.IBaseService;

public interface PositionService extends IBaseService {
	public Position getByName(String name) throws Exception;

	public List<Position> findByParentId(Long parentId) throws Exception;


	public Position getByNameAndSessionId(String string, long sessionid)
			throws Exception;
}
