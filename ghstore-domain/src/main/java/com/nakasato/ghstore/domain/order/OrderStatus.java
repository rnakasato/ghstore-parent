package com.nakasato.ghstore.domain.order;

import com.nakasato.ghstore.domain.DomainSpecificEntity;

public class OrderStatus extends DomainSpecificEntity {
	public static final String COD_AGUARDANDO_PAGAMENTO ="APG";
	public static final String COD_TRANSPORTE ="TRA";
	public static final String COD_ENTREGE ="ETR";
	public static final String COD_CANCELADO ="CAN";
	public static final String COD_PAGO ="PGO";

}
