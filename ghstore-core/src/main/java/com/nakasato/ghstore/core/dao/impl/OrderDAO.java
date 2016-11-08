package com.nakasato.ghstore.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.filter.impl.PerformanceGraphicFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.user.State;

public class OrderDAO extends AbstractDAO < Order > {

	@Override
	public List < Order > find( AbstractDomainEntity filter ) throws Exception {
		List < Order > orderList = null;
		try {
			openSession();

			OrderFilter orderFilter = ( OrderFilter ) filter;
			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Order o " );
			jpql.append( " JOIN FETCH o.customer c   " );
			jpql.append( " JOIN FETCH o.orderStatus s   " );
			jpql.append( " WHERE 1 = 1 " );

			// usa igual em vez de like para evitar retornar mais de um usuário
			// caso hajam usuários CUSTOMER e CUSTOMER_A se buscar customer
			// retornaria ambos
			if( StringUtils.isNotEmpty( orderFilter.getUsername() ) ) {
				jpql.append( " AND c.username = :username " );
			}

			if( StringUtils.isNotEmpty( orderFilter.getStatusCode() ) ) {
				jpql.append( " AND s.code = :statusCode " );
			}

			if( StringUtils.isNotEmpty( orderFilter.getOrderCode() ) ) {
				jpql.append( " AND o.code = :orderCode " );
			}

			if( StringUtils.isNotEmpty( orderFilter.getTransactionCode() ) ) {
				jpql.append( " AND o.transactionCode = :transactionCode " );
			}

			if( orderFilter.getStartDate() != null && orderFilter.getEndDate() != null ) {
				jpql.append( " AND o.insertDate BETWEEN :startDate AND :endDate " );
			}

			Query query = session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( orderFilter.getUsername() ) ) {
				query.setParameter( "username", orderFilter.getUsername() );
			}

			if( StringUtils.isNotEmpty( orderFilter.getStatusCode() ) ) {
				query.setParameter( "statusCode", orderFilter.getStatusCode() );
			}

			if( StringUtils.isNotEmpty( orderFilter.getOrderCode() ) ) {
				query.setParameter( "orderCode", orderFilter.getOrderCode() );
			}

			if( StringUtils.isNotEmpty( orderFilter.getTransactionCode() ) ) {
				query.setParameter( "transactionCode", orderFilter.getTransactionCode() );
			}

			if( orderFilter.getStartDate() != null && orderFilter.getEndDate() != null ) {
				query.setParameter( "startDate", orderFilter.getStartDate() );
				query.setParameter( "endDate", orderFilter.getEndDate() );
			}

			orderList = query.getResultList();
			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}

		return orderList;
	}

	@Override
	public List < Order > findAll() throws Exception {

		List < Order > orderList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Order " );

			Query query = session.createQuery( jpql.toString() );

			orderList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return orderList;

	}

	public List < Order > findGraphicData( PerformanceGraphicFilter filter ) throws Exception {
		List < Order > orderList = null;

		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Order o" );
			jpql.append( " LEFT JOIN FETCH o.orderItemList oi " );
			jpql.append( " LEFT JOIN FETCH oi.product p " );
			jpql.append( " LEFT JOIN FETCH p.storeCategory sc " );
			jpql.append( " LEFT JOIN FETCH o.deliverAddress da " );
			jpql.append( " LEFT JOIN FETCH da.city ct " );
			jpql.append( " LEFT JOIN FETCH ct.state st " );
			jpql.append( " LEFT JOIN FETCH o.customer cs " );
			jpql.append( " WHERE 1=1 " );

			if( ListUtils.isNotEmpty( filter.getCategoryList() ) ) {
				jpql.append( " AND sc IN ( :categoryList ) " );
			}

			if( ListUtils.isNotEmpty( filter.getCityList() ) ) {
				jpql.append( " AND ct IN ( :cityList ) " );
			}

			if( ListUtils.isNotEmpty( filter.getProductList() ) ) {
				jpql.append( " AND p IN ( :productList ) " );
			}

			if( ListUtils.isNotEmpty( filter.getSex() ) ) {
				jpql.append( " AND cs.sex IN ( :sex ) " );
			}

			if( filter.getState() != null ) {
				jpql.append( " AND st IN ( :state ) " );
			}

			if( filter.getStartDate() != null && filter.getEndDate() != null ) {
				jpql.append( " AND o.insertDate BETWEEN :startDate AND :endDate " );
			}

			Query query = session.createQuery( jpql.toString() );

			if( ListUtils.isNotEmpty( filter.getCategoryList() ) ) {
				query.setParameter( "categoryList", filter.getCategoryList() );
			}

			if( ListUtils.isNotEmpty( filter.getCityList() ) ) {
				query.setParameter( "cityList", filter.getCityList() );
			}

			if( ListUtils.isNotEmpty( filter.getProductList() ) ) {
				query.setParameter( "productList", filter.getProductList() );
			}

			if( ListUtils.isNotEmpty( filter.getSex() ) ) {
				query.setParameter( "sex", filter.getSex() );
			}

			if( filter.getState() != null ) {
				query.setParameter( "state", filter.getState() );
			}

			if( filter.getStartDate() != null && filter.getEndDate() != null ) {
				query.setParameter( "startDate", filter.getStartDate() );
				query.setParameter( "endDate", filter.getEndDate() );
			}

			orderList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}

		return orderList;
	}

	public static void main( String[] args ) throws Exception {
		OrderDAO dao = new OrderDAO();
		List < Order > orders;

		PerformanceGraphicFilter filter = new PerformanceGraphicFilter();

		List < StoreCategory > categoryList = new StoreCategoryDAO().findAll();
		for( StoreCategory storeCategory: categoryList ) {
			if( storeCategory.getDescription().contains( "Mang" ) ) {
				List < StoreCategory > cf = new ArrayList<>();
				cf.add( storeCategory );
				 filter.setCategoryList( cf );
				break;
			}
		}

//		List < State > stateList = new StateDAO().findAll();
//		for( State state: stateList ) {
//			if( state.getDescription().toLowerCase().contains( "alagoas" ) ) {
//				filter.setState( state );
//				break;
//			}
//		}

		orders = dao.findGraphicData( filter );

		for( Order o: orders ) {
			System.out.println( o.getOrderItemList().get( 0 ).getProduct().getName() );
		}

		HibernateUtil.shutdown();
	}

}
