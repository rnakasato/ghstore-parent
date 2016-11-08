package com.nakasato.ghstore.core.dao.impl;

import javax.persistence.Query;

import com.nakasato.ghstore.core.IDomainSpecificEntityDAO;
import com.nakasato.ghstore.domain.DomainSpecificEntity;

public abstract class DomainSpecificEntityDAO < T extends DomainSpecificEntity > extends AbstractDAO < T >
		implements IDomainSpecificEntityDAO < T > {

	@Override
	public T findByCode( T entity ) {
		T result = null;
		try {
			openSession();
			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM " ).append( entity.getClass().getName() ).append( " d " );
			jpql.append( " WHERE d.code = :code" );
			super.openSession();
			Query query = session.createQuery( jpql.toString() );
			query.setParameter( "code", entity.getCode() );
			result = ( T ) query.getSingleResult();
			closeSession();
		} catch( Exception e ) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return result;
	}

}
