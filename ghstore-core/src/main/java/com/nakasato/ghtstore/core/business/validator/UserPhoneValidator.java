package com.nakasato.ghtstore.core.business.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.Validator;

public class UserPhoneValidator extends Validator<User> {

	private Pattern dddPattern = Pattern.compile("[0-9]{2}");
	private Pattern phoneNumberPattern = Pattern.compile("[0-9]{5}-[0-9]{4}");

	@Override
	public String validate(User user) {
		Phone phone = user.getPhoneList().get(0);
		Matcher dddMatcher = dddPattern.matcher(phone.getDdd());
		Matcher phoneNumberMatcher = phoneNumberPattern.matcher(phone.getNumber());
		boolean isDDDValid = dddMatcher.matches();
		boolean isPhoneNumberValid = phoneNumberMatcher.matches();

		StringBuilder sb = new StringBuilder();
		if (!isDDDValid) {
			sb.append("DDD inválido");
		}
		if (!isPhoneNumberValid) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append("Número de telefone inválido ");
		}
		msg = sb.toString();
		return msg;
	}

}
