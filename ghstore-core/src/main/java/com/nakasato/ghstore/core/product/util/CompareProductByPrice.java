package com.nakasato.ghstore.core.product.util;

import java.util.Comparator;

import com.nakasato.ghstore.domain.Product;

public class CompareProductByPrice implements Comparator<Product>{
	public int compare(Product o1, Product o2) {
		int result = o1.getPrice().compareTo(o2.getPrice());
		return result;
	}
}