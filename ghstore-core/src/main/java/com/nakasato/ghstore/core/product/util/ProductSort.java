package com.nakasato.ghstore.core.product.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.factory.impl.FactoryComparator;

public class ProductSort {

	public static void sortProducts( List < Product > productList, Integer sortBy, boolean ascend ) {
		Comparator comparator = FactoryComparator.build( sortBy );
		Collections.sort( productList, comparator );

		if( !ascend ) {
			Collections.reverse( productList );
		}
	}
}
