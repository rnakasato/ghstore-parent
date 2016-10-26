package com.nakasato.ghstore.core.product.comparator;

import java.util.Comparator;

import com.nakasato.ghstore.domain.product.Product;

public class CompareProductByCat implements Comparator<Product>{
	@Override
	public int compare(Product o1, Product o2) {
		int result = o1.getStoreCategory().getDescription().compareTo(o2.getStoreCategory().getDescription());
		return result;
	}
}
