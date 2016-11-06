package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;

public class ProductExchangeDAO extends AbstractDAO < ProductExchange > {

	@ Override
	public List < ProductExchange > find( AbstractDomainEntity filter ) throws Exception {

		return null;
	}

	@ Override
	public List < ProductExchange > findAll() throws Exception {
		List < ProductExchange > productExchangeList =null;
		try {
			openSession();

			StringBuilder jpql =new StringBuilder();
			jpql.append( " FROM ProductExchange " );

			Query query =session.createQuery( jpql.toString() );

			productExchangeList =query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return productExchangeList;
	}

}
