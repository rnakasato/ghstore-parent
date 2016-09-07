package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Subcategory;

public class SubcategoryDAO extends AbstractDAO<Subcategory> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Subcategory> find(AbstractDomainEntity entity) {
		Subcategory subcategory = (Subcategory) entity;
		boolean isDescriptionEmpty = StringUtils.isEmpty(subcategory.getDescription());
		boolean isIdNull = (subcategory.getId() == null);
		boolean isStoreCategoryEmpty = (subcategory.getStoreCategory() == null
				|| (subcategory.getStoreCategory().getId() == null
						&& StringUtils.isEmpty(subcategory.getStoreCategory().getDescription())));

		List<Subcategory> subCategoryList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Subcategory s ");
			jpql.append(" WHERE 1=1 ");

			if (!isIdNull) {
				jpql.append(" AND s.id = :id");
			}

			if (!isDescriptionEmpty) {
				jpql.append(" AND UPPER(s.description) like :description");
			}

			if (!isStoreCategoryEmpty) {
				if (subcategory.getStoreCategory().getId() != null) {
					jpql.append(" AND s.storeCategory.id = :storeCategoryId");
				} else if (StringUtils.isNotEmpty(subcategory.getStoreCategory().getDescription())) {
					jpql.append(" AND UPPER(s.storeCategory.description) = :storeCategoryDescription");
				}

			}

			Query query = session.createQuery(jpql.toString());

			if (!isIdNull) {
				query.setParameter("id", subcategory.getId());
			}

			if (!isDescriptionEmpty) {
				query.setParameter("description", "%" + subcategory.getDescription().toUpperCase() + "%");
			}

			if (!isStoreCategoryEmpty) {
				if (subcategory.getStoreCategory().getId() != null) {
					query.setParameter("storeCategoryId", subcategory.getStoreCategory().getId());
				} else if (StringUtils.isNotEmpty(subcategory.getStoreCategory().getDescription())) {
					query.setParameter("storeCategoryDescription",
							"%" + subcategory.getDescription().toUpperCase() + "%");
				}
			}
			subCategoryList = (List<Subcategory>) query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return subCategoryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subcategory> findAll() {
		List<Subcategory> subcategoryList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Subcategory ");

			Query query = session.createQuery(jpql.toString());

			subcategoryList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return subcategoryList;
	}

	/*public static void main(String[] args) throws Exception {

		Subcategory sc = new Subcategory();
		sc.setDescription("subcategoria teste");
		ICommand command = new FactoryCommand().build(sc, EOperation.FIND);
		Result result = command.execute();
		if (result != null) {
			List<Subcategory> list = result.getEntityList();
			for (Subcategory subcategory : list) {
				System.out.println("DESCRIPTION: " + subcategory.getDescription());
				System.out.println("ID: " + subcategory.getId());
				System.out.println("INSERTDATE: " + subcategory.getInsertDate());
			}
		}

		Runtime.getRuntime().exit(0);
	}*/

}
