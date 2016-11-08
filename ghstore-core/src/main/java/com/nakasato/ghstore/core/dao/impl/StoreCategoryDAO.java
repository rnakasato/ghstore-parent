package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.StoreCategoryFilter;
import com.nakasato.ghstore.domain.product.StoreCategory;

public class StoreCategoryDAO extends AbstractDAO < StoreCategory > {

	@SuppressWarnings( "unchecked" )
	@Override
	public List < StoreCategory > find( AbstractDomainEntity entity ) {
		StoreCategoryFilter filter = ( StoreCategoryFilter ) entity;
		boolean isDescriptionEmpty = StringUtils.isEmpty( filter.getDescription() );
		boolean isIdNull = ( filter.getId() == null );

		List < StoreCategory > storeCategoryList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM StoreCategory s" );
			jpql.append( " WHERE 1=1 " );

			if( ! isIdNull ) {
				jpql.append( " AND s.id = :id" );
			}

			if( ! isDescriptionEmpty ) {
				jpql.append( " AND UPPER(s.description) like :description" );

			}

			Query query = session.createQuery( jpql.toString() );

			if( ! isIdNull ) {
				query.setParameter( "id", filter.getId() );
			}

			if( ! isDescriptionEmpty ) {
				query.setParameter( "description", "%" + filter.getDescription().toUpperCase() + "%" );
			}

			storeCategoryList = ( List < StoreCategory > ) query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return storeCategoryList;

	}

	/**
	 * O Método findAll não será utilizado porque no momento não há sentido para
	 * retornar todas as categorias
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List < StoreCategory > findAll() {
		List < StoreCategory > storeCategoryList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM StoreCategory " );

			Query query = session.createQuery( jpql.toString() );

			storeCategoryList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return storeCategoryList;
	}

	@Override
	public void save( StoreCategory entity ) {
	}

	@Override
	public void update( StoreCategory entity ) {
	}

}
