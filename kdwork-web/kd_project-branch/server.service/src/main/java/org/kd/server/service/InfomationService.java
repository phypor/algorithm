package org.kd.server.service;

import org.kd.server.beans.entity.Infomation;
import org.kd.server.service.base.IBaseService;

public interface InfomationService extends IBaseService{
	public Infomation getByTitle(String title) throws Exception;
	public boolean saveOrUpadte(Infomation infomation)throws Exception;
}
