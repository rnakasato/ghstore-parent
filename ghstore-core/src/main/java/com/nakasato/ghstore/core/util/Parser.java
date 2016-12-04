package com.nakasato.ghstore.core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.payment.utils.FreteUtil;
import com.nakasato.ghstore.domain.filter.impl.OrderStatusFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.order.OrderStatus;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCartItem;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class Parser {

	/**
	 * Converte um objeto ShoppingCart para Order, caso o valor total do
	 * shoppingCart seja 0 ou nulo, será retornado null;
	 * 
	 * @param shoppingCart
	 * @return
	 */
	public static Order parseShoppingCartToOrder( ShoppingCart shoppingCart ) {
		Order order = null;

		if( shoppingCart.getTotalValue() != null && shoppingCart.getTotalValue() > 0 ) {
			order = new Order();
			List < OrderItem > orderItemList = new ArrayList();
			try {
				OrderStatusFilter statusFilter = new OrderStatusFilter();
				statusFilter.setCode( OrderStatus.COD_AGUARDANDO_PAGAMENTO );
				ICommand commandFind = FactoryCommand.build( statusFilter, EOperation.FIND );
				Result result = commandFind.execute();
				List < OrderStatus > statusList = result.getEntityList();
				OrderStatus status = null;
				if( statusList != null && ! statusList.isEmpty() ) {
					status = statusList.get( 0 );
				}
				Double totalValue = 0D;
				Double shippingCost =  FreteUtil.getShippingCost( shoppingCart );
				
				order.setShippingCost( shippingCost );
				order.setDiscountValue( shoppingCart.getDiscountValue() );
				totalValue += shippingCost;
				for( ShoppingCartItem cartItem: shoppingCart.getShoppingCartList() ) {
					OrderItem orderItem = new OrderItem();
					orderItem.setAmount( cartItem.getAmount() );
					orderItem.setProduct( cartItem.getProduct() );
					orderItem.setTotalValue( cartItem.getTotalValue() );
					orderItem.setTotalWeigth( cartItem.getTotalWeigth() );
					orderItem.setOrder( order );
					orderItem.setItemValue( cartItem.getItemValue() );
					orderItemList.add( orderItem );
					totalValue += cartItem.getTotalValue();
				}
				order.setDiscount( shoppingCart.isDiscount() );
				order.setInsertDate( new Date() );
				order.setCustomer( shoppingCart.getOwner() );
				order.setOrderStatus( status );
				order.setTotalValue( totalValue );
				order.setCode( CSPRNGUtil.generateHex( 4 ) );
				order.setOrderItemList( orderItemList );
				order.setDeliverAddress( shoppingCart.getAddress() );

			} catch( Exception e ) {
				e.printStackTrace();
			}

		}
		return order;
	}

}
