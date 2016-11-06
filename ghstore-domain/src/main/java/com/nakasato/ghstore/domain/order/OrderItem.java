package com.nakasato.ghstore.domain.order;

import com.nakasato.ghstore.domain.StoreItem;

public class OrderItem extends StoreItem {
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder( Order order ) {
		this.order =order;
	}

}
