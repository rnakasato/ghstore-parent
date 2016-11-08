package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.CityFilter;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class CityDAO extends AbstractDAO < City > {

	@Override
	public List < City > find( AbstractDomainEntity filter ) throws Exception {
		CityFilter cityFilter = ( CityFilter ) filter;
		List < City > cityList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM City c" );
			jpql.append( " JOIN FETCH c.state s " );
			jpql.append( " WHERE 1=1 " );

			if( StringUtils.isNotEmpty( cityFilter.getCityName() ) ) {
				jpql.append( " AND UPPER(c.name) like :cityName " );
			}
			if( StringUtils.isNotEmpty( cityFilter.getStateAcronym() ) ) {
				jpql.append( " AND UPPER(s.acronym) like :acronym " );
			}
			if( StringUtils.isNotEmpty( cityFilter.getStateName() ) ) {
				jpql.append( " AND UPPER(s.description) like :stateName " );
			}

			Query query = session.createQuery( jpql.toString() );

			if( StringUtils.isNotEmpty( cityFilter.getCityName() ) ) {
				query.setParameter( "cityName", "%" + cityFilter.getCityName().toUpperCase() + "%" );
			}
			if( StringUtils.isNotEmpty( cityFilter.getStateAcronym() ) ) {
				query.setParameter( "acronym", "%" + cityFilter.getStateAcronym().toUpperCase() + "%" );
			}
			if( StringUtils.isNotEmpty( cityFilter.getStateName() ) ) {
				query.setParameter( "stateName", "%" + cityFilter.getStateName().toUpperCase() + "%" );
			}

			cityList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
			e.printStackTrace();
		}
		return cityList;
	}

	@Override
	public List < City > findAll() throws Exception {
		List < City > cityList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM City " );

			Query query = session.createQuery( jpql.toString() );

			cityList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return cityList;
	}

	public static void main( String[] args ) throws Exception {
		CityFilter filter = new CityFilter();
		filter.setStateAcronym( "SP" );
		ICommand command = FactoryCommand.build( filter, EOperation.FIND );
		Result r = command.execute();
		List < City > cityList = r.getEntityList();
		for( City city: cityList ) {
			System.out.println( city.getName() );
		}
		HibernateUtil.shutdown();

	}

}
