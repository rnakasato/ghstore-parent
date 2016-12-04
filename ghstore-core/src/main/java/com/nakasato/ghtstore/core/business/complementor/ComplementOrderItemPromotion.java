package com.nakasato.ghtstore.core.business.complementor;

import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementOrderItemPromotion extends Complementor < Order > {

	@Override
	public String complement( Order order ) {
		try {
			for( OrderItem orderItem: order.getOrderItemList() ) {
				orderItem.setPromotion( orderItem.getProduct().getActivePromotion() );
			}

		} catch( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
