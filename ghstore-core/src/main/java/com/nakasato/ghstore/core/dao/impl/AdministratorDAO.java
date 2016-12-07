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
				jpql.append( " AND UPPER(adm.username) like :username" );
			}

			if( StringUtils.isNotEmpty( adminFilter.getLoginUserName() ) ) {
				jpql.append( " AND adm.username = :username" );
			}

			if( StringUtils.isNotEmpty( adminFilter.getName() ) ) {
				jpql.append( " AND UPPER(adm.name) like :name" );
			}

			if( StringUtils.isNotEmpty( adminFilter.getCpf() ) ) {
				jpql.append( " AND UPPER(adm.cpf) like :cpf" );
			}

			if( adminFilter.getActive() != null ) {
				jpql.append( " AND adm.active= :active" );
			}
			
			jpql.append( " ORDER BY adm.username asc " );

			Query query = session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( adminFilter.getUserName() ) ) {
				query.setParameter( "username", "%" + adminFilter.getUserName().toUpperCase() + "%" );
			}

			if( StringUtils.isNotEmpty( adminFilter.getLoginUserName() ) ) {
				query.setParameter( "username", adminFilter.getLoginUserName() );
			}

			if( StringUtils.isNotEmpty( adminFilter.getCpf() ) ) {
				query.setParameter( "cpf", "%" + adminFilter.getCpf().toUpperCase() + "%" );
			}

			if( StringUtils.isNotEmpty( adminFilter.getName() ) ) {
				query.setParameter( "name", "%" + adminFilter.getUserName().toUpperCase() + "%" );
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
			jpql.append( " ORDER BY adm.username asc " );
			
			Query query = session.createQuery( jpql.toString() );

			administratorList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return administratorList;
	}

}
