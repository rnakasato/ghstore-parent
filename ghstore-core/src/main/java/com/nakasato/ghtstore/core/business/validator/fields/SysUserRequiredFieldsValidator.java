package com.nakasato.ghtstore.core.business.validator.fields;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.FieldsValidator;

/**
 * 
 * @author rafae Descrição:
 */
public class SysUserRequiredFieldsValidator extends FieldsValidator < User > {

	@Override
	public String validate( User user ) {
		super.init();
		if( StringUtils.isEmpty( user.getUsername() ) ) {
			appendMsg( "Usuário" );
		}
		
		if( user.getUserType() == null ) {
			appendMsg( "Tipo de usuário" );
		}

		if( StringUtils.isEmpty( user.getPassword() ) ) {
			appendMsg( "Senha" );
		}

		if( StringUtils.isEmpty( user.getName() ) ) {
			appendMsg( "Nome completo" );
		}

		if( StringUtils.isEmpty( user.getCpf() ) ) {
			appendMsg( "CPF" );
		}

		if( user.getBirthDate() == null ) {
			appendMsg( "Data de nascimento" );
		}

		if( StringUtils.isEmpty( user.getSex() ) ) {
			appendMsg( "Sexo" );
		}

		if( ListUtils.isEmpty( user.getAddressList() ) ) {
			appendMsg( "Endereço " );
		}
		// VALIDA SOMENTE UM TELEFONE, NO MOMENTO SO SERÁ ADICIONADO UM NÚMERO
		// POR USUÁRIO
		if( user.getPhoneList() == null || user.getPhoneList().isEmpty() ) {
			appendMsg( "Campos de telefone" );
		} else {
			Phone phone = user.getPhoneList().get( 0 );
			boolean dddEmpty = phone.getDdd() == null;
			boolean numberEmpty = phone.getNumber() == null;

			if( dddEmpty ) {
				appendMsg( "DDD" );
			}

			if( numberEmpty ) {
				appendMsg( "Número de telefone" );
			}
		}

		return getMessage();
	}

}
