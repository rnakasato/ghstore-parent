package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;

public class ProductReturnDAO extends AbstractDAO<ProductReturn> {

	@Override
	public List<ProductReturn> find(AbstractDomainEntity filter) throws Exception {
		ProductReturnFilter returnFilter = (ProductReturnFilter) filter;

		List<ProductReturn> returnList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM ProductReturn pr ");
			jpql.append(" JOIN FETCH pr.order o   ");
			jpql.append(" WHERE 1 = 1 ");

			if (StringUtils.isNotEmpty(returnFilter.getCodOrder())) {
				jpql.append(" AND o.code = :codOrder ");
			}

			if (returnFilter.getStartDate() != null && returnFilter.getEndDate() != null) {
				jpql.append(" AND o.insertDate BETWEEN :startDate AND :endDate ");
			}

			Query query = session.createQuery(jpql.toString());

			if (StringUtils.isNotEmpty(returnFilter.getCodOrder())) {
				query.setParameter("codOrder", returnFilter.getCodOrder());
			}

			if (returnFilter.getStartDate() != null && returnFilter.getEndDate() != null) {
				query.setParameter("startDate", returnFilter.getStartDate());
				query.setParameter("endDate", returnFilter.getEndDate());
			}

			returnList = query.getResultList();
			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}

		return returnList;

	}

	@Override
	public List<ProductReturn> findAll() throws Exception {

		List<ProductReturn> productReturnList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM ProductReturn ");

			Query query = session.createQuery(jpql.toString());

			productReturnList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return productReturnList;
	}

}
