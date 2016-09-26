package com.nakasato.ghstore.core.util;

import java.util.HashSet;
import java.util.Set;

import com.nakasato.ghstore.domain.Order;
import com.nakasato.ghstore.domain.OrderItem;
import com.nakasato.ghstore.domain.ShoppingCart;
import com.nakasato.ghstore.domain.ShoppingCartItem;

public class Parser {
	
	/**
	 * Converte um objeto ShoppingCart para Order, caso o valor total 
	 * do shoppingCart seja 0 ou nulo, será retornado null;
	 * @param shoppingCart
	 * @return
	 */
	public static Order parseShoppingCartToOrder(ShoppingCart shoppingCart){
		Order order = null;
		
		if(shoppingCart.getTotalValue() != null && shoppingCart.getTotalValue() > 0 ){
			order = new Order();			
			Set<OrderItem> orderItemList = new HashSet<>(); 
			
			for(ShoppingCartItem cartItem: shoppingCart.getShoppingCartList()){
				OrderItem orderItem = new OrderItem();
				orderItem.setAmount(cartItem.getAmount());
				orderItem.setProduct(cartItem.getProduct());
				orderItem.setTotalValue(cartItem.getTotalValue());
				orderItemList.add(orderItem);
			}
			order.setOrderItemList(orderItemList);
		}
		return order;
	}
	
}
