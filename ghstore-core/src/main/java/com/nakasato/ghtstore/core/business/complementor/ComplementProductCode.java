package com.nakasato.ghtstore.core.business.complementor;

import com.nakasato.ghstore.core.util.CSPRNGUtil;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductCode extends Complementor < Product > {

	@ Override
	public String complement( Product entity ) {
		entity.setCode( CSPRNGUtil.generateHex( CSPRNGUtil.BYTE_06 ) );
		return null;
	}

}
