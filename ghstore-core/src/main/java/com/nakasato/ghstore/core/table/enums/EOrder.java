package com.nakasato.ghstore.core.table.enums;

public enum EOrder {
	OR();
	public static final String TABLE_NAME="DE_ORDER";
	public static final String PK="IDORDER";
	public static final String COD_ORDER="COD_ORDER";
	public static final String USER_FK="IDUSER";
	public static final String ORDERSTATUS_FK="IDORDERSTATUS";
	public static final String INSERTDATE="INSERTDATE";
	public static final String DELIVERDATE="DELIVERDATE";
}
