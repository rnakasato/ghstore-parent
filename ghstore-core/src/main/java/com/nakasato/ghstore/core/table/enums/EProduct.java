package com.nakasato.ghstore.core.table.enums;

public enum EProduct {
	P();
	public static final String TABLE_NAME="DE_PRODUCT";
	public static final String PK="IDPRODUCT";
	public static final String DESCRIPTION="DESCRIPTION";
	public static final String INSERTDATE="INSERTDATE";
	public static final String UPDATEDATE="UPDATEDATE";
	public static final String PRODUCT_NAME="NAME";
	public static final String PRICE="PRICE";
	public static final String STOCK="STOCK";
	public static final String IMAGE="IMAGE_PATH";
	public static final String STORECATEGORY_FK="IDSTORECATEGORY";
	public static final String SUBCATEGORY_FK="IDSUBCATEGORY";
}
