package com.nakasato.ghtstore.core.business.validator;

import java.util.Date;

import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.Validator;

public class PromotionEndDateValidator extends Validator < Promotion > {

	@Override
	public String validate( Promotion promotion ) {
		msg = null;
		if( ( ( promotion.getDisable() == null || ! promotion.getDisable() )
				&& ( promotion.getCancel() == null || ! promotion.getCancel() ) )
				&& promotion.getEndDate().before( new Date() ) ) {
			msg = "A data da de fim da promoção deve ser posterior à data atual";
		}

		return msg;
	}

}
