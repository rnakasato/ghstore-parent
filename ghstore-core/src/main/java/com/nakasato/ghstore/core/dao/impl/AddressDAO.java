package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class AddressDAO extends AbstractDAO<Address>{

	@Override
	public List<Address> find(AbstractDomainEntity filter) throws Exception {

		return null;
	}

	@Override
	public List<Address> findAll() throws Exception {
		List<Address> addressList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Address ");

			Query query = session.createQuery(jpql.toString());

			addressList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return addressList;
	}
	
	public static void main(String[] args) {
		
		try{			
//			Address ad = new Address();
//			ICommand command = FactoryCommand.build(new City(), EOperation.FINDALL);
//			Result r = command.execute();
//			List<City> cityList = r.getEntityList();
//			ad.setCity(cityList.get(0));
//			ad.setCep("08725640");
//			ad.setInsertDate(new Date());
//			ad.setNeighborhood("Bairro teste");
//			ad.setNumber(123);
//			ad.setStreet("Rua teste");
//			
//			ICommand commandSave = FactoryCommand.build(ad, EOperation.SAVE);
//			Result rSave = commandSave.execute();
			
			
			ICommand commandFind = FactoryCommand.build(new Address(), EOperation.FINDALL);
			Result rFindAll = commandFind.execute();
			List<Address> addressList = rFindAll.getEntityList();
			for (Address address : addressList) {
				System.out.println(address.getStreet());
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
