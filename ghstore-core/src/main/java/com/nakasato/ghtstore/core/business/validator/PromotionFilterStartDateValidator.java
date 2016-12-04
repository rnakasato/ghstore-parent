package com.nakasato.ghtstore.core.business.validator;

import com.nakasato.ghstore.domain.filter.impl.PromotionFilter;
import com.nakasato.ghtstore.core.business.Validator;

public class PromotionFilterStartDateValidator extends Validator < PromotionFilter > {

	@Override
	public String validate( PromotionFilter filter ) {
		msg = null;
		if( ( filter.getStartDateBegin() != null && filter.getStartDateEnd() == null )
				|| ( filter.getStartDateEnd() != null && filter.getStartDateBegin() == null ) ) {
			msg = "Para utilizar o filtro de intervalo de data de início da promoção deve ser definido o valor inicial e final ";
		} else if( filter.getStartDateBegin() != null && filter.getStartDateEnd() != null
				&& filter.getStartDateEnd().after( filter.getStartDateBegin() )  ) {
			msg = "O valor inicial do da data de início da promoção deve ser inferior ao valor final";
		}

		return msg;
	}

}
