package com.nakasato.ghstore.core.product.comparator;

import java.util.Comparator;

import com.nakasato.ghstore.domain.product.Product;

public class CompareProductByInsertDate implements Comparator < Product > {
	@Override
	public int compare( Product o1, Product o2 ) {
		int result = o1.getInsertDate().compareTo( o2.getInsertDate() );
		return result;
	}
}
