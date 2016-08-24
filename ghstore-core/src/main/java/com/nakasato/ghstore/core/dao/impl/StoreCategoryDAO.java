package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class StoreCategoryDAO extends AbstractDAO<StoreCategory> {

	@Override
	public List<StoreCategory> find(AbstractDomainEntity entity) {
		StoreCategory storeCategory = (StoreCategory) entity;
		boolean isDescriptionEmpty = StringUtils.isEmpty(storeCategory.getDescription());
		boolean isIdNull = (storeCategory.getId() == null);

		List<StoreCategory> storeCategoryList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM StoreCategory s").append(" WHERE 1=1 ");

			if (!isIdNull) {
				jpql.append(" AND s.id = :id");
			}

			if (!isDescriptionEmpty) {
				jpql.append(" AND UPPER(s.description) like :description");

			}

			Query query = session.createQuery(jpql.toString());

			if (!isIdNull) {
				query.setParameter("id", storeCategory.getId());
			}

			if (!isDescriptionEmpty) {
				query.setParameter("description", "%" + storeCategory.getDescription().toUpperCase() + "%");
			}

			storeCategoryList = (List<StoreCategory>) query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return storeCategoryList;

	}

	/**
	 * O Método findAll não será utilizado porque no momento não há sentido para
	 * retornar todas as categorias
	 */
	@Override
	public List<StoreCategory> findAll() {
		List<StoreCategory> storeCategoryList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM StoreCategory ");

			Query query = session.createQuery(jpql.toString());

			storeCategoryList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return storeCategoryList;
	}
	
	@Override
	public void save(StoreCategory entity) {
	}

	@Override
	public void update(StoreCategory entity) {
	}

	public static void main(String[] args) throws Exception{
		StoreCategory sc = new StoreCategory();
		sc.setDescription("acessórios");
		ICommand command = new FactoryCommand().build(sc,EOperation.FIND);
		Result result = command.execute();
		if(result != null){
			List<StoreCategory> list = result.getEntityList();
			for (StoreCategory storeCategory : list) {
				System.out.println("ID: " + storeCategory.getId());
				System.out.println("DESCRIPTION: " + storeCategory.getDescription());
				System.out.println("INSERTDATE: " + storeCategory.getInsertDate());
			}
		}		
	}
}
