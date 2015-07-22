package org.kd.server.service;

import java.util.List;

import org.kd.server.beans.entity.Section;
import org.kd.server.service.base.IBaseService;

public interface SectionService extends IBaseService{
     public List<Section> findByTypeCode(int page,int pageSize,String code) throws Exception;
}
