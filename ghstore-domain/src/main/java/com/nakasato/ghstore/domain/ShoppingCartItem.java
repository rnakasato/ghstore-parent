package com.nakasato.ghstore.domain;

public class ShoppingCartItem extends StoreItem{
	private boolean reserved;
	
	// TODO verifica se o produto est� reservado
	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
	
}
