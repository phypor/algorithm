package org.kd.server.impl.common;
/**
 * 
 * @author bojie
 * 
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.kd.server.beans.entity.RecruitInfo;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.service.RecruitInfoService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("recruitInfoService")
public class RecruitInfoServiceImpl extends DefultBaseService implements
		RecruitInfoService {
	@Resource(name = "baseService")
	private IBaseService baseService;

	public RecruitInfo findByHash(String hash) {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition q = new QueryCondition("hash", QueryCondition.EQ, hash);
		queryConditions.add(q);
		try {
			return (RecruitInfo) baseService.getSingleResult(RecruitInfo.class, queryConditions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

  

}
