package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementSysUserUpdate extends Complementor < User > {

	@Override
	public String complement( User entity ) {
		for( Address address: entity.getAddressList() ) {
			address.setCep( FormatUtils.removeHifen( address.getCep() ) );
		}
		entity.setUpdateDate( new Date() );
		
		String cpf = entity.getCpf();
		entity.setCpf( FormatUtils.getFlatCPF( cpf ) );

		for( Phone phone: entity.getPhoneList() ) {
			phone.setNumber( FormatUtils.removeHifen( phone.getNumber() ) );
			phone.setUser( entity );
		}

		for( Address address: entity.getAddressList() ) {
			address.setCep( FormatUtils.removeHifen( address.getCep() ) );
			address.setInsertDate( new Date() );
		}

		entity.setUpdateDate( new Date() );
		
		return null;
	}

}
