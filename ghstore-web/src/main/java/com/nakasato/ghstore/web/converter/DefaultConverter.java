package com.nakasato.ghstore.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

@ FacesConverter( "defaultConverter" )
public class DefaultConverter implements Converter {

	@ Override
	public Object getAsObject( FacesContext context, UIComponent component, String value ) {
		AbstractDomainEntity entity =null;
		if( StringUtils.isNotEmpty( value ) ) {
			entity =( AbstractDomainEntity ) component.getAttributes().get( value );
		}
		return entity;
	}

	@ Override
	public String getAsString( FacesContext context, UIComponent component, Object value ) {
		if( value ==null ) {
			return "";
		}
		String id =null;
		if( value instanceof AbstractDomainEntity ) {
			AbstractDomainEntity entity =( AbstractDomainEntity ) value;
			if( entity !=null &&entity.getId() !=null ) {
				id =entity.getId().toString();
				component.getAttributes().put( id, value );
			}
		}
		return id;
	}

}
