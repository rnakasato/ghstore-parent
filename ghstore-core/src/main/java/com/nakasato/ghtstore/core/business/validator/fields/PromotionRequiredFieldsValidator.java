package com.nakasato.ghtstore.core.business.validator.fields;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.FieldsValidator;

public class PromotionRequiredFieldsValidator extends FieldsValidator < Promotion > {

	@Override
	public String validate( Promotion promotion ) {
		super.init();
		
		if( promotion.getActive() == null ) {
			appendMsg( "Status" );
		}

		if( StringUtils.isEmpty( promotion.getDescription() ) ) {
			appendMsg( "Nome da promoção" );
		}
		
		if(promotion.getDiscountPercentage() == null){
			appendMsg( "Início da promoção" );
		}
		
		if(promotion.getStartDate() == null){
			appendMsg( "Fim da promoção" );
		}
		
		if(promotion.getEndDate() == null){
			appendMsg( "Desconto (%)" );
		}
		
		if(ListUtils.isEmpty( promotion.getProductList() )){
			appendMsg( "Produtos na promoção" );
		}
		
		return getMessage();
	}

}
