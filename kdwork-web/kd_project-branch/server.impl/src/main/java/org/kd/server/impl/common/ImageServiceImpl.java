package org.kd.server.impl.common;

import java.util.List;
import javax.annotation.Resource;

import org.kd.server.beans.entity.Image;
import org.kd.server.service.ImageService;
import org.kd.server.service.base.IBaseService;
import org.kd.server.impl.base.DefultBaseService;
import org.springframework.stereotype.Service;

@Service("imageService")
public class ImageServiceImpl  extends DefultBaseService implements ImageService{
	@Resource(name="baseService")
	private IBaseService baseService;

	// @Cacheable(value="myCache")  // 使用了一个缓存名叫 accountCache 
	public List<Image> findByTypeEnable(String typeCode, boolean enable,int page,int pageSize) throws Exception {
		return baseService.getPaginationJpql(page, pageSize, "select i from Image i where i.imageType.code=?0 and i.enable=?1  order by i.id desc",typeCode,enable);		
		//return baseService.getPaginationJpql(page, pageSize, "select i from Image i where i.imageType.code=?0 and i.enable=?1 and i.beginTime<=?2 and i.endTime>=?3 order by i.id desc",typeCode,enable,new Date(),new Date());
	}

}
