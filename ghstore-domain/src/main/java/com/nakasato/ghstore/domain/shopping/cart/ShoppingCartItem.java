package com.nakasato.ghstore.domain.shopping.cart;

import com.nakasato.ghstore.domain.StoreItem;

public class ShoppingCartItem extends StoreItem {
	private boolean reserved;

	// TODO verifica se o produto está reservado
	public boolean isReserved() {
		return reserved;
	}

	public void setReserved( boolean reserved ) {
		this.reserved = reserved;
	}

}
