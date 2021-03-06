package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.UserTypeFilter;
import com.nakasato.ghstore.domain.user.UserType;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class UserTypeDAO extends DomainSpecificEntityDAO < UserType > {

	@Override
	public List < UserType > find( AbstractDomainEntity filter ) throws Exception {
		UserTypeFilter utFilter = ( UserTypeFilter ) filter;

		List < UserType > userTypeList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM UserType u " );
			jpql.append( " WHERE 1=1 " );

			if( ListUtils.isNotEmpty( utFilter.getCodeList() ) ) {
				jpql.append( " AND u.code in (:codeList)" );
			}
			
			jpql.append( " ORDER BY u.name asc " );

			Query query = session.createQuery( jpql.toString() );

			if( ListUtils.isNotEmpty( utFilter.getCodeList() ) ) {
				query.setParameter( "codeList", utFilter.getCodeList() );
			}

			userTypeList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return userTypeList;

	}

	@Override
	public List < UserType > findAll() throws Exception {
		List < UserType > userTypeList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM UserType u " );
			jpql.append( " ORDER BY u.name asc " );

			Query query = session.createQuery( jpql.toString() );

			userTypeList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return userTypeList;
	}

	public static void main( String[] args ) throws Exception {
		ICommand command = FactoryCommand.build( new UserType(), EOperation.FINDALL );
		Result r = command.execute();
		List < UserType > list = r.getEntityList();
		for( UserType userType: list ) {
			System.out.println( userType.getName() );
		}
		Thread t = new Thread();
		t.sleep( 5000 );
		HibernateUtil.shutdown();
	}

}
