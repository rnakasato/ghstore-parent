package com.nakasato.ghstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AbstractDomainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ShoppingCartItem> shoppingCartList;
	private Double totalValue;
	private Long totalWeight;
	private Address address;
	private User owner;

	public List<ShoppingCartItem> getShoppingCartList() {
		return shoppingCartList;
	}

	public void setShoppingCartList(List<ShoppingCartItem> shoppingCartList) {
		this.shoppingCartList = shoppingCartList;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public void addItem(ShoppingCartItem cartItem) {
		if (shoppingCartList == null) {
			shoppingCartList = new ArrayList<>();
		}
		shoppingCartList.add(cartItem);
	}

	public void removeItem(ShoppingCartItem cartItem) {
		if (shoppingCartList != null) {
			shoppingCartList.remove(cartItem);
		}
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getTotalWeight() {
		return totalWeight;
	}
	
	public void setTotalWeight(Long totalWeight) {
		this.totalWeight = totalWeight;
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

}
