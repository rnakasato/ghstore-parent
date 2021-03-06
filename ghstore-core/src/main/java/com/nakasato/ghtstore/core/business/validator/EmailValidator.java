package com.nakasato.ghtstore.core.business.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.Validator;

public class EmailValidator extends Validator < User > {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern pattern = Pattern.compile( EMAIL_PATTERN, Pattern.CASE_INSENSITIVE );

	@Override
	public String validate( User user ) {
		msg = null;
		Matcher matcher = pattern.matcher( user.getEmail() );
		if( ! matcher.matches() ) {
			msg = "E-mail inv�lido";
		}

		return msg;
	}
}
