package com.nakasato.ghtstore.core.business.complementor;

import java.util.List;
import java.util.Map;

import com.nakasato.ghstore.core.adapter.Adapter;
import com.nakasato.ghstore.core.dao.impl.OrderDAO;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.enums.ELines;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;
import com.nakasato.ghstore.factory.impl.FactoryIndicatorAdapter;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementPerformanceGraphicCarrier extends Complementor < PerformanceGraphicCarrier > {

	private OrderDAO orderDAO = new OrderDAO();

	@Override
	public String complement( PerformanceGraphicCarrier carrier ) {
		String msg = null;

		Map < String, PerformanceGraphicData > dataMap = null;
		try {
			List < Order > orderList = orderDAO.findGraphicData( carrier.getFilter() );
			if( ListUtils.isEmpty( orderList ) ) {
				msg = "Não há resultados para os filtros selecionados";
			} else {
				carrier.setOrderList( orderList );
				
				Adapter adapter = FactoryIndicatorAdapter.build( ELines.getValue( carrier.getFilter().getLines() ) );
				List < PerformanceGraphicData > dataList = ( List < PerformanceGraphicData > ) adapter
						.adapt( carrier );
				carrier.setDataList( dataList );
			}

		} catch( Exception e ) {
			e.printStackTrace();
		}

		return msg;
	}

}
