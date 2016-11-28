package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.AdministratorFilter;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.user.Administrator;

public class AdministratorDAO extends AbstractDAO < Administrator > {

	@Override
	public List < Administrator > find( AbstractDomainEntity filter ) throws Exception {
		AdministratorFilter adminFilter = ( AdministratorFilter ) filter;
		List < Administrator > adminList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Administrator adm " );
			if( adminFilter.getLoadAddress() != null && adminFilter.getLoadAddress() ) {
				jpql.append( " JOIN FETCH adm.addressList ad" );
			}

			jpql.append( " WHERE 1=1 " );
			if( StringUtils.isNotEmpty( adminFilter.getUserName() ) ) {
				jpql.append( " AND adm.username = :username" );
			}

			if( adminFilter.getActive() != null ) {
				jpql.append( " AND adm.active= :active" );
			}

			Query query = session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( adminFilter.getUserName() ) ) {
				query.setParameter( "username", adminFilter.getUserName() );
			}

			if( adminFilter.getActive() != null ) {
				query.setParameter( "active", adminFilter.getActive() );
			}

			adminList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
			e.printStackTrace();
		}
		return adminList;
	}

	@Override
	public List < Administrator > findAll() throws Exception {
		List < Administrator > administratorList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Administrator " );

			Query query = session.createQuery( jpql.toString() );

			administratorList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return administratorList;
	}

}
