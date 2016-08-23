package com.nakasato.ghstore.core.table.enums;

/**
 * ESubcategory representa a tabela DE_SUBCATEGORY
 * @author Rafael
 *
 */
public enum ESubcategory {
	S();
	public static final String TABLE_NAME = "DE_SUBCATEGORY";
	public static final String PK = "IDSUBCATEGORY";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String INSERTDATE = "INSERTDATE";
	public static final String STORECATEGORY_FK = "IDSTORECATEGORY";
}
