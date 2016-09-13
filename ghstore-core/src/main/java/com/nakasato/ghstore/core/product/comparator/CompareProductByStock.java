package com.nakasato.ghstore.core.product.comparator;

import java.util.Comparator;

import com.nakasato.ghstore.domain.Product;

public class CompareProductByStock implements Comparator<Product>{
	public int compare(Product o1, Product o2) {
		int result = o1.getStock().compareTo(o2.getStock());
		return result;
	}
}
