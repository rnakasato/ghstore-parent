package com.nakasato.ghtstore.core.business.complementor;

import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.order.OrderStatus;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementOrderProductStock extends Complementor<Order> {

	@Override
	public String complement(Order order) {
		try {
			ProductDAO dao = new ProductDAO();
			
			if(order.getOrderStatus().getCode().equals(OrderStatus.COD_AGUARDANDO_PAGAMENTO)){
				for (OrderItem orderItem : order.getOrderItemList()) {
					Product p = orderItem.getProduct();
					Integer newAmount = p.getStock();
					newAmount -= orderItem.getAmount();
					p.setStock(newAmount);
					dao.update(p);
				}
			}
			// Foram feitos dois ifs porque apesar da única diferença do bloco ser a soma/subtração
			// dessa forma a condição teria de ser avaliada somente uma vez
			if(order.getOrderStatus().getCode().equals(OrderStatus.COD_CANCELADO)){
				for (OrderItem orderItem : order.getOrderItemList()) {
					Product p = orderItem.getProduct();
					Integer newAmount = p.getStock();
					newAmount += orderItem.getAmount();
					p.setStock(newAmount);
					dao.update(p);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
