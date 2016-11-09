package com.nakasato.ghstore.core.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nakasato.ghstore.core.adapter.Adapter;
import com.nakasato.ghstore.core.dao.impl.OrderDAO;
import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.core.util.DateFormatUtils;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.enums.EAxisX;
import com.nakasato.ghstore.domain.enums.EAxisY;
import com.nakasato.ghstore.domain.enums.ESex;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.filter.impl.PerformanceGraphicFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.performance.graphic.AxisData;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;

public class SexIndicatorAdapter extends DefaultIndicatorAdapter implements Adapter < PerformanceGraphicCarrier, List < PerformanceGraphicData > > {

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

	public static void main( String[] args ) throws Exception {
		PerformanceGraphicCarrier carrier = new PerformanceGraphicCarrier();
		PerformanceGraphicFilter filter = new PerformanceGraphicFilter();
		filter.setAxisX( EAxisX.DAYS.getCode() );
		filter.setAxisY( EAxisY.SOLD_AMOUNT.getCode() );
		carrier.setFilter( filter );

		OrderDAO dao = new OrderDAO();
		List < Order > orderList = dao.find( new OrderFilter() );
		carrier.setOrderList( orderList );

		List < PerformanceGraphicData > dataList = new SexIndicatorAdapter().adapt( carrier );

		for( PerformanceGraphicData p: dataList ) {
			System.out.println( p.getDescription() );
		}

		HibernateUtil.shutdown();

	}
}
