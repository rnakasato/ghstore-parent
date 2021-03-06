package com.nakasato.ghstore.web.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.nakasato.ghstore.domain.product.Tag;

@FacesConverter( "tagConverter" )
public class TagConverter implements Converter {

	public Object getAsObject( FacesContext fc, UIComponent uic, String value ) {
		if( value != null && value.trim().length() > 0 ) {
			try {
				Tag tag = new Tag();
				tag.setDescription( value );
				return tag;
			} catch( NumberFormatException e ) {
				throw new ConverterException(
						new FacesMessage( FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme." ) );
			}
		} else {
			return null;
		}
	}

	public String getAsString( FacesContext fc, UIComponent uic, Object object ) {
		if( object != null ) {
			return ( ( Tag ) object ).getDescription();
		} else {
			return null;
		}
	}

}
