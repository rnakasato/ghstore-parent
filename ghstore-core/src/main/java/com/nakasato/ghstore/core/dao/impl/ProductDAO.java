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

		boolean isDescriptionEmpty = StringUtils.isEmpty( filter.getDescription() );
		boolean isIdNull = ( filter.getId() == null );
		boolean isStoreCategoryNull = ( filter.getCategory() == null );
		boolean isStoreCategoryListNull = ListUtils.isEmpty( filter.getStoreCategoryList() );
		boolean isSubCategoryNull = ( filter.getSubcategory() == null );
		boolean isSubcategoryListNull = ListUtils.isEmpty( filter.getSubcategoryList() );
		boolean isNameEmpty = StringUtils.isEmpty( filter.getName() );
		boolean isCodeEmpty = StringUtils.isEmpty( filter.getCode() );

		List < Product > productList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Product p" );
			jpql.append( " WHERE 1=1 AND p.active = true " );

			if( ! isIdNull ) {
				jpql.append( " AND p.id = :id" );
			}

			if( ! isDescriptionEmpty ) {
				jpql.append( " AND UPPER(p.description) like :description" );

			}
			if( ! isStoreCategoryNull ) {
				if( StringUtils.isNotEmpty( filter.getCategory().getDescription() ) ) {
					jpql.append( " AND UPPER(p.storeCategory.description) like :storeCategoryDescription" );
				}
				if( filter.getCategory().getId() != null ) {
					jpql.append( " AND p.storeCategory.id = :storeCategoryId" );
				}
			} else if( ! isStoreCategoryListNull ) {
				jpql.append( " AND p.storeCategory in (:storeCategoryList)" );
			}

			if( ! isSubCategoryNull ) {
				if( StringUtils.isNotEmpty( filter.getSubcategory().getDescription() ) ) {
					jpql.append( " AND UPPER(p.subcategory.description) like :subcategoryDescription" );
				}
				if( filter.getSubcategory().getId() != null ) {
					jpql.append( " AND p.subcategory.id = :subcategoryId" );
				}
			} else if( ! isSubcategoryListNull ) {
				jpql.append( " AND p.subcategory in (:subcategoryList)" );
			}

			if( ! isNameEmpty ) {
				jpql.append( " AND p.name like :name" );
			}

			if( ! isCodeEmpty ) {
				jpql.append( " AND p.code = :code" );
			}

			Query query = session.createQuery( jpql.toString() );

			if( ! isIdNull ) {
				query.setParameter( "id", filter.getId() );
			}

			if( ! isDescriptionEmpty ) {
				query.setParameter( "description", "%" + filter.getDescription() + "%" );
			}
			if( ! isStoreCategoryNull ) {
				if( StringUtils.isNotEmpty( filter.getCategory().getDescription() ) ) {
					query.setParameter( "storeCategoryDescription",
							"%" + filter.getCategory().getDescription().toUpperCase() + "%" );
				}
				if( filter.getCategory().getId() != null ) {
					query.setParameter( "storeCategoryId", filter.getCategory().getId() );
				}
			} else if( ! isStoreCategoryListNull ) {
				query.setParameter( "storeCategoryList", filter.getStoreCategoryList() );
			}

			if( ! isSubCategoryNull ) {
				if( StringUtils.isNotEmpty( filter.getSubcategory().getDescription() ) ) {
					query.setParameter( "subcategoryDescription",
							"%" + filter.getSubcategory().getDescription().toUpperCase() + "%" );
				}
				if( filter.getSubcategory().getId() != null ) {
					query.setParameter( "subcategoryId", filter.getSubcategory().getId() );
				}
			} else if( ! isSubcategoryListNull ) {
				query.setParameter( "subcategoryList", filter.getSubcategoryList() );
			}

			if( ! isNameEmpty ) {
				query.setParameter( "name", "%" + filter.getName() + "%" );
			}

			if( ! isCodeEmpty ) {
				query.setParameter( "code", filter.getCode() );
			}

			productList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return productList;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List < Product > findAll() {
		List < Product > productList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Product " );

			Query query = session.createQuery( jpql.toString() );

			productList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return productList;
	}

}
