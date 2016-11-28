package com.nakasato.ghtstore.core.business;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

/**
 * 
 * @author rafae Descrição:
 */
public abstract class FieldsValidator < T extends AbstractDomainEntity > extends Validator < T > {
	private static final String STANDARD_MESSAGE = "O(s) campo(s) é(são) obrigatório(s): \n";

	private StringBuilder sbMessage;
	
	protected void init(){
		sbMessage = null;
	}

	protected void appendMsg( String message ) {
		if( sbMessage == null ) {
			sbMessage = new StringBuilder();
			sbMessage.append( STANDARD_MESSAGE );
		}
		if( sbMessage.length() > STANDARD_MESSAGE.length() ) {
			sbMessage.append( ", " );
		}
		sbMessage.append( message );
	}

	protected String getMessage() {
		String msg = null;
		boolean isNull = sbMessage == null;
		boolean isEmpty = ! isNull && StringUtils.isEmpty( sbMessage.toString() );

		if( ! isNull && ! isEmpty ) {
			sbMessage.append( "." );
			msg = sbMessage.toString();
		}
		sbMessage = null;
		return msg;
	}
}
