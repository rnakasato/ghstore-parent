package com.nakasato.ghtstore.core.business.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghtstore.core.business.Validator;

public class CustomerCEPValidator extends Validator < Customer > {

	private Pattern pattern =Pattern.compile( "[0-9]{5}-[0-9]{3}" );

	private StringBuilder sb;

	@ Override
	public String validate( Customer customer ) {
		sb =new StringBuilder();
		msg =null;
		boolean validCEP =true;
		int addressNumber =1;
		for( Address ad: customer.getDeliveryAddressList() ) {
			Matcher matcher =pattern.matcher( FormatUtils.formatString( ad.getCep(), "#####-###" ) );
			if( !matcher.matches() ) {
				validCEP =false;
				addAddressNumber( addressNumber );
			}
			addressNumber ++;
		}
		if( !validCEP ) {
			sb.append( "." );
		}
		if( sb.length() >0 ) {
			msg =sb.toString();
		}

		return msg;
	}

	private void addAddressNumber( int position ) {
		if( sb.length() ==0 ) {
			sb.append( "CEP(s) inválido(s) para o(s) endereço(s) de posicao " ).append( position );
		} else {
			sb.append( ", " ).append( position );
		}
	}

}
