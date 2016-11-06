package com.nakasato.ghstore.domain.carrier;

import com.nakasato.ghstore.domain.EntityCarrier;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;
import com.nakasato.ghstore.domain.user.Customer;

public class PaymentCreationCarrier extends EntityCarrier {
	private ShoppingCart cart;
	private Customer loggedUser;
	private String paymentAddress;

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart( ShoppingCart cart ) {
		this.cart =cart;
	}

	public Customer getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser( Customer loggedUser ) {
		this.loggedUser =loggedUser;
	}

	public String getPaymentAddress() {
		return paymentAddress;
	}

	public void setPaymentAddress( String paymentAddress ) {
		this.paymentAddress =paymentAddress;
	}

}
