package com.nakasato.ghstore.domain;

public class OrderItem extends StoreItem {
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
