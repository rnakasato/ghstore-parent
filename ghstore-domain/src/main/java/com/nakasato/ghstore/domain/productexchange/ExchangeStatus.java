package com.nakasato.ghstore.domain.productexchange;

import com.nakasato.ghstore.domain.DomainSpecificEntity;

public class ExchangeStatus extends DomainSpecificEntity {
	public static final String COD_AGUARDANDO_RECEBIMENTO = "AGR";
	public static final String COD_RECEBIDO = "REC";
	public static final String COD_ACEITO = "ACT";
	public static final String COD_REJEITADO = "REJ";
	public static final String COD_EM_TRANSPORTE = "TRA";
	public static final String COD_ENTREGUE = "TRE";

}
