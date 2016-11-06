package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class CustomerDAO extends AbstractDAO < Customer > {

	@ Override
	public List < Customer > find( AbstractDomainEntity filter ) throws Exception {
		CustomerFilter customerFilter =( CustomerFilter ) filter;
		List < Customer > customerList =null;
		try {
			openSession();

			StringBuilder jpql =new StringBuilder();
			jpql.append( " FROM Customer c" );
			if( customerFilter.getLoadAddress() !=null &&customerFilter.getLoadAddress() ) {
				jpql.append( " JOIN FETCH Address a" );
			}
			jpql.append( " LEFT JOIN FETCH c.coupons cp" );
			jpql.append( " WHERE 1=1 " );
			if( StringUtils.isNotEmpty( customerFilter.getUserName() ) ) {
				jpql.append( " AND c.username = :username" );
			}

			Query query =session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( customerFilter.getUserName() ) ) {
				query.setParameter( "username", customerFilter.getUserName() );
			}
			customerList =query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
			e.printStackTrace();
		}
		return customerList;
	}

	@ Override
	public List < Customer > findAll() throws Exception {
		List < Customer > customerList =null;
		try {
			openSession();

			StringBuilder jpql =new StringBuilder();
			jpql.append( " FROM Customer " );

			Query query =session.createQuery( jpql.toString() );

			customerList =query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return customerList;
	}

	public static void main( String[] args ) {

		try {
			// Customer customer = new Customer();
			//
			// ICommand findType = FactoryCommand.build(new UserType(),
			// EOperation.FINDALL);
			// Result r = findType.execute();
			// List<UserType> list = r.getEntityList();
			//
			// for (UserType userType : list) {
			// if(userType.getCode().equals(UserType.COD_CUSTOMER)){
			// customer.setUserType(userType);
			// break;
			// }
			// }
			//
			// customer.setBirthDate(new Date());
			// customer.setCpf("78965412311");
			// customer.setEmail("teste@mail.com");
			// customer.setInsertDate(new Date());
			// customer.setName("Fulano da Silva");
			// customer.setPassword("123456789");
			// customer.setSex("M");
			// customer.setUsername("fusilva");
			// customer.setUpdateDate(new Date());
			CustomerFilter f =new CustomerFilter();
			// f.setLoadAddress(true);
			ICommand cFIndALl =FactoryCommand.build( f, EOperation.FIND );
			Result rCustomer =cFIndALl.execute();
			Customer customer =( Customer ) rCustomer.getEntityList().get( 0 );
			System.out.println( customer.getName() );

			// Phone phone = new Phone();
			// phone.setNumber(123456789);
			// phone.setDdd(11);
			// phone.setUser(customer);
			// phone.setInsertDate(new Date());
			//
			// List<Phone> phones= new ArrayList<>();
			// phones.add(phone);
			// customer.setPhoneList(phones);

			//
			// Address ad = new Address();
			// ICommand command = FactoryCommand.build(new City(),
			// EOperation.FINDALL);
			// Result rCity = command.execute();
			// List<City> cityList = rCity.getEntityList();
			// ad.setCity(cityList.get(0));
			// ad.setCep("08725640");
			// ad.setInsertDate(new Date());
			// ad.setNeighborhood("Bairro testeE");
			// ad.setNumber(123);
			// ad.setStreet("Rua testeE");
			//
			//
			// //
			// List<Address> addressSet = new ArrayList<>();
			// if(customer.getDeliveryAddressList() != null){
			// addressSet = customer.getDeliveryAddressList();
			// }
			// addressSet.add(ad);
			//
			// ICommand cSave = FactoryCommand.build(customer,
			// EOperation.UPDATE);
			// cSave.execute();
			HibernateUtil.shutdown();

		} catch( Exception e ) {
			e.printStackTrace();
		}

	}

}
