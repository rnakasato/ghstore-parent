package com.nakasato.ghtstore.core.business.complementor;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productexchange.ExchangeItem;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnedItem;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductExchangeProductStock extends Complementor<ProductExchange> {

	@Override
	public String complement(ProductExchange productExchange) {
		try {
			ProductDAO dao = new ProductDAO();
			List<ExchangeItem> exchangeList = productExchange.getExchangeItems();
			for (ExchangeItem ex : exchangeList) {
				Product p = ex.getProduct();
				Integer stock = p.getStock();
				stock -= ex.getAmount();
				dao.update(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
