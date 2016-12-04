package com.nakasato.ghstore.domain.productreturn;

import com.nakasato.ghstore.domain.DomainSpecificEntity;

public class ReturnStatus extends DomainSpecificEntity {
	public static final String COD_AGUARDANDO_RECEBIMENTO = "AGR";
	public static final String COD_RECEBIDO = "REC";
	public static final String COD_ACEITO = "ACT";
	public static final String COD_REJEITADO = "REJ";
	public static final String COD_EM_RETORNO = "PRE";
	public static final String COD_RETORNADO = "DRE";

}
