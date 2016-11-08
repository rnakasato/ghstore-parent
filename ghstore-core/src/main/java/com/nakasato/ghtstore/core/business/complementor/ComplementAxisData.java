package com.nakasato.ghtstore.core.business.complementor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.performance.graphic.AxisData;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementAxisData extends Complementor < PerformanceGraphicCarrier > {

	@Override
	public String complement( PerformanceGraphicCarrier carrier ) {

		List < PerformanceGraphicData > dataList = carrier.getDataList();

		for( PerformanceGraphicData pData: dataList ) {
			for( PerformanceGraphicData pNext: dataList ) {
				if( pData != pNext ) {
					Map < String, AxisData > dataMap = pData.getAxisData();
					Map < String, AxisData > nextDataMap = pNext.getAxisData();

					for( Map.Entry < String, AxisData > entry: dataMap.entrySet() ) {
						AxisData data = nextDataMap.get( entry.getKey() );
						if( data == null ) {
							data = new AxisData();
							data.setAxisX( entry.getKey() );
							data.setAxisY( 0 );
							nextDataMap.put( data.getAxisX(), data );
						}
					}

				}
			}

		}

		return null;
	}
	
	
	public static void main( String[] args ) {
		List<PerformanceGraphicData> dataList = new ArrayList < PerformanceGraphicData >();
				
		PerformanceGraphicData dataA = new PerformanceGraphicData();
		PerformanceGraphicData dataB = new PerformanceGraphicData();
		PerformanceGraphicData dataC = new PerformanceGraphicData();
		PerformanceGraphicData dataD = new PerformanceGraphicData();
		
		dataList.add( dataA );
		dataList.add( dataB );
		dataList.add( dataC );
		dataList.add( dataD );
		
		Map<String,AxisData> dataMapA = new HashMap<>();
		Map<String,AxisData> dataMapB = new HashMap<>();
		Map<String,AxisData> dataMapC = new HashMap<>();
		
		AxisData dt1 = new AxisData();
		AxisData dt2 = new AxisData();
		AxisData dt3 = new AxisData();
		AxisData dt4 = new AxisData();
		AxisData dt5 = new AxisData();
		AxisData dt6 = new AxisData();
		AxisData dt7 = new AxisData();
		AxisData dt8 = new AxisData();
	
		
		dt1.setAxisX( "0001" );
		dt1.setAxisY( 30 );
		
		dt2.setAxisX( "0003" );
		dt2.setAxisY( 35 );
		
		dt3.setAxisX( "0005" );
		dt3.setAxisY( 35 );
		
		
		dt4.setAxisX( "0001" );
		dt4.setAxisY( 30 );
		
		dt5.setAxisX( "0002" );
		dt5.setAxisY( 35 );
		
		dt6.setAxisX( "0004" );
		dt6.setAxisY( 35 );
		
		dt7.setAxisX( "0007" );
		dt7.setAxisY( 30 );
		
		dt8.setAxisX( "0009" );
		dt8.setAxisY( 35 );		
		
		dataMapA.put( dt1.getAxisX(), dt1 );
		dataMapA.put( dt2.getAxisX(), dt2 );
		dataMapA.put( dt3.getAxisX(), dt3 );
		
		
		dataMapB.put( dt4.getAxisX(), dt4 );
		dataMapB.put( dt5.getAxisX(), dt5 );
		dataMapB.put( dt6.getAxisX(), dt6 );
		
		dataMapC.put( dt7.getAxisX(), dt7 );
		dataMapC.put( dt8.getAxisX(), dt8 );

		
		dataA.setAxisData( dataMapA );
		dataB.setAxisData( dataMapB );
		
		dataC.setAxisData( dataMapC );
		dataD.setAxisData( dataMapA );
		
		PerformanceGraphicCarrier carrier = new PerformanceGraphicCarrier();
		carrier.setDataList( dataList );
		new ComplementAxisData().complement( carrier );
		
		System.out.println( "TESTE" );
		
		
	}

}
