package com.nakasato.ghtstore.core.business.validator;

import java.util.List;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnedItem;
import com.nakasato.ghtstore.core.business.Validator;

public class ProductReturnAmountValidator extends Validator < ProductReturn > {

	@Override
	public String validate( ProductReturn entity ) {
		List < ReturnedItem > returnList = entity.getReturnedItems();
		if( ListUtils.isEmpty( returnList ) ) {
			msg = "Deve haver ao menos um item selecionado e com quantidade definida para devolução";
		} else {
			boolean empty = true;
			for( ReturnedItem returnedItem: returnList ) {
				if( returnedItem.getAmount() > 0 ) {
					empty = false;
					break;
				}
			}
			if( empty ) {
				msg = "Nenhuma quantidade foi selecionada para devolução";
			}
		}
		return msg;
	}

}
