package com.nakasato.ghtstore.core.business.validator.fields;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.FieldsValidator;

/**
 * 
 * @author rafae Descri��o:
 */
public class SysUserRequiredFieldsValidator extends FieldsValidator < User > {

	@Override
	public String validate( User user ) {
		super.init();
		if( StringUtils.isEmpty( user.getUsername() ) ) {
			appendMsg( "Usu�rio" );
		}
		
		if( user.getUserType() == null ) {
			appendMsg( "Tipo de usu�rio" );
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
			appendMsg( "Endere�o " );
		}
		// VALIDA SOMENTE UM TELEFONE, NO MOMENTO SO SER� ADICIONADO UM N�MERO
		// POR USU�RIO
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
				appendMsg( "N�mero de telefone" );
			}
		}

		return getMessage();
	}

}
