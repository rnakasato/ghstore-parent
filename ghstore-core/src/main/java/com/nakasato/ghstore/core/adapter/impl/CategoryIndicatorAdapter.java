package com.nakasato.ghstore.core.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nakasato.ghstore.core.dao.impl.StoreCategoryDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;
import com.nakasato.ghstore.domain.product.StoreCategory;

public class CategoryIndicatorAdapter extends DefaultIndicatorAdapter {

	@Override
	public List < PerformanceGraphicData > adapt( PerformanceGraphicCarrier carrier ) {
		List < PerformanceGraphicData > dataList = null;

		Map < String, PerformanceGraphicData > dataMap = new HashMap<>();

		// Inicializa mapa com PerformanceGraphicData para todas as
		// categorias
		StoreCategoryDAO dao = new StoreCategoryDAO();
		dao.setSession( SessionThreadLocal.getSession() );
		List < StoreCategory > categoryList = dao.findAll();

		for( StoreCategory s: categoryList ) {
			PerformanceGraphicData categoryData = new PerformanceGraphicData();
			categoryData.setDescription( s.getDescription() );
			categoryData.setAxisData( new HashMap<>() );
			categoryData.setAxisTotal( 0 );
			dataMap.put( s.getDescription(), categoryData );
		}

		for( Order order: carrier.getOrderList() ) {
			for( OrderItem item: order.getOrderItemList() ) {
				// Verifica se j� existe registro para o produto
				PerformanceGraphicData data = dataMap.get( item.getProduct().getStoreCategory().getDescription() );
				fillAxisData( data, carrier.getFilter(), order, item );
			}

		}

		dataList = new ArrayList<>( dataMap.values() );

		return dataList;
	}

}
