package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.StateFilter;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class StateDAO extends AbstractDAO<State> {

	@Override
	public List<State> find(AbstractDomainEntity filter) throws Exception {

		StateFilter stateFilter = (StateFilter) filter;
		List<State> stateList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM State s");
			jpql.append(" WHERE 1=1 ");

			if (StringUtils.isNotEmpty(stateFilter.getName())) {
				jpql.append(" AND UPPER(s.description) like :stateName ");
			}
			if (StringUtils.isNotEmpty(stateFilter.getAcronym())) {
				jpql.append(" AND UPPER(s.acronym) like :acronym ");
			}

			Query query = session.createQuery(jpql.toString());
			
			if (StringUtils.isNotEmpty(stateFilter.getName())) {
				query.setParameter("stateName", "%" + stateFilter.getName().toUpperCase() + "%");
			}

			if (StringUtils.isNotEmpty(stateFilter.getAcronym())) {
				query.setParameter("acronym", "%" + stateFilter.getAcronym().toUpperCase() + "%");
			}

			stateList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
			e.printStackTrace();
		}
		return stateList;

	}

	@Override
	public List<State> findAll() throws Exception {
		List<State> stateList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM State ");

			Query query = session.createQuery(jpql.toString());

			stateList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return stateList;

	}

	public static void main(String[] args) throws Exception {
		ICommand command = FactoryCommand.build(new State(), EOperation.FINDALL);
		Result r = command.execute();
		List<State> stateList = r.getEntityList();
		for (State state : stateList) {
			System.out.println(state.getDescription());
		}

	}

}
