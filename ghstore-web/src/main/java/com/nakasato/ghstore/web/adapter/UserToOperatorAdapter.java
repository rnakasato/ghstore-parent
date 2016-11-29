package com.nakasato.ghstore.web.adapter;

import com.nakasato.ghstore.core.adapter.Adapter;
import com.nakasato.ghstore.domain.user.Operator;
import com.nakasato.ghstore.domain.user.User;

public class UserToOperatorAdapter implements Adapter < User, Operator >{

	@Override
	public Operator adapt( User sysUser ) {
		Operator op = new Operator();
		op.setActive( sysUser.getActive() );
		op.setAddressList( sysUser.getAddressList() );
		op.setBirthDate( sysUser.getBirthDate() );
		op.setCpf( sysUser.getCpf() );
		op.setDescription( sysUser.getDescription() );
		op.setEmail( sysUser.getEmail() );
		op.setFirstLogin( sysUser.getFirstLogin() );
		op.setId( sysUser.getId() );
		op.setInsertDate( sysUser.getInsertDate() );
		op.setName( sysUser.getName() );
		op.setPassword( sysUser.getPassword() );
		op.setPhoneList( sysUser.getPhoneList() );
		op.setSex( sysUser.getSex() );
		op.setUpdateDate( sysUser.getUpdateDate() );
		op.setUsername( sysUser.getUsername() );
		op.setUserType( sysUser.getUserType() );
		
		return op;
	}

}
