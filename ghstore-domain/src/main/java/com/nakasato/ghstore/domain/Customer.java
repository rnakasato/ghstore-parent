package com.nakasato.ghstore.domain;

import java.util.Set;

public class Customer extends User {
	private Wishlist wishlist;
	private Set<Order> orderList;
	private ShoppingCart shoppingCart;
	private Set<Address> deliveryAddressList;

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	public Set<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(Set<Order> orderList) {
		this.orderList = orderList;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Set<Address> getDeliveryAddressList() {
		return deliveryAddressList;
	}

	public void setDeliveryAddressList(Set<Address> deliveryAddressList) {
		this.deliveryAddressList = deliveryAddressList;
	}

}
