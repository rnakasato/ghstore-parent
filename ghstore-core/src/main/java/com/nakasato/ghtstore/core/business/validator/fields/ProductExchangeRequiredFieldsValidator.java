package com.nakasato.ghtstore.core.business.validator.fields;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghtstore.core.business.FieldsValidator;

public class ProductExchangeRequiredFieldsValidator extends FieldsValidator<ProductExchange>{

	@Override
	public String validate(ProductExchange entity) {
		if(StringUtils.isEmpty(entity.getReason())){
			appendMsg("Motivo");
		}
		
		return getMessage();
	}

}
