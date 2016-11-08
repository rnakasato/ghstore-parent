package com.nakasato.ghstore.factory.impl;

import java.util.HashMap;
import java.util.Map;

import com.nakasato.ghstore.core.adapter.Adapter;
import com.nakasato.ghstore.core.adapter.impl.CategoryIndicatorAdapter;
import com.nakasato.ghstore.core.adapter.impl.CityIndicatorAdapter;
import com.nakasato.ghstore.core.adapter.impl.GeneralIndicatorAdapter;
import com.nakasato.ghstore.core.adapter.impl.SexIndicatorAdapter;
import com.nakasato.ghstore.domain.enums.ELines;

public class FactoryIndicatorAdapter {
	private static Map < ELines, Adapter > adapterMap;

	private static void initMap() {
		if( adapterMap == null ) {
			adapterMap = new HashMap<>();
			adapterMap.put( ELines.GENERAL_SUMMARY, new GeneralIndicatorAdapter() );
			adapterMap.put( ELines.CATEGORY, new CategoryIndicatorAdapter() );
			adapterMap.put( ELines.SEX, new SexIndicatorAdapter() );
			adapterMap.put( ELines.TOP_TEN_CITIES, new CityIndicatorAdapter() );

		}
	}

	public static Adapter build( ELines lineType ) {
		initMap();
		Adapter a = adapterMap.get( lineType );
		return a;
	}

}
