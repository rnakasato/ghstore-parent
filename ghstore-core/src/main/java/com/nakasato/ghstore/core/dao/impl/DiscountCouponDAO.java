package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.DiscountCouponFilter;
import com.nakasato.ghstore.domain.productexchange.DiscountCoupon;

public class DiscountCouponDAO extends AbstractDAO < DiscountCoupon > {

	@Override
	public List < DiscountCoupon > find( AbstractDomainEntity filter ) throws Exception {
		DiscountCouponFilter discountFilter = ( DiscountCouponFilter ) filter;
		List < DiscountCoupon > discountList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM DiscountCoupon d" );
			jpql.append( " JOIN FETCH d.customer c " );
			jpql.append( " WHERE 1=1 " );

			if( discountFilter.getCustomerId() != null ) {
				jpql.append( " AND c.id = :customerId " );
			}

			if( discountFilter.getUsed() != null ) {
				jpql.append( " AND d.used = :used " );
			}

			Query query = session.createQuery( jpql.toString() );

			if( discountFilter.getCustomerId() != null ) {
				query.setParameter( "customerId", discountFilter.getCustomerId() );
			}

			if( discountFilter.getUsed() != null ) {
				query.setParameter( "used", discountFilter.getUsed() );
			}

			discountList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
			e.printStackTrace();
		}
		return discountList;
	}

	@Override
	public List < DiscountCoupon > findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
