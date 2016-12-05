package com.nakasato.ghstore.web.mb.productexchange;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.filter.impl.ExchangeStatusFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductExchangeFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productexchange.ExchangeStatus;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.productreturn.ReturnStatus;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.ghstore.web.mb.user.LoginMB;

@ManagedBean( name = "exchangeMB" )
@ViewScoped
public class ExchangeMB extends BaseMB {
	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	protected ProductExchangeFilter filter;
	protected List < ProductExchange > exchangeResults;
	protected List < ExchangeStatus > exchangeStatusList;
	protected ProductExchange selectedExchange;

	private List < Customer > customerList;

	private Customer customer;

	// Inicialização
	@PostConstruct
	public void init() {
		filter = new ProductExchangeFilter();
		initExchangeStatus();
		initCustomerList();

		User user = ( User ) loginMB.getLoggedUser();
		if( user instanceof Customer ) {
			customer = ( Customer ) user;
			filter.setCustomer( customer );
		}
	}

	protected void initCustomerList() {
		try {
			ICommand commandFindAll = FactoryCommand.build( new Customer(), EOperation.FINDALL );
			customerList = commandFindAll.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	// Busca
	public void listProductExchange() {
		try {
			boolean hasError = false;
			if( filter != null ) {
				hasError = validateDates();
			}

			if( ! hasError ) {
				ICommand command;
				command = FactoryCommand.build( filter, EOperation.FIND );
				exchangeResults = command.execute().getEntityList();
			}
		} catch( ClassNotFoundException e1 ) {
			e1.printStackTrace();
		}
	}

	public void viewDetails( ProductExchange productExchange ) {
		selectedExchange = productExchange;

	}

	protected void initExchangeStatus() {
		try {
			ICommand commandFindAll = FactoryCommand.build( new ExchangeStatus(), EOperation.FINDALL );
			exchangeStatusList = commandFindAll.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	protected boolean validateDates() {
		boolean hasError = false;
		if( ( filter.getStartDate() != null && filter.getEndDate() == null )
				|| ( filter.getStartDate() == null && filter.getEndDate() != null ) ) {
			addMessage( "Para usar o filtro de data as datas inicial e final devem estar preenchidas!" );
			hasError = true;
		} else if( filter.getStartDate() != null && filter.getEndDate() != null
				&& filter.getEndDate().before( filter.getStartDate() ) ) {
			addMessage( "A data final deve ser inferior que a data inicial!" );
			hasError = true;
		}
		return hasError;
	}

	// Actions
	public void changeToAccepted( ProductExchange productExchange ) {
		changeStatus( productExchange, ExchangeStatus.COD_ACEITO );
	}

	public void changeToReceived( ProductExchange productExchange ) {
		changeStatus( productExchange, ExchangeStatus.COD_RECEBIDO );
	}

	public void changeToRejected( ProductExchange productExchange ) {
		changeStatus( productExchange, ExchangeStatus.COD_REJEITADO );
	}

	public void changeToTransport( ProductExchange productExchange ) {
		changeStatus( productExchange, ExchangeStatus.COD_EM_TRANSPORTE );
	}

	public void changeToDelivered( ProductExchange productExchange ) {
		changeStatus( productExchange, ExchangeStatus.COD_ENTREGUE );
	}

	private void changeStatus( ProductExchange productExchange, String statusCode ) {
		try {
			ExchangeStatusFilter statusFilter = new ExchangeStatusFilter();
			statusFilter.setCode( statusCode );

			ICommand command;
			command = FactoryCommand.build( statusFilter, EOperation.FIND );
			List < ExchangeStatus > statusList = command.execute().getEntityList();

			ExchangeStatus status = null;
			if( statusList != null && ! statusList.isEmpty() ) {
				status = statusList.get( 0 );
			}
			productExchange.setStatus( status );

			command = FactoryCommand.build( productExchange, EOperation.UPDATE );
			command.execute();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearFilter() {
		filter = new ProductExchangeFilter();
		if( customer != null ) {
			filter.setCustomer( customer );
		}
	}

	public String getImagePath( Product product ) {
		String path;
		if( product != null ) {
			path = SaveDirectory.REQUEST_IMG_DIR + product.getImage();
		} else {
			path = "default.jpg";
		}
		return path;
	}

	public ProductExchangeFilter getFilter() {
		return filter;
	}

	public void setFilter( ProductExchangeFilter filter ) {
		this.filter = filter;
	}

	public List < ProductExchange > getExchangeResults() {
		return exchangeResults;
	}

	public void setExchangeResults( List < ProductExchange > exchangeResults ) {
		this.exchangeResults = exchangeResults;
	}

	public List < ExchangeStatus > getExchangeStatusList() {
		return exchangeStatusList;
	}

	public void setExchangeStatusList( List < ExchangeStatus > exchangeStatusList ) {
		this.exchangeStatusList = exchangeStatusList;
	}

	public ProductExchange getSelectedExchange() {
		return selectedExchange;
	}

	public void setSelectedExchange( ProductExchange selectedExchange ) {
		this.selectedExchange = selectedExchange;
	}

	public List < Customer > getCustomerList() {
		return customerList;
	}

	public void setCustomerList( List < Customer > customerList ) {
		this.customerList = customerList;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

}
