package com.nakasato.ghtstore.core.business.validator;

import java.util.Date;

import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.Validator;

public class UserBirthDateValidator extends Validator < User > {

	@Override
	public String validate( User user ) {
		msg = null;
		Date today = new Date();
		if( today.before( user.getBirthDate() ) ) {
			msg = "Data de nascimento inválida";
		}

		return msg;
	}

}
