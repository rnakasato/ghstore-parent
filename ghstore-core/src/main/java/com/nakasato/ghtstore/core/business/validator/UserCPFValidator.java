package com.nakasato.ghtstore.core.business.validator;

import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.Validator;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

/**
 * 
 * @author rafae Descrição:
 */
public class UserCPFValidator extends Validator < User > {

	@ Override
	public String validate( User user ) {
		msg =null;
		try {
			CPFValidator validator =new CPFValidator();
			validator.assertValid( user.getCpf() );

		} catch( InvalidStateException e ) {
			msg ="CPF inválido";
		}
		return msg;
	}

}
