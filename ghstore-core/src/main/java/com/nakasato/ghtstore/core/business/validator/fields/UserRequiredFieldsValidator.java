package com.nakasato.ghtstore.core.business.validator.fields;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghtstore.core.business.FieldsValidator;

/**
 * 
 * @author rafae Descri��o:
 */
public class UserRequiredFieldsValidator extends FieldsValidator<Customer> {

	@Override
	public String validate(Customer c) {
		if (StringUtils.isEmpty(c.getUsername())) {
			appendMsg("Usu�rio");
		}

		if (StringUtils.isEmpty(c.getPassword())) {
			appendMsg("Senha");
		}

		if (StringUtils.isEmpty(c.getName())) {
			appendMsg("Nome completo");
		}

		if (StringUtils.isEmpty(c.getCpf())) {
			appendMsg("CPF");
		}

		if (c.getBirthDate() == null) {
			appendMsg("Data de nascimento");
		}

		if (StringUtils.isEmpty(c.getSex())) {
			appendMsg("Sexo");
		}

		if (c.getDeliveryAddressList() == null || c.getDeliveryAddressList().isEmpty()) {
			appendMsg("Endere�o de entrega");
		}
		// VALIDA SOMENTE UM TELEFONE, NO MOMENTO SO SER� ADICIONADO UM N�MERO
		// POR USU�RIO
		if (c.getPhoneList() == null || c.getPhoneList().isEmpty()) {
			appendMsg("Campos de telefone");
		} else {
			Phone phone = c.getPhoneList().get(0);
			boolean dddEmpty = phone.getDdd() == null;
			boolean numberEmpty = phone.getNumber() == null;

			if (dddEmpty) {
				appendMsg("DDD");
			}

			if (numberEmpty) {
				appendMsg("N�mero de telefone");
			}
		}

		return getMessage();
	}

}
