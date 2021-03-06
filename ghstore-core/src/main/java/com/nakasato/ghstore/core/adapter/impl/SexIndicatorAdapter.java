package com.nakasato.ghstore.core.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.enums.ESex;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;

public class SexIndicatorAdapter extends DefaultIndicatorAdapter {

	@Override
	public List < PerformanceGraphicData > adapt( PerformanceGraphicCarrier carrier ) {
		List < PerformanceGraphicData > dataList = null;

		// Inicializa mapa com PerformanceGraphicData para sexo M e F
		Map < String, PerformanceGraphicData > dataMap = new HashMap<>();

		PerformanceGraphicData dataM = new PerformanceGraphicData();
		dataM.setDescription( ESex.M.getDescription() );
		dataM.setAxisData( new HashMap<>() );
		dataM.setAxisTotal( 0 );

		PerformanceGraphicData dataF = new PerformanceGraphicData();
		dataF.setDescription( ESex.F.getDescription() );
		dataF.setAxisData( new HashMap<>() );
		dataF.setAxisTotal( 0 );

		dataMap.put( ESex.M.getCode(), dataM );
		dataMap.put( ESex.F.getCode(), dataF );

		// Itera pedidos para agrupar os valores
		for( Order order: carrier.getOrderList() ) {
			PerformanceGraphicData data = dataMap.get( order.getCustomer().getSex() );

			for( OrderItem item: order.getOrderItemList() ) {
				fillAxisData( data, carrier.getFilter(), order, item );
			}

		}

		dataList = new ArrayList<>( dataMap.values() );
		return dataList;
	}

}
