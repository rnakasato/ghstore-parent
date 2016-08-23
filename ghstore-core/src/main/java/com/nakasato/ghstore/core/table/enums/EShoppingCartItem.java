package com.nakasato.ghstore.core.table.enums;

public enum EShoppingCartItem {
	CI();
	public static final String TABLE_NAME = "DE_CARTITEM";
	public static final String PK="IDCARTITEM";
	public static final String SHOPPINGCART_ID="IDSHOPPINGCART";
	public static final String PRODUCT_ID="IDPRODUCT";
	public static final String AMOUNT="AMOUNT";
	public static final String INSERTDATE="INSERTDATE";
}
