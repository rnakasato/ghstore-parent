package com.nakasato.ghstore.web.mb.order;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.filter.impl.OrderStatusFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderStatus;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

@ManagedBean(name = "adminOrderMB")
@ViewScoped
public class AdminOrderMB extends OrderMB {

	private List<Customer> customerList;

	// Inicialização
	@PostConstruct
	public void init() {
		filter = new OrderFilter();
		initOrderStatus();
		initCustomerList();
	}

	private void initCustomerList() {
		try {
			ICommand commandFindAll = FactoryCommand.build(new Customer(), EOperation.FINDALL);
			customerList = commandFindAll.execute().getEntityList();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Busca
	@Override
	public void listOrders() {
		try {
			boolean hasError = false;
			if (filter != null) {
				hasError = validateDates();
			}
			if (!hasError) {
				ICommand command;
				command = FactoryCommand.build(filter, EOperation.FIND);
				orderResults = command.execute().getEntityList();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	// Actions
	public void changeToPaid(Order order) {
		try {
			OrderStatusFilter statusFilter = new OrderStatusFilter();
			statusFilter.setCode(OrderStatus.COD_PAGO);

			ICommand command;
			command = FactoryCommand.build(statusFilter, EOperation.FIND);
			List<OrderStatus> statusList = command.execute().getEntityList();

			OrderStatus status = null;
			if (statusList != null && !statusList.isEmpty()) {
				status = statusList.get(0);
			}
			order.setOrderStatus(status);

			command = FactoryCommand.build(order, EOperation.UPDATE);
			command.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void changeToTransportation(Order order) {
		try {
			OrderStatusFilter statusFilter = new OrderStatusFilter();
			statusFilter.setCode(OrderStatus.COD_TRANSPORTE);

			ICommand command;
			command = FactoryCommand.build(statusFilter, EOperation.FIND);
			List<OrderStatus> statusList = command.execute().getEntityList();

			OrderStatus status = null;
			if (statusList != null && !statusList.isEmpty()) {
				status = statusList.get(0);
			}
			order.setOrderStatus(status);

			command = FactoryCommand.build(order, EOperation.UPDATE);
			command.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void changeToDelivered(Order order) {
		try {
			OrderStatusFilter statusFilter = new OrderStatusFilter();
			statusFilter.setCode(OrderStatus.COD_ENTREGE);

			ICommand command;
			command = FactoryCommand.build(statusFilter, EOperation.FIND);
			List<OrderStatus> statusList = command.execute().getEntityList();

			OrderStatus status = null;
			if (statusList != null && !statusList.isEmpty()) {
				status = statusList.get(0);
			}
			order.setOrderStatus(status);
			order.setDeliverDate(new Date());

			command = FactoryCommand.build(order, EOperation.UPDATE);
			command.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void changeToCanceled(Order order) {
		try {
			OrderStatusFilter statusFilter = new OrderStatusFilter();
			statusFilter.setCode(OrderStatus.COD_CANCELADO);

			ICommand command;
			command = FactoryCommand.build(statusFilter, EOperation.FIND);
			List<OrderStatus> statusList = command.execute().getEntityList();

			OrderStatus status = null;
			if (statusList != null && !statusList.isEmpty()) {
				status = statusList.get(0);
			}
			order.setOrderStatus(status);

			command = FactoryCommand.build(order, EOperation.UPDATE);
			command.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void clearFilter() {
		filter = new OrderFilter();
	}
	
	// getters e setters
	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public String getImagePath(Product product) {
		String path;
		if (product != null) {
			path = SaveDirectory.REQUEST_IMG_DIR + product.getImage();
		} else {
			path = "default.jpg";
		}
		return path;
	}


}
