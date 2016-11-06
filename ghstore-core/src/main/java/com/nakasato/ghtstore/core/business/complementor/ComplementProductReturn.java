package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnedItem;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductReturn extends Complementor < ProductReturn > {

	@ Override
	public String complement( ProductReturn productReturn ) {
		productReturn.setInsertDate( new Date() );
		Double totalValue =0D;
		for( ReturnedItem rt: productReturn.getReturnedItems() ) {
			rt.setProductReturn( productReturn );
			rt.setInsertDate( new Date() );
			rt.setTotalValue( rt.getProduct().getPrice() *rt.getAmount() );
			totalValue +=rt.getTotalValue();
		}
		productReturn.setTotalValue( totalValue );

		return null;
	}

}
