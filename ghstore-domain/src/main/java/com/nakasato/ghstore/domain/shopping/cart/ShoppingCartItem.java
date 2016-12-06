package com.nakasato.ghstore.domain.shopping.cart;

import com.nakasato.ghstore.domain.StoreItem;

public class ShoppingCartItem extends StoreItem {
	@Override
	public boolean equals( Object obj ) {
		boolean equal = false;
		if(obj instanceof ShoppingCartItem ){
			ShoppingCartItem item = (ShoppingCartItem) obj;
			if(this.getProduct() != null && item.getProduct() != null){
				equal = this.getProduct().equals( item.getProduct() );
			}
		}
		return equal;
	}
	
	

}
