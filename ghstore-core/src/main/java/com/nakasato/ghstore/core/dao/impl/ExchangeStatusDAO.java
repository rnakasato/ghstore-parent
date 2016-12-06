package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ExchangeStatusFilter;
import com.nakasato.ghstore.domain.productexchange.ExchangeStatus;

public class ExchangeStatusDAO extends DomainSpecificEntityDAO  < ExchangeStatus > {

	@Override
	public List < ExchangeStatus > find( AbstractDomainEntity filter ) throws Exception {
		ExchangeStatusFilter statusFilter = ( ExchangeStatusFilter ) filter;
		List < ExchangeStatus > statusList = null;
		try {
			openSession();
			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM ExchangeStatus e" );
			jpql.append( " WHERE 1=1 " );
			if( StringUtils.isNotEmpty( statusFilter.getCode() ) ) {
				jpql.append( " AND e.code like :code " );
			}
			
			jpql.append( " ORDER BY e.description asc " );

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
	public List < ExchangeStatus > findAll() throws Exception {

		List < ExchangeStatus > statusList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM ExchangeStatus e " );
			jpql.append( " ORDER BY e.description asc " );

			Query query = session.createQuery( jpql.toString() );

			statusList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return statusList;
	}



}
