package org.kd.server.impl.common;
/**
 * 
 * @author bojie
 * 
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.kd.server.beans.entity.Infomation;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.service.InfomationService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("infomationService")
public class InfomationServiceImpl extends DefultBaseService implements
		InfomationService {
	@Resource(name = "baseService")
	private IBaseService baseService;

	public Infomation getByTitle(String title) throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("title",
				QueryCondition.LK, title);
		queryConditions.add(q);
		return (Infomation) baseService.getSingleResult(Infomation.class, queryConditions) ;
	}

	public boolean saveOrUpadte(Infomation infomation)  {// URL判断是否是一样的信息
		Infomation result = null;
		try {
			result = getByTitle(infomation.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result != null) {// exsit
			infomation.setId(result.getId());
			try {
				baseService.update(infomation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		try {
			baseService.save(infomation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
