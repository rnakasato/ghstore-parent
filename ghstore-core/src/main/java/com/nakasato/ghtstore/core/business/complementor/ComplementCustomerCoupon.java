package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.core.dao.impl.DiscountCouponDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.productexchange.DiscountCoupon;
import com.nakasato.ghstore.domain.productexchange.ExchangeStatus;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementCustomerCoupon extends Complementor < ProductExchange > {

	@Override
	public String complement( ProductExchange productExchange ) {

		if( productExchange.getStatus() != null
				&& productExchange.getStatus().getCode().equals( ExchangeStatus.COD_ACEITO ) ) {
			try {
				Customer customer = productExchange.getOrder().getCustomer();

				DiscountCoupon coupon = new DiscountCoupon();
				coupon.setCustomer( customer );
				coupon.setOrder( productExchange.getOrder() );
				coupon.setUsed( false );
				coupon.setInsertDate( new Date() );

				DiscountCouponDAO dao = new DiscountCouponDAO();
				dao.setSession( SessionThreadLocal.getSession() );
				dao.save( coupon );

			} catch( Exception e ) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
