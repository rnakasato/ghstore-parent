package com.nakasato.ghtstore.core.business.validator;

import java.util.Calendar;

import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.Validator;

public class UserBirthDateValidator extends Validator < User > {

	@Override
	public String validate( User user ) {
		msg = null;

		Calendar today = Calendar.getInstance();
		today.set( Calendar.MILLISECOND, 0 );

		Calendar birthDate = Calendar.getInstance();
		birthDate.setTime( user.getBirthDate() );
		birthDate.set( Calendar.MILLISECOND, 0 );

		int years = 0;
		while( birthDate.before( today ) ) {
			birthDate.add( Calendar.YEAR, 1 );
			if( birthDate.before( today ) ) {
				years ++ ;
			}
		}

		if( today.getTime().before( user.getBirthDate() ) || years < 15) {
			msg = "Data de nascimento inválida";
		}

		return msg;
	}

}
