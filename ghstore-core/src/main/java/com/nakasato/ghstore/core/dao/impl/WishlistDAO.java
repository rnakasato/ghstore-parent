package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.filter.impl.WishlistFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.user.Wishlist;

public class WishlistDAO extends AbstractDAO < Wishlist > {

	@Override
	public List < Wishlist > find( AbstractDomainEntity entity ) throws Exception {

		WishlistFilter filter = ( WishlistFilter ) entity;

		List < Wishlist > wishList = null;
		try {

			StringBuilder jpql = new StringBuilder();
			jpql.append( " SELECT DISTINCT (w) FROM Wishlist w" );
			jpql.append( " LEFT JOIN w.product p " );
			jpql.append( " LEFT JOIN w.customer c " );
			jpql.append( " LEFT JOIN p.tagList t " );
			jpql.append( " LEFT JOIN p.storeCategory sc " );
			jpql.append( " LEFT JOIN p.subcategory sb " );
			jpql.append( " WHERE 1=1 AND p.active = true " );

			if( ListUtils.isNotEmpty( filter.getTagList() ) ) {
				jpql.append( " AND t in (:tagList)" );
			}

			if( ListUtils.isNotEmpty( filter.getStoreCategoryList() ) ) {
				jpql.append( " AND sc in (:storeCategoryList)" );
			}

			if( ListUtils.isNotEmpty( filter.getSubcategoryList() ) ) {
				jpql.append( " AND sb in (:subcategoryList)" );
			}

			if( StringUtils.isNotEmpty( filter.getProductName() ) ) {
				jpql.append( " AND UPPER(p.name) like :name" );
			}

			if( filter.getCustomer() != null ) {
				jpql.append( " AND c = :customer" );
			}
			
			jpql.append( " ORDER BY p.name asc " );

			Query query = session.createQuery( jpql.toString() );

			if( ListUtils.isNotEmpty( filter.getTagList() ) ) {
				query.setParameter( "tagList", filter.getTagList() );
			}

			if( ListUtils.isNotEmpty( filter.getStoreCategoryList() ) ) {
				query.setParameter( "storeCategoryList", filter.getStoreCategoryList() );
			}

			if( ListUtils.isNotEmpty( filter.getSubcategoryList() ) ) {
				query.setParameter( "subcategoryList", filter.getSubcategoryList() );
			}

			if( StringUtils.isNotEmpty( filter.getProductName() ) ) {
				query.setParameter( "name", "%" + filter.getProductName().toUpperCase() + "%" );
			}

			if( filter.getCustomer() != null ) {
				query.setParameter( "customer", filter.getCustomer() );
			}

			wishList = query.getResultList();

		} catch( RuntimeException e ) {
			e.printStackTrace();
			throw e;
		}
		return wishList;

	}

	@Override
	public List < Wishlist > findAll() throws Exception {
		return null;
	}

}
