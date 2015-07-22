package org.kd.server.impl.common;
/**
 * 
 * @author bojie
 * 
 */
import javax.annotation.Resource;

import org.kd.server.beans.entity.Industry;
import org.kd.server.service.IndustryService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("industryService")
public class IndustryServiceImpl  extends DefultBaseService implements IndustryService{
	@Resource(name = "baseService")
	private IBaseService baseService;
	public Industry findByName(String name) throws Exception {
			String tmp = "select p from Industry p where p.name like '%"
					+ name + "%'";
			return (Industry) baseService.getUniqueResultByJpql(tmp);
	}

}
