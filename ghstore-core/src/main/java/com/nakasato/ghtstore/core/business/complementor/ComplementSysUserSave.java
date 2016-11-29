package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementSysUserSave extends Complementor < User > {

	@Override
	public String complement( User user ) {
		String cpf = user.getCpf();
		user.setCpf( FormatUtils.getFlatCPF( cpf ) );

		for( Phone phone: user.getPhoneList() ) {
			phone.setNumber( FormatUtils.removeHifen( phone.getNumber() ) );
			phone.setUser( user );
		}

		for( Address address: user.getAddressList() ) {
			address.setCep( FormatUtils.removeHifen( address.getCep() ) );
			address.setInsertDate( new Date() );
		}

		user.setInsertDate( new Date() );
		user.setUpdateDate( new Date() );
		
		user.setActive( true );
		user.setFirstLogin( true );

		
		return null;
	}

}
