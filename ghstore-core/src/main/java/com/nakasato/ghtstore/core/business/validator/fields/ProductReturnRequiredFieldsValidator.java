package com.nakasato.ghtstore.core.business.validator.fields;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghtstore.core.business.FieldsValidator;

public class ProductReturnRequiredFieldsValidator extends FieldsValidator<ProductReturn>{

	@Override
	public String validate(ProductReturn entity) {
		if(StringUtils.isEmpty(entity.getReason())){
			appendMsg("Motivo");
		}
		
		return getMessage();
	}

}
