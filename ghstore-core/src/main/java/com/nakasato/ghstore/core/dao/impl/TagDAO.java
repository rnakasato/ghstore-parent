package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.filter.impl.TagFilter;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Tag;

public class TagDAO extends AbstractDAO<Tag> {

	@Override
	public List<Tag> find(AbstractDomainEntity entity) {
		TagFilter filter = null;
		filter = (TagFilter) entity;

		List<Tag> tagList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Tag t WHERE 1=1");
			if (StringUtils.isNotEmpty(filter.getDescription())) {
				jpql.append(" AND lower(t.description) like :description");
			}

			Query query = session.createQuery(jpql.toString());

			if (StringUtils.isNotEmpty(filter.getDescription())) {
				query.setParameter("description", "%" + filter.getDescription().toLowerCase() + "%");
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

}
