package com.nakasato.ghtstore.core.business.validator;

import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.Validator;

public class PromotionDiscountValidator extends Validator < Promotion > {

	@Override
	public String validate( Promotion promotion ) {
		msg = null;
		if( ( promotion.getDiscountPercentage() != null
				&& ( promotion.getDiscountPercentage() <= 0D || promotion.getDiscountPercentage() >= 100 ) ) ) {
			msg = "O valor do desconto deve ser acima de 1 e inferior a 100";
		}

		return msg;
	}

}
