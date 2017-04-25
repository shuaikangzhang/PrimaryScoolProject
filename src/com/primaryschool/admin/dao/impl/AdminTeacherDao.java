package com.primaryschool.admin.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.primaryschool.admin.dao.IAdminTeacherDao;
import com.primaryschool.home.dao.ITypeFlagToTypeIdDao;
@Repository
public class AdminTeacherDao<T> implements IAdminTeacherDao<T> {

	@Autowired
	private SessionFactory  sessionFactory;
	
	@Autowired
	private ITypeFlagToTypeIdDao  typeFlagToTypeIdDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findTeacherInfo(String flag, int position, int item_per_page) {
		// TODO Auto-generated method stub
		//根据类型获取id
		int id=typeFlagToTypeIdDao.findTeacherTypeIdByTypeFlag(flag);
		String hql="select new com.primaryschool.home.entity.Teacher(t.id,t.itemTitle,t.addTime,t.isPublish,t.author) from Teacher t where t.typeId=?  order by t.addTime desc";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		query.setFirstResult(position);
		query.setMaxResults(item_per_page);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findTeacherInfoById(int id) {
		// TODO Auto-generated method stub
		String hql="select new com.primaryschool.home.entity.Teacher(t.id,t.author,t.itemTitle,t.itemContent,t.addTime,t.viewCount,tt.itemTypeName,tt.itemTypeFlag,t.isPublish)from Teacher t,TeacherType tt  where tt.id=t.typeId and t.id=?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		return (T) query.uniqueResult();
	}

	@Override
	public int findTeacherCount(String flag) {
		// TODO Auto-generated method stub
		BigInteger count;
		int r;
		//根据类型获取id
		int id=typeFlagToTypeIdDao.findTeacherTypeIdByTypeFlag(flag);
		String sql="select count(CASE WHEN t.type_id=?  THEN 1 ELSE NULL END) from ps_teacher t";
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(sql); 
		query.setInteger(0,id);
		count= (BigInteger) query.uniqueResult();
		r=count.intValue();
		return r;
	}

	@Override
	public boolean updateTeacherInfo(T t) {
		// TODO Auto-generated method stub
		 String hql="update Teacher u set u.itemTitle=:itemTitle ,u.itemContent=:itemContent,u.addTime=:addTime,u.isImage=:isImage,u.isPublish=:isPublish,u.author=:author  where u.id=:id";
		Query query  = sessionFactory.getCurrentSession().createQuery(hql); 
		query.setProperties(t);
		return (query.executeUpdate()>0);
	}

	@Override
	public int addTeacherInfo(T t) {
		// TODO Auto-generated method stub
		Serializable result =sessionFactory.getCurrentSession().save(t);
		return (Integer)result;
	}

	@Override
	public void deleteTeacherById(List<?> ids) {
		// TODO Auto-generated method stub
		String hql = "delete from Teacher where id in (:ids)";
		 Query query  =  sessionFactory.getCurrentSession().createQuery(hql);
		 query.setParameterList("ids", ids).executeUpdate();	
	}

}