package com.nakasato.ghtstore.core.business.validator;

import java.util.List;

import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.Validator;

public class PromotionPeriodValidator extends Validator < Promotion > {

	@Override
	public String validate( Promotion promotion ) {
		String msg = null;

		// a validação do preenchimento da data está no
		// PromotionRequiredFieldsValidator
		if( promotion.getEndDate().before( promotion.getStartDate() ) ) {
			msg = "A data do fim da promoção deve ser posterior à data de início";
		} else if( promotion.getStartDate().compareTo( promotion.getEndDate() ) == 0 ) {
			msg = "A data de início da promoção deve ser diferente da data do fim";
		}

		List < Product > productList = promotion.getProductList();

		// caso não seja uma operação de desativação o período deve ser validado
		if( ( promotion.getDisable() == null || ! promotion.getDisable() )
				&& ( promotion.getCancel() == null || ! promotion.getCancel() )) {
			for( Product product: productList ) {
				List < Promotion > promotionList = product.getPromotionList();
				for( Promotion promo: promotionList ) {
					// Erro caso exista uma promoção para um dos produtos da
					// promoção em que:
					// - A data de início da promoção exista dentro do período
					// de
					// outra promoção
					// - A data de fim da promoção exista dentro do período de
					// outra
					// promoção
					if( promo != promotion
							&& ( promotion.getStartDate().after( promo.getStartDate() )
									&& promotion.getStartDate().before( promo.getEndDate() ) )
							|| ( promotion.getEndDate().after( promo.getStartDate() )
									&& promotion.getEndDate().before( promo.getEndDate() ) ) ) {
						msg = "Já existe uma promoção neste período para um ou mais produtos selecionados";
						break;
					}
				}
			}
		}

		return msg;
	}

}
