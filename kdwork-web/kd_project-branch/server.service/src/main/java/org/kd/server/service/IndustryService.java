package org.kd.server.service;

import org.kd.server.beans.entity.Industry;
import org.kd.server.service.base.IBaseService;

public interface IndustryService  extends IBaseService{
public Industry findByName(String name) throws Exception;
}
