package com.nakasato.ghstore.domain.user;

import java.util.List;

import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.productexchange.DiscountCoupon;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;

public class Customer extends User {
	private Wishlist wishlist;
	private List < Order > orderList;
	private ShoppingCart shoppingCart;
	private List < Address > deliveryAddressList;
	private List < DiscountCoupon > coupons;

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist( Wishlist wishlist ) {
		this.wishlist =wishlist;
	}

	public List < Order > getOrderList() {
		return orderList;
	}

	public void setOrderList( List < Order > orderList ) {
		this.orderList =orderList;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart( ShoppingCart shoppingCart ) {
		this.shoppingCart =shoppingCart;
	}

	public List < Address > getDeliveryAddressList() {
		return deliveryAddressList;
	}

	public void setDeliveryAddressList( List < Address > deliveryAddressList ) {
		this.deliveryAddressList =deliveryAddressList;
	}

	public List < DiscountCoupon > getCoupons() {
		return coupons;
	}

	public void setCoupons( List < DiscountCoupon > coupons ) {
		this.coupons =coupons;
	}

}
