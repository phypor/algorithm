package org.kd.server.service;

import javax.servlet.http.HttpServletRequest;

import org.kd.server.beans.entity.InitialInfo;
import org.kd.server.service.base.IBaseService;

public interface InitialInfoService extends IBaseService{
    public boolean addInitialInfo(InitialInfo initialInfo,HttpServletRequest request)throws Exception;
}
