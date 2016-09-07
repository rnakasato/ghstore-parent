package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Tag;

public class TagDAO extends AbstractDAO<Tag> {

	@Override
	public List<Tag> find(AbstractDomainEntity entity) {
		Tag tag = null;
		if (entity instanceof Tag) {
			tag = (Tag) entity;
		}
		List<Tag> tagList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Tag t WHERE 1=1");
			if (StringUtils.isNotEmpty(tag.getDescription())) {
				jpql.append(" AND lower(t.description) like :description");
			}

			Query query = session.createQuery(jpql.toString());

			if (StringUtils.isNotEmpty(tag.getDescription())) {
				query.setParameter("description", "%" + tag.getDescription().toLowerCase() + "%");
			}

			tagList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return tagList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> findAll() {
		List<Tag> tagList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Tag ");

			Query query = session.createQuery(jpql.toString());

			tagList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return tagList;
	}

	// public static void main(String[] args) throws Exception {
	// Tag tag = new Tag();
	// tag.setDescription("JBC");
	// tag.setInsertDate(new Date());
	//
	// ICommand command = FactoryCommand.build(tag, EOperation.FIND);
	// Result result = command.execute();
	// List<Tag> tagList = result.getEntityList();
	// if (tagList != null) {
	// for (Tag t : tagList) {
	// System.out.println("ID: " + t.getId());
	// System.out.println("DESCRIPTION: " + t.getDescription());
	// }
	// }
	// Runtime.getRuntime().exit(0);
	//
	// }

}
