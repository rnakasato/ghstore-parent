package com.nakasato.ghstore.web.mb.productreturn;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.filter.impl.ReturnStatusFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnStatus;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.ghstore.web.mb.user.LoginMB;

@ManagedBean( name = "returnMB" )
@ViewScoped
public class ReturnMB extends BaseMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	protected ProductReturnFilter filter;
	protected List < ProductReturn > returnResults;
	protected List < ReturnStatus > returnStatusList;
	protected ProductReturn selectedReturn;

	private List < Customer > customerList;

	protected Customer customer;

	// Inicialização
	@PostConstruct
	public void init() {
		filter = new ProductReturnFilter();
		initReturnStatus();
		initCustomerList();

		User user = ( User ) loginMB.getLoggedUser();
		if( user instanceof Customer ) {
			customer = ( Customer ) user;
			filter.setCustomer( customer );
		}
	}

	private void initCustomerList() {
		try {
			ICommand commandFindAll = FactoryCommand.build( new Customer(), EOperation.FINDALL );
			customerList = commandFindAll.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	// Busca
	public void listProductReturn() {
		try {

			boolean hasError = false;
			if( filter != null ) {
				hasError = validateDates();
			}
			if( ! hasError ) {
				ICommand command;
				command = FactoryCommand.build( filter, EOperation.FIND );
				returnResults = command.execute().getEntityList();
			}
		} catch( ClassNotFoundException e1 ) {
			e1.printStackTrace();
		}
	}

	public void viewDetails( ProductReturn productReturn ) {
		selectedReturn = productReturn;

	}

	protected void initReturnStatus() {
		try {
			ICommand commandFindAll = FactoryCommand.build( new ReturnStatus(), EOperation.FINDALL );
			returnStatusList = commandFindAll.execute().getEntityList();
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
	public void changeToAccepted( ProductReturn productReturn ) {
		changeStatus( productReturn, ReturnStatus.COD_ACEITO );
	}

	public void changeToReceived( ProductReturn productReturn ) {
		changeStatus( productReturn, ReturnStatus.COD_RECEBIDO );
	}

	public void changeToRejected( ProductReturn productReturn ) {
		changeStatus( productReturn, ReturnStatus.COD_REJEITADO );
	}

	public void changeToInReturn( ProductReturn productReturn ) {
		changeStatus( productReturn, ReturnStatus.COD_EM_RETORNO );
	}

	public void changeToReturned( ProductReturn productReturn ) {
		changeStatus( productReturn, ReturnStatus.COD_RETORNADO );
	}

	private void changeStatus( ProductReturn productReturn, String statusCode ) {
		try {
			ReturnStatusFilter statusFilter = new ReturnStatusFilter();
			statusFilter.setCode( statusCode );

			ICommand command;
			command = FactoryCommand.build( statusFilter, EOperation.FIND );
			List < ReturnStatus > statusList = command.execute().getEntityList();

			ReturnStatus status = null;
			if( statusList != null && ! statusList.isEmpty() ) {
				status = statusList.get( 0 );
			}
			productReturn.setStatus( status );

			command = FactoryCommand.build( productReturn, EOperation.UPDATE );
			command.execute();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearFilter() {
		filter = new ProductReturnFilter();

		if( customer != null ) {
			filter.setCustomer( customer );
		}
	}

	// Getters e Setters
	public String getImagePath( Product product ) {
		String path;
		if( product != null ) {
			path = SaveDirectory.REQUEST_IMG_DIR + product.getImage();
		} else {
			path = "default.jpg";
		}
		return path;
	}

	public ProductReturnFilter getFilter() {
		return filter;
	}

	public void setFilter( ProductReturnFilter filter ) {
		this.filter = filter;
	}

	public List < ProductReturn > getReturnResults() {
		return returnResults;
	}

	public void setReturnResults( List < ProductReturn > returnResults ) {
		this.returnResults = returnResults;
	}

	public List < ReturnStatus > getReturnStatusList() {
		return returnStatusList;
	}

	public void setReturnStatusList( List < ReturnStatus > returnStatusList ) {
		this.returnStatusList = returnStatusList;
	}

	public ProductReturn getSelectedReturn() {
		return selectedReturn;
	}

	public void setSelectedReturn( ProductReturn selectedReturn ) {
		this.selectedReturn = selectedReturn;
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
