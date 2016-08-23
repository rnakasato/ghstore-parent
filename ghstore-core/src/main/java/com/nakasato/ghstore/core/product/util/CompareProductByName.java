package com.nakasato.ghstore.core.product.util;

import java.util.Comparator;

import com.nakasato.ghstore.domain.Product;

public class CompareProductByName implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		int result = o1.getName().compareTo(o2.getName());
		return result;
	}

}
