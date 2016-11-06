package com.nakasato.ghstore.domain.product;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class StoreCategory extends AbstractCategory implements Serializable {
	public static String ACTION_FIGURE ="Colecionáveis";
	public static String ACCESSORY ="Acessórios";
	public static String HQ ="HQs";
	public static String MANGA ="Mangás";
	public static String SHIRT ="Camisas";
	public static String BOARDGAME ="Jogos de Tabuleiro";
	/**
	 * 
	 */
	private static final long serialVersionUID =1L;

	@ Override
	public boolean isEmpty() {
		boolean isEmpty =false;

		if( this.getId() ==null &&StringUtils.isEmpty( this.getDescription() ) ) {
			isEmpty =true;
		}

		return isEmpty;
	}

}
