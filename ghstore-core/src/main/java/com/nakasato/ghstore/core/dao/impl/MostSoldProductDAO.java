package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.MostSoldProductFilter;
import com.nakasato.ghstore.domain.product.group.MostSoldProduct;
import com.nakasato.ghstore.domain.user.Customer;

public class MostSoldProductDAO extends AbstractDAO < MostSoldProduct > {

	@Override
	public List < MostSoldProduct > find( AbstractDomainEntity entity ) throws Exception {
		MostSoldProductFilter filter = ( MostSoldProductFilter ) entity;
		List < MostSoldProduct > recommendedList = null;

		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(
					" SELECT new com.nakasato.ghstore.domain.product.group.MostSoldProduct(p, SUM(oi.amount)) FROM Order o" );
			jpql.append( " LEFT JOIN o.orderItemList oi " );
			jpql.append( " LEFT JOIN oi.product p " );
			jpql.append( " LEFT JOIN p.storeCategory sc " );
			jpql.append( " LEFT JOIN o.deliverAddress da " );
			jpql.append( " LEFT JOIN da.city ct " );
			jpql.append( " LEFT JOIN ct.state st " );
			jpql.append( " LEFT JOIN o.customer cs " );
			jpql.append( " WHERE 1=1 " );

			if( filter.getCustomer() != null ) {
				jpql.append( " AND cs = :customer " );
			}

			if( ListUtils.isNotEmpty( filter.getCategoryList() ) ) {
				jpql.append( " AND sc in (:categoryList) " );
			}
			
			if( ListUtils.isNotEmpty( filter.getNotInList() ) ) {
				jpql.append( " AND p not in (:productList) " );
			}

			jpql.append( " GROUP BY p.id " );
			jpql.append( " ORDER BY SUM(oi.amount)  desc " );

			Query query = session.createQuery( jpql.toString() );

			if( filter.getCustomer() != null ) {
				query.setParameter( "customer", filter.getCustomer() );
			}

			if( ListUtils.isNotEmpty( filter.getCategoryList() ) ) {
				query.setParameter( "categoryList", filter.getCategoryList() );
			}
			
			if( ListUtils.isNotEmpty( filter.getNotInList() ) ) {
				query.setParameter( "productList", filter.getNotInList() );
			}
			
			if( filter.getMaxResults() != null ) {
				query.setMaxResults( filter.getMaxResults() );
			}

			recommendedList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}

		return recommendedList;
	}

	@Override
	public List < MostSoldProduct > findAll() throws Exception {

		return null;
	}

	public static void main( String[] args ) {
		MostSoldProductDAO dao = new MostSoldProductDAO();
		dao.setSession( SessionThreadLocal.getSession() );
		
		CustomerDAO cDAO = new CustomerDAO();
		cDAO.setSession( SessionThreadLocal.getSession() );
		try {
			List<Customer> cL = cDAO.findAll();
			MostSoldProductFilter filter = new MostSoldProductFilter();
			filter.setCustomer( cL.get( 0 ) );
			filter.setMaxResults( 4 );
			List < MostSoldProduct > rl = dao.find( filter );
			for( MostSoldProduct recommendedProduct: rl ) {
				System.out.println( recommendedProduct.getProduct().getName()  + " VENDIDOS: " + recommendedProduct.getAmount());
			}
		} catch( Exception e ) {
			SessionThreadLocal.rollback();
			e.printStackTrace();
		} finally {
			SessionThreadLocal.closeSession();
			System.exit( 0 );
		}
	}
}
