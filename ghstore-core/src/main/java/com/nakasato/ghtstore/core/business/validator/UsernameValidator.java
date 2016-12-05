package com.nakasato.ghtstore.core.business.validator;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.AdministratorDAO;
import com.nakasato.ghstore.core.dao.impl.CustomerDAO;
import com.nakasato.ghstore.core.dao.impl.OperatorDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.filter.impl.AdministratorFilter;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.filter.impl.OperatorFilter;
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Operator;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghtstore.core.business.Validator;

import br.com.caelum.stella.validation.InvalidStateException;

/**
 * 
 * @author rafae Descrição:
 */
public class UsernameValidator extends Validator < User > {

	@Override
	public String validate( User user ) {
		msg = null;
		try {
			boolean exists = false;
			// Carrega todos os clientes com o mesmo nome de usuário (caso
			// exista)
			CustomerDAO customerDAO = new CustomerDAO();
			customerDAO.setSession( SessionThreadLocal.getSession() );

			CustomerFilter customerFilter = new CustomerFilter();
			customerFilter.setUserName( user.getUsername() );

			List < Customer > customerList = customerDAO.find( customerFilter );

			if( ListUtils.isNotEmpty( customerList ) ) {
				exists = true;
			}

			if( ! exists ) {
				// Carrega todos os administradores com o mesmo nome de usuário
				// (caso
				// exista)
				AdministratorDAO adminDAO = new AdministratorDAO();
				adminDAO.setSession( SessionThreadLocal.getSession() );

				AdministratorFilter adminFilter = new AdministratorFilter();
				adminFilter.setLoginUserName( user.getUsername() );

				List < Administrator > adminList = adminDAO.find( adminFilter );

				if( ListUtils.isNotEmpty( adminList ) ) {
					exists = true;
				}
			}

			if( ! exists ) {
				// Carrega todos os operadores com o mesmo nome de usuário (caso
				// exista)
				OperatorDAO operatorDAO = new OperatorDAO();
				operatorDAO.setSession( SessionThreadLocal.getSession() );

				OperatorFilter opFilter = new OperatorFilter();
				opFilter.setLoginUserName( user.getUsername() );

				List < Operator > opList = operatorDAO.find( opFilter );

				if( ListUtils.isNotEmpty( opList ) ) {
					exists = true;
				}
			}

			if( exists ) {
				msg = "O nome de usuário " + user.getUsername() + "já está sendo utilizado";
			}

		} catch(

		Exception e ) {
			msg = "Erro inesperado";
		}
		return msg;
	}

}
