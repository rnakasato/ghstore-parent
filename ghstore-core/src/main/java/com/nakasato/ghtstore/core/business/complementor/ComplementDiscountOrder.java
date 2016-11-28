package com.nakasato.ghtstore.core.business.complementor;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.DiscountCouponDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.productexchange.DiscountCoupon;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementDiscountOrder extends Complementor < Order > {

	@Override
	public String complement( Order entity ) {
		try {
			if( entity.isDiscount() ) {
				Customer customer = entity.getCustomer();
				List < DiscountCoupon > coupons = customer.getCoupons();
				DiscountCoupon usedCoupon = null;
				if( coupons != null ) {
					for( DiscountCoupon discountCoupon: coupons ) {
						if( ! discountCoupon.isUsed() ) {
							usedCoupon = discountCoupon;
							usedCoupon.setUsed( true );
							break;
						}
					}
				}
				if( usedCoupon != null ) {
					DiscountCouponDAO dao = new DiscountCouponDAO();
					dao.setSession( SessionThreadLocal.getSession() );
					dao.update( usedCoupon );
				}
			}
		} catch( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
