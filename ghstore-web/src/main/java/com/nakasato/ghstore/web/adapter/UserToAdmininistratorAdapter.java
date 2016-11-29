package com.nakasato.ghstore.web.adapter;

import com.nakasato.ghstore.core.adapter.Adapter;
import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.User;

public class UserToAdmininistratorAdapter implements Adapter < User, Administrator >{

	@Override
	public Administrator adapt( User sysUser ) {
		
		sysUser.setCpf( FormatUtils.getFlatCPF( sysUser.getCpf() ) );
		
		Administrator admin = new Administrator();
		admin.setActive( sysUser.getActive() );
		admin.setAddressList( sysUser.getAddressList() );
		admin.setBirthDate( sysUser.getBirthDate() );
		admin.setCpf( FormatUtils.getFlatCPF( sysUser.getCpf() ) );
		admin.setDescription( sysUser.getDescription() );
		admin.setEmail( sysUser.getEmail() );
		admin.setFirstLogin( sysUser.getFirstLogin() );
		admin.setId( sysUser.getId() );
		admin.setInsertDate( sysUser.getInsertDate() );
		admin.setName( sysUser.getName() );
		admin.setPassword( sysUser.getPassword() );
		admin.setPhoneList( sysUser.getPhoneList() );
		admin.setSex( sysUser.getSex() );
		admin.setUpdateDate( sysUser.getUpdateDate() );
		admin.setUsername( sysUser.getUsername() );
		admin.setUserType( sysUser.getUserType() );
		
		return admin;
	}

}
