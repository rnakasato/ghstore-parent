package com.nakasato.ghtstore.core.business.complementor;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnedItem;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductReturnProductStock extends Complementor < ProductReturn > {

	@Override
	public String complement( ProductReturn productReturn ) {
		try {
			ProductDAO dao = new ProductDAO();
			List < ReturnedItem > returnList = productReturn.getReturnedItems();
			for( ReturnedItem returnedItem: returnList ) {
				Product p = returnedItem.getProduct();
				Integer stock = p.getStock();
				stock += returnedItem.getAmount();
				dao.update( p );
			}

		} catch( Exception e ) {
			e.printStackTrace();
		}

		return null;
	}

}
