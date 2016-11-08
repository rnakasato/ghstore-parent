package com.nakasato.ghstore.web.mb.product.graphic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.enums.EAxisX;
import com.nakasato.ghstore.domain.enums.EAxisY;
import com.nakasato.ghstore.domain.filter.impl.CityFilter;
import com.nakasato.ghstore.domain.filter.impl.PerformanceGraphicFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.adapter.PerformanceGraphicDataAdapter;
import com.nakasato.ghstore.web.mb.BaseMB;

/**
 * 
 * @author rafae Descrição:
 */

@ManagedBean( name = "performanceGraphicMB" )
@ViewScoped
public class PerformanceGraphicMB extends BaseMB {

	protected List < StoreCategory > categoryList;
	protected List < Product > productList;
	private List < State > stateList;
	private List < City > cityList;
	private PerformanceGraphicFilter filter;

	private Double axisYMaxValue;

	private LineChartModel chartModel;

	@PostConstruct
	public void init() {
		initLists();
		filter = new PerformanceGraphicFilter();
		initEmptyChart();
	}

	public void createLineChart() {
		try {
			PerformanceGraphicCarrier carrier = new PerformanceGraphicCarrier();
			carrier.setFilter( filter );

			ICommand commandFind;
			commandFind = FactoryCommand.build( carrier, EOperation.FIND );
			Result result = commandFind.execute();

			if( StringUtils.isNotEmpty( result.getMsg() ) ) {
				addMessage( result.getMsg() );
				initEmptyChart();
				
			} else {
				chartModel = initLinearModel( carrier );
				chartModel.setTitle( "Gráfico de performance" );
				chartModel.setAnimate( true );
				chartModel.setLegendPosition( "se" );
			}
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private LineChartModel initLinearModel( PerformanceGraphicCarrier carrier ) {
		LineChartModel model = new LineChartModel();

		String maxValue = null;

		PerformanceGraphicDataAdapter adapter = new PerformanceGraphicDataAdapter();
		List < LineChartSeries > seriesList = adapter.adapt( carrier );
		for( LineChartSeries lineChartSeries: seriesList ) {
			model.addSeries( lineChartSeries );

			if( StringUtils.isEmpty( maxValue ) ) {
				Map dataMap = lineChartSeries.getData();
				List < String > keySet = new ArrayList<>( dataMap.keySet() );
				maxValue = keySet.get( dataMap.size() - 1 );
			}
		}

		Axis yAxis = model.getAxis( AxisType.Y );

		yAxis.setMin( 0 );
		yAxis.setMax( axisYMaxValue );

		String yLabel = EAxisY.getValue( filter.getAxisY() ).getDescription();
		yAxis.setLabel( yLabel );

		Axis xAxis = new CategoryAxis();

		String xLabel = EAxisX.getValue( filter.getAxisX() ).getDescription();
		xAxis.setLabel( xLabel );

		model.getAxes().put( AxisType.X, xAxis );

		return model;
	}

	private void initEmptyChart() {
		chartModel = new LineChartModel();

		LineChartSeries defaultSerie = new LineChartSeries();
		defaultSerie.setLabel( "" );
		defaultSerie.set( 0, 0 );
		chartModel.addSeries( defaultSerie );

		chartModel.setTitle( "Gráfico de performance" );
		chartModel.setAnimate( true );
		chartModel.setLegendPosition( "se" );

		Axis yAxis = chartModel.getAxis( AxisType.Y );

		yAxis.setMin( 0 );
		yAxis.setMax( axisYMaxValue );

		yAxis.setLabel( "-" );
		chartModel.getAxes().put( AxisType.X, new CategoryAxis( "-" ) );

	}

	private void initLists() {
		try {
			ICommand commandFind = FactoryCommand.build( new StoreCategory(), EOperation.FINDALL );
			categoryList = commandFind.execute().getEntityList();

			commandFind = FactoryCommand.build( new Product(), EOperation.FINDALL );
			productList = commandFind.execute().getEntityList();

			commandFind = FactoryCommand.build( new State(), EOperation.FINDALL );
			stateList = commandFind.execute().getEntityList();

			cityList = new ArrayList < City >();

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
			addMessage( "Erro inesperado" );
		}

	}

	public void initCityList( AjaxBehaviorEvent event ) {
		try {
			if( filter.getState() != null ) {
				CityFilter cityFilter = new CityFilter();
				cityFilter.setStateAcronym( filter.getState().getAcronym() );
				ICommand command = FactoryCommand.build( cityFilter, EOperation.FIND );
				Result result = command.execute();
				cityList = result.getEntityList();
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearFilter() {
		filter = new PerformanceGraphicFilter();
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList = categoryList;
	}

	public List < Product > getProductList() {
		return productList;
	}

	public void setProductList( List < Product > productList ) {
		this.productList = productList;
	}

	public List < State > getStateList() {
		return stateList;
	}

	public void setStateList( List < State > stateList ) {
		this.stateList = stateList;
	}

	public List < City > getCityList() {
		return cityList;
	}

	public void setCityList( List < City > cityList ) {
		this.cityList = cityList;
	}

	public PerformanceGraphicFilter getFilter() {
		return filter;
	}

	public void setFilter( PerformanceGraphicFilter filter ) {
		this.filter = filter;
	}

	public Double getAxisYMaxValue() {
		return axisYMaxValue;
	}

	public void setAxisYMaxValue( Double axisYMaxValue ) {
		this.axisYMaxValue = axisYMaxValue;
	}

	public LineChartModel getChartModel() {
		return chartModel;
	}

	public void setChartModel( LineChartModel chartModel ) {
		this.chartModel = chartModel;
	}

}
