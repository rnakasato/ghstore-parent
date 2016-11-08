package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.core.dao.impl.UserTypeDAO;
import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.UserType;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementCustomer extends Complementor < Customer > {

	@Override
	public String complement( Customer entity ) {
		String cpf = entity.getCpf();
		entity.setCpf( FormatUtils.getFlatCPF( cpf ) );

		for( Phone phone: entity.getPhoneList() ) {
			phone.setNumber( FormatUtils.removeHifen( phone.getNumber() ) );
		}

		for( Address address: entity.getDeliveryAddressList() ) {
			address.setCep( FormatUtils.removeHifen( address.getCep() ) );
			address.setInsertDate( new Date() );
		}

		entity.setInsertDate( new Date() );
		entity.setUpdateDate( new Date() );

		UserTypeDAO userTypeDAO = new UserTypeDAO();
		UserType type = new UserType();
		type.setCode( UserType.COD_CUSTOMER );
		type = userTypeDAO.findByCode( type );
		entity.setUserType( type );

		return null;
	}

}
