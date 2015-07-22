package org.kd.server.impl.common;

import java.util.List;
import javax.annotation.Resource;

import org.kd.server.beans.entity.Section;
import org.kd.server.service.SectionService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("sectionService")
public class SectionServiceImpl extends DefultBaseService implements SectionService{
	@Resource(name="baseService")
	private IBaseService baseService;

	public List<Section> findByTypeCode(int page,int pageSize,String code) throws Exception {
		return   baseService.getPaginationJpql(page, pageSize,"select s from Section s where s.sectionType.code=?0 and s.available=true order by s.sessionOrder asc",code);		 
	}

}
