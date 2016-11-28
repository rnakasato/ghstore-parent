package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;

public class ProductDAO extends AbstractDAO < Product > {

	@SuppressWarnings( "unchecked" )
	@Override
	public List < Product > find( AbstractDomainEntity entity ) {
		ProductFilter filter = ( ProductFilter ) entity;

		List < Product > productList = null;
		try {

			StringBuilder jpql = new StringBuilder();
			jpql.append( " SELECT DISTINCT (p) FROM Product p" );
			jpql.append( " LEFT JOIN p.tagList t " );
			jpql.append( " LEFT JOIN p.storeCategory sc " );
			jpql.append( " LEFT JOIN p.subcategory sb " );
			jpql.append( " WHERE 1=1 AND p.active = true " );

			if( filter.getId() != null ) {
				jpql.append( " AND p.id = :id" );
			}

			if( ListUtils.isNotEmpty( filter.getTagList() ) ) {
				jpql.append( " AND t in (:tagList)" );
			}

			if( filter.getInitialValue() != null && filter.getFinalValue() != null ) {
				jpql.append( " AND p.price BETWEEN :initialValue AND :finalValue " );
			}

			if( StringUtils.isNotEmpty( filter.getDescription() ) ) {
				jpql.append( " AND UPPER(p.description) like :description" );

			}

			if( filter.getCategory() != null )  {
				if( StringUtils.isNotEmpty( filter.getCategory().getDescription() ) ) {
					jpql.append( " AND UPPER(sc.description) like :storeCategoryDescription" );
				}
				if( filter.getCategory().getId() != null ) {
					jpql.append( " AND sc.id = :storeCategoryId" );
				}
			} else if( ListUtils.isNotEmpty( filter.getStoreCategoryList() ) ) {
				jpql.append( " AND sc in (:storeCategoryList)" );
			}

			if( filter.getSubcategory() != null ) {
				if( StringUtils.isNotEmpty( filter.getSubcategory().getDescription() ) ) {
					jpql.append( " AND UPPER(sb.description) like :subcategoryDescription" );
				}
				if( filter.getSubcategory().getId() != null ) {
					jpql.append( " AND sb.id = :subcategoryId" );
				}
			} else if( ListUtils.isNotEmpty( filter.getSubcategoryList() ) ) {
				jpql.append( " AND sb in (:subcategoryList)" );
			}

			if( StringUtils.isNotEmpty( filter.getName() ) ) {
				jpql.append( " AND UPPER(p.name) like :name" );
			}

			if( StringUtils.isNotEmpty( filter.getCode() ) ) {
				jpql.append( " AND p.code = :code" );
			}

			Query query = session.createQuery( jpql.toString() );

			if( filter.getId() != null ) {
				query.setParameter( "id", filter.getId() );
			}

			if( ListUtils.isNotEmpty( filter.getTagList() ) ) {
				query.setParameter( "tagList", filter.getTagList() );
			}

			if( filter.getInitialValue() != null && filter.getFinalValue() != null ) {
				query.setParameter( "initialValue", filter.getInitialValue() );
				query.setParameter( "finalValue", filter.getFinalValue() );
			}

			if( StringUtils.isNotEmpty( filter.getDescription() ) ) {
				jpql.append( " AND UPPER(p.description) like :description" );

			}

			if( StringUtils.isNotEmpty( filter.getDescription() ) ) {
				query.setParameter( "description", "%" + filter.getDescription() + "%" );
			}
			if( filter.getCategory() != null ) {
				if( StringUtils.isNotEmpty( filter.getCategory().getDescription() ) ) {
					query.setParameter( "storeCategoryDescription",
							"%" + filter.getCategory().getDescription().toUpperCase() + "%" );
				}
				if( filter.getCategory().getId() != null ) {
					query.setParameter( "storeCategoryId", filter.getCategory().getId() );
				}
			} else if( ListUtils.isNotEmpty( filter.getStoreCategoryList() ) ) {
				query.setParameter( "storeCategoryList", filter.getStoreCategoryList() );
			}

			if( filter.getSubcategory() != null ) {
				if( StringUtils.isNotEmpty( filter.getSubcategory().getDescription() ) ) {
					query.setParameter( "subcategoryDescription",
							"%" + filter.getSubcategory().getDescription().toUpperCase() + "%" );
				}
				if( filter.getSubcategory().getId() != null ) {
					query.setParameter( "subcategoryId", filter.getSubcategory().getId() );
				}
			} else if( ListUtils.isNotEmpty( filter.getSubcategoryList() ) ) {
				query.setParameter( "subcategoryList", filter.getSubcategoryList() );
			}

			if( StringUtils.isNotEmpty( filter.getName() ) ) {
				query.setParameter( "name", "%" + filter.getName().toUpperCase() + "%" );
			}

			if( StringUtils.isNotEmpty( filter.getCode() ) ) {
				query.setParameter( "code", filter.getCode() );
			}

			productList = query.getResultList();

		} catch( RuntimeException e ) {
			e.printStackTrace();
			throw e;
		}
		return productList;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List < Product > findAll() {
		List < Product > productList = null;
		try {

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Product " );

			Query query = session.createQuery( jpql.toString() );

			productList = query.getResultList();

		} catch( RuntimeException e ) {

		}
		return productList;
	}

}
