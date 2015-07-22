package org.kd.server.service;

import org.kd.server.beans.entity.CompanyNature;
import org.kd.server.service.base.IBaseService;

public interface CompanyNatureService extends IBaseService {

	CompanyNature findByName(String strEnterpriseTye) throws Exception;

}
