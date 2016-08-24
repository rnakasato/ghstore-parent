package com.nakasato.ghstore.core.dao.impl;

import org.hibernate.Session;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class AbstractDAO<T extends AbstractDomainEntity> implements IDAO<T> {

	protected Session session;

	@Override
	public void save(T entity) throws Exception{
		try {
			openSession();
			session.save(entity);
			closeSession();
		} catch (Exception e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public T findById(T entity) throws Exception {
		T result = null;
		try {
			openSession();
			Class clazz = entity.getClass();			
			result = (T) session.find(clazz, entity.getId());
		} catch (Exception e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return result;
	}

	@Override
	public void delete(T entity) throws Exception {
		try {
			openSession();
			session.delete(entity);
			closeSession();
		} catch (Exception e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
	}

	@Override
	public void update(T entity) throws Exception {
		try {
			openSession();
			session.update(entity);
			closeSession();
		} catch (Exception e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
	}

	public void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
	}

	public void cancelSession() {
		session.getTransaction().rollback();
		session.close();
	}

	public void closeSession() {
		session.getTransaction().commit();
		session.close();
	}

}
