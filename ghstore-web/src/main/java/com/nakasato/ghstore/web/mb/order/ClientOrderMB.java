package com.nakasato.ghstore.web.mb.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.filter.impl.OrderStatusFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.order.OrderStatus;
import com.nakasato.ghstore.domain.productexchange.ExchangeItem;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnedItem;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.user.LoginMB;

@ManagedBean( name = "clientOrderMB" )
@ViewScoped
public class ClientOrderMB extends OrderMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	private Customer customer;

	private Order returnOrder;
	private Order exchangeOrder;

	private String exchangeReason;
	private String returnReason;

	private List < ReturnedItem > returnList;
	private List < ReturnedItem > selectedReturnList;

	private List < ExchangeItem > exchangeList;
	private List < ExchangeItem > selectedExchangeList;

	@PostConstruct
	public void init() {
		filter = new OrderFilter();

		customer = ( Customer ) loginMB.getLoggedUser();
		if( customer != null ) {
			filter.setUsername( customer.getUsername() );
		}
		initOrderStatus();
	}

	@Override
	public void listOrders() {
		try {
			boolean hasError = false;
			if( filter != null ) {
				hasError = validateDates();
			}
			if( ! hasError ) {
				ICommand command;
				command = FactoryCommand.build( filter, EOperation.FIND );
				orderResults = command.execute().getEntityList();
			}
		} catch( ClassNotFoundException e1 ) {
			e1.printStackTrace();
		}
	}

	public void changeToCanceled( Order order ) {
		try {
			OrderStatusFilter statusFilter = new OrderStatusFilter();
			statusFilter.setCode( OrderStatus.COD_CANCELADO );

			ICommand command;
			command = FactoryCommand.build( statusFilter, EOperation.FIND );
			List < OrderStatus > statusList = command.execute().getEntityList();

			OrderStatus status = null;
			if( statusList != null && ! statusList.isEmpty() ) {
				status = statusList.get( 0 );
			}
			order.setOrderStatus( status );

			command = FactoryCommand.build( order, EOperation.UPDATE );
			command.execute();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void initReturn( Order order ) {
		returnOrder = order;
		returnList = new ArrayList<>();
		returnReason = null;
		selectedReturnList = new ArrayList<>();
		for( OrderItem item: order.getOrderItemList() ) {
			ReturnedItem ret = new ReturnedItem();
			ret.setOriginalAmount( item.getAmount() );

			if( ! ListUtils.isEmpty( order.getProductReturnList() ) ) {
				// calcula quantos produtos podem ser devolvidos
				for( ProductReturn pr: order.getProductReturnList() ) {
					for( ReturnedItem rt: pr.getReturnedItems() ) {
						if( rt.getProduct().equals( item.getProduct() ) ) {
							ret.setOriginalAmount( ret.getOriginalAmount() - rt.getAmount() );
						}
					}
				}
			}

			if( ret.getOriginalAmount() > 0 ) {
				ret.setAmount( 0 );
				ret.setProduct( item.getProduct() );
				ret.setTotalValue( 0D );
				returnList.add( ret );
			}
		}
	}

	public void initExchange( Order order ) {
		exchangeOrder = order;
		exchangeReason = null;
		selectedExchangeList = new ArrayList<>();
		exchangeList = new ArrayList<>();
		for( OrderItem item: order.getOrderItemList() ) {
			ExchangeItem ex = new ExchangeItem();
			ex.setOriginalAmount( item.getAmount() );
			ex.setAmount( 0 );
			ex.setProduct( item.getProduct() );
			ex.setTotalValue( 0D );
			exchangeList.add( ex );

		}
	}

	public void doReturn() {
		try {
			ProductReturn productReturn = new ProductReturn();
			productReturn.setOrder( returnOrder );
			productReturn.setReturnedItems( selectedReturnList );
			productReturn.setReason( returnReason );

			ICommand commandSave;
			commandSave = FactoryCommand.build( productReturn, EOperation.SAVE );
			Result result = commandSave.execute();
			if( StringUtils.isNotEmpty( result.getMsg() ) ) {
				addMessage( result.getMsg() );
			} else {
				addMessage( "Produto devolvido" );
				selectedReturnList = new ArrayList<>();
				returnReason = null;
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		listOrders();

	}

	public void doExchange() {
		try {
			ProductExchange productExchange = new ProductExchange();
			productExchange.setOrder( exchangeOrder );
			productExchange.setExchangeItems( selectedExchangeList );
			productExchange.setReason( exchangeReason );
			
			ICommand commandSave;
			commandSave = FactoryCommand.build( productExchange, EOperation.SAVE );
			Result result = commandSave.execute();
			if( StringUtils.isNotEmpty( result.getMsg() ) ) {
				addMessage( result.getMsg() );
			} else {
				addMessage( "Produto trocado, foi lhe dado um cupom de desconto de 10%" );
				selectedReturnList = new ArrayList<>();
				exchangeReason = null;
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		listOrders();
	}

	public boolean allowReturn( Order order ) {
		boolean notAllow = true;
		if( order.getDeliverDate() != null ) {
			Long difference = ( getToday().getTime() - order.getDeliverDate().getTime() ) / ( 24 * 60 * 60 * 1000 );

			if( order.getOrderStatus().getCode().equals( OrderStatus.COD_ENTREGE ) && difference <= 7 ) {
				notAllow = false;
			}
		}
		return notAllow;

	}

	public boolean allowExchange( Order order ) {
		boolean notAllow = true;
		if( order.getDeliverDate() != null ) {
			Long difference = ( getToday().getTime() - order.getDeliverDate().getTime() ) / ( 24 * 60 * 60 * 1000 );

			if( order.getOrderStatus().getCode().equals( OrderStatus.COD_ENTREGE ) && difference <= 30 ) {
				notAllow = false;
			}
		}
		return notAllow;

	}

	public void putMapAmount( OrderItem item ) {

	}

	@Override
	public void clearFilter() {
		filter = new OrderFilter();
		if( customer != null ) {
			filter.setUsername( customer.getUsername() );
		}
	}

	// Getters e Setters
	
	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer = customer;
	}

	public Order getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder( Order returnOrder ) {
		this.returnOrder = returnOrder;
	}

	public Order getExchangeOrder() {
		return exchangeOrder;
	}

	public void setExchangeOrder( Order exchangeOrder ) {
		this.exchangeOrder = exchangeOrder;
	}

	public List < ReturnedItem > getReturnList() {
		return returnList;
	}

	public void setReturnList( List < ReturnedItem > returnList ) {
		this.returnList = returnList;
	}

	public List < ReturnedItem > getSelectedReturnList() {
		return selectedReturnList;
	}

	public void setSelectedReturnList( List < ReturnedItem > selectedReturnList ) {
		this.selectedReturnList = selectedReturnList;
	}

	public String getExchangeReason() {
		return exchangeReason;
	}

	public void setExchangeReason( String exchangeReason ) {
		this.exchangeReason = exchangeReason;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason( String returnReason ) {
		this.returnReason = returnReason;
	}

	public List < ExchangeItem > getExchangeList() {
		return exchangeList;
	}

	public void setExchangeList( List < ExchangeItem > exchangeList ) {
		this.exchangeList = exchangeList;
	}

	public List < ExchangeItem > getSelectedExchangeList() {
		return selectedExchangeList;
	}

	public void setSelectedExchangeList( List < ExchangeItem > selectedExchangeList ) {
		this.selectedExchangeList = selectedExchangeList;
	}

}
