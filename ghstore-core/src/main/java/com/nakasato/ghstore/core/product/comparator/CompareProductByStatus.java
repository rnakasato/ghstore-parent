package com.nakasato.ghstore.core.product.comparator;

import java.util.Comparator;

import com.nakasato.ghstore.domain.product.Product;

public class CompareProductByStatus implements Comparator < Product > {
	public int compare( Product o1, Product o2 ) {
		int result = o1.getStatus().compareTo( o2.getStatus() );
		return result;
	}
}
