package com.nakasato.ghstore.core.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nakasato.ghstore.core.adapter.Adapter;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;

public class GeneralIndicatorAdapter extends DefaultIndicatorAdapter
		implements Adapter < PerformanceGraphicCarrier, List < PerformanceGraphicData > > {

	@Override
	public List < PerformanceGraphicData > adapt( PerformanceGraphicCarrier carrier ) {
		List < PerformanceGraphicData > dataList = null;

		Map < String, PerformanceGraphicData > dataMap = new HashMap<>();

		for( Order order: carrier.getOrderList() ) {
			for( OrderItem item: order.getOrderItemList() ) {
				// Verifica se já existe registro para o produto
				PerformanceGraphicData data = dataMap.get( item.getProduct().getCode() );
				if( data == null ) {
					// caso não exista, será criado um novo registro para o
					// produto
					data = new PerformanceGraphicData();
					data.setDescription( item.getProduct().getName() );
					data.setAxisData( new HashMap<>() );
					data.setAxisTotal( 0 );
					dataMap.put( item.getProduct().getCode(), data );
				}

				fillAxisData( data, carrier.getFilter(), order, item );

			}

		}

		dataList = new ArrayList<>( dataMap.values() );
		return dataList;
	}

}
