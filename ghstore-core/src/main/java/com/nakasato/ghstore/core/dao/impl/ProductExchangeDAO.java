package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ProductExchangeFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;

public class ProductExchangeDAO extends AbstractDAO < ProductExchange > {

	@Override
	public List < ProductExchange > find( AbstractDomainEntity filter ) throws Exception {

		ProductExchangeFilter exchangeFilter = ( ProductExchangeFilter ) filter;

		List < ProductExchange > exchangeList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM ProductExchange pe " );
			jpql.append( " JOIN FETCH pe.order o   " );
			jpql.append( " JOIN FETCH o.customer c   " );
			jpql.append( " JOIN FETCH pe.status s   " );
			jpql.append( " WHERE 1 = 1 " );

			if( StringUtils.isNotEmpty( exchangeFilter.getCodOrder() ) ) {
				jpql.append( " AND o.code = :codOrder " );
			}
			
			if( exchangeFilter.getCustomer() != null ) {
				jpql.append( " AND c = :customer" );
			}

			if( StringUtils.isNotEmpty( exchangeFilter.getUsername() ) ) {
				jpql.append( " AND c.username = :username " );
			}

			if( exchangeFilter.getStartDate() != null && exchangeFilter.getEndDate() != null ) {
				jpql.append( " AND o.insertDate BETWEEN :startDate AND :endDate " );
			}

			if( ListUtils.isNotEmpty( exchangeFilter.getStatusList() ) ) {
				jpql.append( " AND s in (:statusList) " );
			}
			
			jpql.append( " ORDER BY o.insertDate desc " );

			Query query = session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( exchangeFilter.getCodOrder() ) ) {
				query.setParameter( "codOrder", exchangeFilter.getCodOrder() );
			}
			
			if( exchangeFilter.getCustomer() != null ) {
				query.setParameter( "customer", exchangeFilter.getCustomer() );
			}

			if( StringUtils.isNotEmpty( exchangeFilter.getUsername() ) ) {
				query.setParameter( "username", exchangeFilter.getUsername() );
			}

			if( exchangeFilter.getStartDate() != null && exchangeFilter.getEndDate() != null ) {
				query.setParameter( "startDate", exchangeFilter.getStartDate() );
				query.setParameter( "endDate", exchangeFilter.getEndDate() );
			}

			if( ListUtils.isNotEmpty( exchangeFilter.getStatusList() ) ) {
				query.setParameter( "statusList", exchangeFilter.getStatusList() );
			}

			exchangeList = query.getResultList();
			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}

		return exchangeList;

	}

	@Override
	public List < ProductExchange > findAll() throws Exception {
		List < ProductExchange > productExchangeList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM ProductExchange " );

			Query query = session.createQuery( jpql.toString() );

			productExchangeList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return productExchangeList;
	}

}
