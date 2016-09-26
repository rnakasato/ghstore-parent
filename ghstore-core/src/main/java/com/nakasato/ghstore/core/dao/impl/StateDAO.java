package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.State;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class StateDAO extends AbstractDAO<State>{

	@Override
	public List<State> find(AbstractDomainEntity filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
	
	public static void main(String[] args) throws Exception{
		ICommand command = FactoryCommand.build(new State(), EOperation.FINDALL);
		Result r = command.execute();
		List<State> stateList = r.getEntityList();
		for (State state : stateList) {
			System.out.println(state.getDescription());
		}
		
	}

}
