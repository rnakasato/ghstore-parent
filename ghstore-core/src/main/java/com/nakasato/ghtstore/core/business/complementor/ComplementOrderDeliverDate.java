package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderStatus;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementOrderDeliverDate extends Complementor < Order >{

	@Override
	public String complement( Order order ) {
		
		if(order.getOrderStatus() != null && order.getOrderStatus().getCode().equals( OrderStatus.COD_ENTREGE )){
			order.setDeliverDate( new Date() );
		}
		return null;
	}

}
