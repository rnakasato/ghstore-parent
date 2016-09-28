package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.filter.impl.OrderStatusFilter;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.OrderStatus;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class OrderStatusDAO extends AbstractDAO<OrderStatus> {

	@Override
	public List<OrderStatus> find(AbstractDomainEntity filter) throws Exception {
		OrderStatusFilter statusFilter = (OrderStatusFilter) filter;
		List<OrderStatus> statusList = null;
		try {
			openSession();
			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM OrderStatus o");
			jpql.append(" WHERE 1=1 ");
			if (StringUtils.isNotEmpty(statusFilter.getCode())) {
				jpql.append(" AND o.code like :code ");
			}

			Query query = session.createQuery(jpql.toString());

			if (StringUtils.isNotEmpty(statusFilter.getCode())) {
				query.setParameter("code", statusFilter.getCode());
			}
			statusList = query.getResultList();
			
			closeSession();
		} catch (Exception e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return statusList;
	}

	@Override
	public List<OrderStatus> findAll() throws Exception {

		List<OrderStatus> statusList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM OrderStatus ");

			Query query = session.createQuery(jpql.toString());

			statusList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return statusList;
	}
	
	public static void main(String[] args) throws Exception{
		OrderStatusFilter filter = new OrderStatusFilter();
		filter.setCode(OrderStatus.COD_PENDENTE);
		ICommand commandFind = FactoryCommand.build(filter, EOperation.FIND);
		List<OrderStatus> statusList = commandFind.execute().getEntityList();
		
		for (OrderStatus orderStatus : statusList) {
			System.out.println(orderStatus.getDescription());
		}
	}

}
