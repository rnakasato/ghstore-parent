package com.nakasato.ghstore.domain.product;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class StoreCategory extends AbstractCategory implements Serializable {
	public static String ACTION_FIGURE ="Colecion�veis";
	public static String ACCESSORY ="Acess�rios";
	public static String HQ ="HQs";
	public static String MANGA ="Mang�s";
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
