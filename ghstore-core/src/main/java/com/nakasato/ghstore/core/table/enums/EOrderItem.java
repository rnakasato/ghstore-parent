package com.nakasato.ghstore.core.table.enums;

public enum EOrderItem {
	OI();
	public static final String TABLE_NAME="DE_ORDERITEM";
	public static final String PK="IDORDERITEM";
	public static final String PRODUCT_FK="IDPRODUCT";
	public static final String AMOUNT="AMOUNT";	
}
