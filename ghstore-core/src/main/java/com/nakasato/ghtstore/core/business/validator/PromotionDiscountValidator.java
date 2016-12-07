package com.nakasato.ghtstore.core.business.validator;

import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.Validator;

public class PromotionDiscountValidator extends Validator < Promotion > {

	@Override
	public String validate( Promotion promotion ) {
		msg = null;
		if( ( promotion.getDiscountPercentage() != null
				&& ( promotion.getDiscountPercentage() <= 0D || promotion.getDiscountPercentage() > 90 ) ) ) {
			msg = "O valor do desconto deve ser entre 1 e 90";
		}

		return msg;
	}

}
