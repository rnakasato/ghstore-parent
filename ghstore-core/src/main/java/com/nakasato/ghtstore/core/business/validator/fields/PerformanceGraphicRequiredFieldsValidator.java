package com.nakasato.ghtstore.core.business.validator.fields;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.filter.impl.PerformanceGraphicFilter;
import com.nakasato.ghtstore.core.business.FieldsValidator;

public class PerformanceGraphicRequiredFieldsValidator extends FieldsValidator < PerformanceGraphicCarrier > {

	@Override
	public String validate( PerformanceGraphicCarrier carrier ) {
		PerformanceGraphicFilter filter = carrier.getFilter();

		if( StringUtils.isEmpty( filter.getAxisX() ) ) {
			appendMsg( "Eixo horizontal" );
		}

		if( StringUtils.isEmpty( filter.getAxisY() ) ) {
			appendMsg( "Eixo vertical" );
		}

		if( StringUtils.isEmpty( filter.getLines() ) ) {
			appendMsg( "Linhas" );
		}

		if( filter.getStartDate() == null ) {
			appendMsg( "Data inicial" );
		}

		if( filter.getEndDate() == null ) {
			appendMsg( "Data final" );
		}
		return getMessage();
	}

}
