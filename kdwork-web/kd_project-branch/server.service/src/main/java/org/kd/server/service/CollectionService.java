package org.kd.server.service;

import java.util.List;

import org.kd.server.beans.entity.Collection;
import org.kd.server.service.base.IBaseService;

public interface CollectionService extends IBaseService{

	public List<Collection> findByUserId(Long id) throws Exception;


}
