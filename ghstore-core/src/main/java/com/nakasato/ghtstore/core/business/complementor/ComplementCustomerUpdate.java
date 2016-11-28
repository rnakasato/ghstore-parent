package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementCustomerUpdate extends Complementor < Customer > {

	@Override
	public String complement( Customer entity ) {
		for( Address address: entity.getDeliveryAddressList() ) {
			address.setCep( FormatUtils.removeHifen( address.getCep() ) );
		}
		entity.setUpdateDate( new Date() );
		
		String cpf = entity.getCpf();
		entity.setCpf( FormatUtils.getFlatCPF( cpf ) );

		for( Phone phone: entity.getPhoneList() ) {
			phone.setNumber( FormatUtils.removeHifen( phone.getNumber() ) );
		}

		for( Address address: entity.getDeliveryAddressList() ) {
			address.setCep( FormatUtils.removeHifen( address.getCep() ) );
			address.setInsertDate( new Date() );
		}

		entity.setUpdateDate( new Date() );
		
		return null;
	}

}
