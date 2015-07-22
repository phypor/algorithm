package org.kd.server.service;

import java.util.List;

import org.kd.server.beans.entity.Image;
import org.kd.server.service.base.IBaseService;

public interface ImageService extends IBaseService{

	public List<Image> findByTypeEnable(String typeCode,boolean enable,int page,int pageSize)throws Exception;
}
