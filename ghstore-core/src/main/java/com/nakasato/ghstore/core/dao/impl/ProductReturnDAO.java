package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;

public class ProductReturnDAO extends AbstractDAO < ProductReturn > {

	@Override
	public List < ProductReturn > find( AbstractDomainEntity filter ) throws Exception {
		ProductReturnFilter returnFilter = ( ProductReturnFilter ) filter;

		List < ProductReturn > returnList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM ProductReturn pr " );
			jpql.append( " JOIN FETCH pr.order o   " );
			jpql.append( " JOIN FETCH o.customer c   " );
			jpql.append( " JOIN FETCH pr.status s   " );
			jpql.append( " WHERE 1 = 1 " );

			if( StringUtils.isNotEmpty( returnFilter.getCodOrder() ) ) {
				jpql.append( " AND o.code = :codOrder " );
			}
			
			if( StringUtils.isNotEmpty( returnFilter.getUsername() ) ) {
				jpql.append( " AND UPPER(c.username) like :username " );
			}
			
			if( returnFilter.getCustomer() != null ) {
				jpql.append( " AND c = :customer" );
			}

			if( returnFilter.getStartDate() != null && returnFilter.getEndDate() != null ) {
				jpql.append( " AND o.insertDate BETWEEN :startDate AND :endDate " );
			}

			if( ListUtils.isNotEmpty( returnFilter.getStatusList() ) ) {
				jpql.append( " AND s in (:statusList) " );
			}
			
			jpql.append( " ORDER BY o.insertDate desc " );
			
			Query query = session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( returnFilter.getCodOrder() ) ) {
				query.setParameter( "codOrder", returnFilter.getCodOrder() );
			}
			
			if( returnFilter.getCustomer() != null ) {
				query.setParameter( "customer", returnFilter.getCustomer() );
			}
			
			if( StringUtils.isNotEmpty( returnFilter.getUsername() ) ) {
				query.setParameter( "username", "%" + returnFilter.getUsername().toUpperCase()  + "%");
			}

			if( returnFilter.getStartDate() != null && returnFilter.getEndDate() != null ) {
				query.setParameter( "startDate", returnFilter.getStartDate() );
				query.setParameter( "endDate", returnFilter.getEndDate() );
			}

			if( ListUtils.isNotEmpty( returnFilter.getStatusList() ) ) {
				query.setParameter( "statusList", returnFilter.getStatusList() );
			}

			returnList = query.getResultList();
			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}

		return returnList;

	}

	@Override
	public List < ProductReturn > findAll() throws Exception {

		List < ProductReturn > productReturnList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM ProductReturn " );

			Query query = session.createQuery( jpql.toString() );

			productReturnList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return productReturnList;
	}

}
