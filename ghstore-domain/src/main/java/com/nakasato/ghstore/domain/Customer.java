package com.nakasato.ghstore.domain;

import java.util.List;

public class Customer extends User {
	private Wishlist wishlist;
	private List<Order> orderList;
	private ShoppingCart shoppingCart;
	private List<Address> deliveryAddressList;

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public List<Address> getDeliveryAddressList() {
		return deliveryAddressList;
	}

	public void setDeliveryAddressList(List<Address> deliveryAddressList) {
		this.deliveryAddressList = deliveryAddressList;
	}

}
