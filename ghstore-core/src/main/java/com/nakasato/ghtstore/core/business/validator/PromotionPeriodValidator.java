package com.nakasato.ghtstore.core.business.validator;

import java.util.List;

import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.Validator;

public class PromotionPeriodValidator extends Validator < Promotion > {

	@Override
	public String validate( Promotion promotion ) {
		String msg = null;

		// a valida��o do preenchimento da data est� no
		// PromotionRequiredFieldsValidator
		if( promotion.getEndDate().before( promotion.getStartDate() ) ) {
			msg = "A data do fim da promo��o deve ser posterior � data de in�cio";
		} else if( promotion.getStartDate().compareTo( promotion.getEndDate() ) == 0 ) {
			msg = "A data de in�cio da promo��o deve ser diferente da data do fim";
		}

		List < Product > productList = promotion.getProductList();

		// caso n�o seja uma opera��o de desativa��o o per�odo deve ser validado
		if( ( promotion.getDisable() == null || ! promotion.getDisable() )
				&& ( promotion.getCancel() == null || ! promotion.getCancel() )) {
			for( Product product: productList ) {
				List < Promotion > promotionList = product.getPromotionList();
				for( Promotion promo: promotionList ) {
					// Erro caso exista uma promo��o para um dos produtos da
					// promo��o em que:
					// - A data de in�cio da promo��o exista dentro do per�odo
					// de
					// outra promo��o
					// - A data de fim da promo��o exista dentro do per�odo de
					// outra
					// promo��o
					if( promo != promotion
							&& ( promotion.getStartDate().after( promo.getStartDate() )
									&& promotion.getStartDate().before( promo.getEndDate() ) )
							|| ( promotion.getEndDate().after( promo.getStartDate() )
									&& promotion.getEndDate().before( promo.getEndDate() ) ) ) {
						msg = "J� existe uma promo��o neste per�odo para um ou mais produtos selecionados";
						break;
					}
				}
			}
		}

		return msg;
	}

}
