package com.nakasato.ghstore.core.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderStatus;

public class OrderDAO extends AbstractDAO<Order> {

	@Override
	public List<Order> find(AbstractDomainEntity filter) throws Exception {
		List<Order> orderList = null;
		try {
			openSession();

			OrderFilter orderFilter = (OrderFilter) filter;
			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Order o ");
			jpql.append(" JOIN FETCH o.customer c   ");
			jpql.append(" JOIN FETCH o.orderStatus s   ");
			jpql.append(" WHERE 1 = 1 ");

			// usa igual em vez de like para evitar retornar mais de um usuário
			// caso hajam usuários CUSTOMER e CUSTOMER_A se buscar customer
			// retornaria ambos
			if (StringUtils.isNotEmpty(orderFilter.getUsername())) {
				jpql.append(" AND c.username = :username ");
			}

			if (StringUtils.isNotEmpty(orderFilter.getStatusCode())) {
				jpql.append(" AND s.code = :statusCode ");
			}

			if (StringUtils.isNotEmpty(orderFilter.getOrderCode())) {
				jpql.append(" AND o.code = :orderCode ");
			}
			
			if (StringUtils.isNotEmpty(orderFilter.getTransactionCode())) {
				jpql.append(" AND o.transactionCode = :transactionCode ");
			}

			if (orderFilter.getStartDate() != null && orderFilter.getEndDate() != null) {
				jpql.append(" AND o.insertDate BETWEEN :startDate AND :endDate ");
			}

			Query query = session.createQuery(jpql.toString());

			if (StringUtils.isNotEmpty(orderFilter.getUsername())) {
				query.setParameter("username", orderFilter.getUsername());
			}

			if (StringUtils.isNotEmpty(orderFilter.getStatusCode())) {
				query.setParameter("statusCode", orderFilter.getStatusCode());
			}

			if (StringUtils.isNotEmpty(orderFilter.getOrderCode())) {
				query.setParameter("orderCode", orderFilter.getOrderCode());
			}
			
			if (StringUtils.isNotEmpty(orderFilter.getTransactionCode())) {
				query.setParameter("transactionCode", orderFilter.getTransactionCode());
			}

			if (orderFilter.getStartDate() != null && orderFilter.getEndDate() != null) {
				query.setParameter("startDate", orderFilter.getStartDate());
				query.setParameter("endDate", orderFilter.getEndDate());
			}
			
			orderList = query.getResultList();
			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}

		return orderList;
	}

	@Override
	public List<Order> findAll() throws Exception {

		List<Order> orderList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Order ");

			Query query = session.createQuery(jpql.toString());

			orderList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return orderList;

	}

	public static void main(String[] args) throws Exception {
		OrderDAO dao = new OrderDAO();
		List<Order> orders;

		OrderFilter filter = new OrderFilter();
		filter.setOrderCode("731E0EA5");
//		Calendar start = new GregorianCalendar(2016, 9, 14);
//		Calendar end = new GregorianCalendar(2016, 9, 18);
//
//		filter.setStartDate(start.getTime());
//		filter.setEndDate(end.getTime());

//		filter.setStatusCode(OrderStatus.COD_AGUARDANDO_PAGAMENTO);
		orders = dao.find(filter);
		for (Order order : orders) {
			System.out.println(order.getCode());
		}

		HibernateUtil.shutdown();
	}

}
