package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.City;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class CityDAO extends AbstractDAO<City>{

	@Override
	public List<City> find(AbstractDomainEntity filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> findAll() throws Exception {
		List<City> cityList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM City ");

			Query query = session.createQuery(jpql.toString());

			cityList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return cityList;
	}
	
	public static void main(String[] args) throws Exception{
		ICommand command = FactoryCommand.build(new City(), EOperation.FINDALL);
		Result r = command.execute();
		List<City> cityList = r.getEntityList();
		for (City city : cityList) {
			System.out.println(city.getName());
		}
		
	}
	
	

}
