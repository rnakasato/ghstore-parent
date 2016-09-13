package com.nakasato.ghstore.factory.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.nakasato.core.util.enums.EComparator;
import com.nakasato.ghstore.core.product.comparator.CompareProductByCat;
import com.nakasato.ghstore.core.product.comparator.CompareProductByName;
import com.nakasato.ghstore.core.product.comparator.CompareProductByPrice;
import com.nakasato.ghstore.core.product.comparator.CompareProductBySell;
import com.nakasato.ghstore.core.product.comparator.CompareProductByStatus;
import com.nakasato.ghstore.core.product.comparator.CompareProductByStock;

public class FactoryComparator {
private static Map<Integer,Comparator> comparatorMap;
	
	private static void initMap(){
		if(comparatorMap == null){
			comparatorMap = new HashMap<>();
			comparatorMap.put(EComparator.PRODUCT_NAME, new CompareProductByName());
			comparatorMap.put(EComparator.PRODUCT_CATEGORY, new CompareProductByCat());
			comparatorMap.put(EComparator.PRODUCT_PRICE, new CompareProductByPrice());
			comparatorMap.put(EComparator.PRODUCT_STATUS, new CompareProductByStatus());
			comparatorMap.put(EComparator.PRODUCT_STOCK, new CompareProductByStock());
			comparatorMap.put(EComparator.PRODUCT_SELL, new CompareProductBySell());
		}
	}
	
	public static Comparator build(Integer comparatorType){
		initMap();
		Comparator c = comparatorMap.get(comparatorType);
		return c;
	}
}
	