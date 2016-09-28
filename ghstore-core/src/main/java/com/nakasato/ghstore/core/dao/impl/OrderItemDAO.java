package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.OrderItem;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class OrderItemDAO extends AbstractDAO<OrderItem>{

	@Override
	public List<OrderItem> find(AbstractDomainEntity filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderItem> findAll() throws Exception {
		List<OrderItem> stateList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM OrderItem ");

			Query query = session.createQuery(jpql.toString());

			stateList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return stateList;
	}
	
	public static void main(String[] args) {
		try {
			ICommand command = FactoryCommand.build(new ProductFilter(), EOperation.FINDALL);
			Result r = command.execute();
			List<Product> productList = r.getEntityList();
			
			Product p = productList.get(0);
			OrderItem it = new OrderItem();
			it.setProduct(p);
			it.setAmount(10);
			it.setTotalValue(p.getPrice()*10);
			it.setTotalWeigth(p.getWeight().longValue()*10);
			
			ICommand commandSave = FactoryCommand.build(it, EOperation.SAVE);
			Result rSave = commandSave.execute();
			
			ICommand commandFind = FactoryCommand.build(new OrderItem(), EOperation.FINDALL);
			Result rFind = command.execute();
			List<OrderItem> itemList = r.getEntityList();
			for (OrderItem orderItem : itemList) {
				System.out.println(orderItem.getProduct().getName());
				System.out.println(orderItem.getTotalValue());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
