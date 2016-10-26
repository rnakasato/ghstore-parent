package com.nakasato.ghtstore.core.business.validator;

import java.util.List;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.productexchange.ExchangeItem;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnedItem;
import com.nakasato.ghtstore.core.business.Validator;

public class ProductExchangeAmountValidator extends Validator<ProductExchange> {

	@Override
	public String validate(ProductExchange entity) {
		List<ExchangeItem> exchangeList = entity.getExchangeItems();
		if (ListUtils.isListEmpty(exchangeList)) {
			msg = "Deve haver ao menos um item selecionado e com quantidade definida para troca";
		} else {
			boolean empty = true;
			for (ExchangeItem exchangeItem : exchangeList) {
				if (exchangeItem.getAmount() > 0) {
					empty = false;
					break;
				}
			}
			if (empty) {
				msg = "Nenhuma quantidade foi selecionada para troca";
			}
		}
		return msg;
	}

}
