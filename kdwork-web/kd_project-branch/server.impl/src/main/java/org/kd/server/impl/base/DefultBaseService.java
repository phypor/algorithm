package org.kd.server.impl.base;

import java.util.List;

import javax.annotation.Resource;

import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.dao.base.IBaseDao;
import org.kd.server.service.base.IBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("rawtypes")
@Service("baseService")
public class DefultBaseService implements IBaseService {
	
	@Resource(name="baseDao")
	private IBaseDao baseDao;

	@Transactional(propagation=Propagation.REQUIRED)
	public <T> void delete(Class<T> clazz, Object id)throws Exception{
		baseDao.delete(clazz, id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public <T> void delete(Class<T> clazz, Object[] ids)throws Exception{
		baseDao.delete(clazz, ids);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public int executeJpql(String jpql, Object... objects)throws Exception{
		return baseDao.executeJpql(jpql, objects);
	}

	public <T> List<T> get(Class<T> clazz,
			List<QueryCondition> queryConditions, String orderBy,
			int currentPage, int pageSize)throws Exception{
		return baseDao.get(clazz, queryConditions, orderBy, currentPage, pageSize);
	}

	public <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions)throws Exception{
		return baseDao.get(clazz, queryConditions);
	}

	public <T> List<T> get(Class<T> clazz,
			List<QueryCondition> queryConditions, String orderBy)throws Exception{
		return baseDao.get(clazz, queryConditions, orderBy);
	}

	public <T> List<T> getAll(Class<T> clazz)throws Exception{
		return baseDao.getAll(clazz);
	}

	public <T> T getById(Class<T> clazz, Object id)throws Exception{
		return baseDao.getById(clazz, id);
	}

	public <T> List<T> getByIds(Class<T> clazz, Object[] ids)throws Exception{
		return baseDao.getByIds(clazz, ids);
	}
	
	public <T> List<T> getByJpql(String jpql, Object... objects)throws Exception{
		return baseDao.getByJpql(jpql, objects);
	}

	public <T> Pagination<T> getPagination(Class<T> clazz,
			List<QueryCondition> queryConditions, String orderBy,
			int currentPage, int pageSize)throws Exception{
		currentPage = currentPage<1?1:currentPage;
		pageSize = pageSize<1?1:pageSize;
		
		return baseDao.getPagination(clazz, queryConditions, orderBy, currentPage, pageSize);
	}
	
	public <T>List<T>  getPaginationJpql(int currentPage, int pageSize,String jpql,Object... objects)throws Exception{
		return baseDao.getPageQuery(currentPage, pageSize, jpql, objects);
	}

	public long getRecordCount(Class clazz, List<QueryCondition> queryConditions)throws Exception{
		return baseDao.getRecordCount(clazz, queryConditions);
	}

	public Object getSingleResult(Class clazz,
			List<QueryCondition> queryConditions)throws Exception{
		return baseDao.getSingleResult(clazz, queryConditions);
	}

	public Object getUniqueResultByJpql(String jpql, Object... objects)throws Exception{
		return baseDao.getUniqueResultByJpql(jpql, objects);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Object entity)throws Exception{
		baseDao.save(entity);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public <T> void batchSave(List<T> entitys)throws Exception{
		baseDao.batchSave(entitys);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void update(Object entity)throws Exception{
		baseDao.update(entity);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public int executeBySQL(String sql, Object... params)throws Exception{
		return baseDao.executeBySQL(sql, params);
	}
	
}
