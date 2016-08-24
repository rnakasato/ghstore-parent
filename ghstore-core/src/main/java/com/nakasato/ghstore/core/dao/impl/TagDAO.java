package com.nakasato.ghstore.core.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class TagDAO extends AbstractDAO<Tag> {

	@Override
	public List<Tag> find(AbstractDomainEntity entity) {
		return findAll();
	}

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

	public static void main(String[] args) throws Exception {
		Tag tag = new Tag();
		tag.setDescription("JBC");
		tag.setInsertDate(new Date());

		ICommand command = FactoryCommand.build(tag, EOperation.FIND);
		Result result = command.execute();
		List<Tag> tagList = result.getEntityList();
		if (tagList != null) {
			for (Tag t : tagList) {
				System.out.println("ID: " + t.getId());
				System.out.println("DESCRIPTION: " + t.getDescription());
			}
		}
		Runtime.getRuntime().exit(0);

	}

}
