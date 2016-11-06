package com.nakasato.ghstore.domain.productexchange;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.user.Customer;

public class DiscountCoupon extends AbstractDomainEntity {
	private Customer customer;
	private boolean used;
	private Order order;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer =customer;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed( boolean used ) {
		this.used =used;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder( Order order ) {
		this.order =order;
	}

}
