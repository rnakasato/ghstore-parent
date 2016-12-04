package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ReturnStatusFilter;
import com.nakasato.ghstore.domain.productreturn.ReturnStatus;

public class ReturnStatusDAO extends AbstractDAO < ReturnStatus > {

	@Override
	public List < ReturnStatus > find( AbstractDomainEntity filter ) throws Exception {
		ReturnStatusFilter statusFilter = ( ReturnStatusFilter ) filter;
		List < ReturnStatus > statusList = null;
		try {
			openSession();
			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM ReturnStatus r" );
			jpql.append( " WHERE 1=1 " );
			if( StringUtils.isNotEmpty( statusFilter.getCode() ) ) {
				jpql.append( " AND r.code like :code " );
			}
			
			jpql.append( " ORDER BY r.description asc " );

			Query query = session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( statusFilter.getCode() ) ) {
				query.setParameter( "code", statusFilter.getCode() );
			}
			statusList = query.getResultList();

			closeSession();
		} catch( Exception e ) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return statusList;
	}

	@Override
	public List < ReturnStatus > findAll() throws Exception {

		List < ReturnStatus > statusList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM ReturnStatus r " );
			jpql.append( " ORDER BY r.description asc " );

			Query query = session.createQuery( jpql.toString() );

			statusList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return statusList;
	}

}
