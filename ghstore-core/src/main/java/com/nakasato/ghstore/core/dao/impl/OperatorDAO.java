package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.AdministratorFilter;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.filter.impl.OperatorFilter;
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.Operator;

public class OperatorDAO extends AbstractDAO < Operator > {

	@Override
	public List < Operator > find( AbstractDomainEntity filter ) throws Exception {
		OperatorFilter operatorFilter = ( OperatorFilter ) filter;
		List < Operator > operatorList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Operator op " );
			if( operatorFilter.getLoadAddress() != null && operatorFilter.getLoadAddress() ) {
				jpql.append( " JOIN FETCH op.addressList ad" );
			}

			jpql.append( " WHERE 1=1 " );
			if( StringUtils.isNotEmpty( operatorFilter.getUserName() ) ) {
				jpql.append( " AND op.username = :username" );
			}

			if( operatorFilter.getActive() != null ) {
				jpql.append( " AND op.active= :active" );
			}

			Query query = session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( operatorFilter.getUserName() ) ) {
				query.setParameter( "username", operatorFilter.getUserName() );
			}

			if( operatorFilter.getActive() != null ) {
				query.setParameter( "active", operatorFilter.getActive() );
			}

			operatorList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
			e.printStackTrace();
		}
		return operatorList;
	}

	@Override
	public List < Operator > findAll() throws Exception {
		List < Operator > operatorList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Operator " );

			Query query = session.createQuery( jpql.toString() );

			operatorList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return operatorList;
	}

}
