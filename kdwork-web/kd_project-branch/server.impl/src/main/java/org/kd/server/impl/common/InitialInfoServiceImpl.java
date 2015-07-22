package org.kd.server.impl.common;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kd.server.beans.entity.InitialInfo;
import org.kd.server.service.InitialInfoService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("initialInfoService")
public class InitialInfoServiceImpl extends DefultBaseService implements InitialInfoService{
	@Resource(name="baseService")
	private IBaseService baseService;

	public boolean addInitialInfo(InitialInfo initialInfo,HttpServletRequest request)throws Exception{
		boolean flag = false;
		initialInfo.setLastOnlineTime(new Date());
		initialInfo.setSessionId(request.getSession().getId());

		InitialInfo initialInfoIn = (InitialInfo) baseService.getUniqueResultByJpql("from InitialInfo as o where o.sessionId=?0", initialInfo.getSessionId());
		
		if(initialInfoIn==null){
		     baseService.save(initialInfo);
		}else{
			initialInfoIn.setLastOnlineTime(new Date());
			baseService.update(initialInfoIn);
		}
		flag = true;

		return flag;
	}

}
