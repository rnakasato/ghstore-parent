package com.nakasato.ghtstore.core.business.complementor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.enums.ELines;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementCityAxisData extends Complementor < PerformanceGraphicCarrier > {

	@Override
	public String complement( PerformanceGraphicCarrier carrier ) {

		if( carrier.getFilter().getLines().equals( ELines.TOP_TEN_CITIES.getCode() ) ) {
			List < PerformanceGraphicData > dataList = carrier.getDataList();
			Collections.sort( dataList, Collections.reverseOrder() );
			 
			List < PerformanceGraphicData > topList = new ArrayList<>(10);
			int size = dataList.size();
			if(size > 10){
				size = 10;
			}
			topList.addAll( dataList.subList( 0, size ) );
			carrier.setDataList( topList );

		}

		return null;
	}

}
