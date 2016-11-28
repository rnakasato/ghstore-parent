package com.nakasato.ghstore.core.dao.impl;

import org.hibernate.Session;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class AbstractDAO < T extends AbstractDomainEntity > implements IDAO < T > {

	protected Session session;

	@Override
	public void save( T entity ) throws Exception {
		session.save( entity );
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Override
	public T findById( T entity ) throws Exception {
		T result = null;

		Class clazz = entity.getClass();
		result = ( T ) session.find( clazz, entity.getId() );
		return result;
	}

	@Override
	public void delete( T entity ) throws Exception {
		session.delete( entity );
	}

	@Override
	public void update( T entity ) throws Exception {
		session.update( entity );
	}

	public Session getSession() {
		return session;
	}

	public void setSession( Session session ) {
		this.session = session;
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
