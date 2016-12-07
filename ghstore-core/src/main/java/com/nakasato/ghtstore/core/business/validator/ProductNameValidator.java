package com.nakasato.ghtstore.core.business.validator;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghtstore.core.business.Validator;

/**
 * Responsável por validar se o estoque é menor do que zero
 * 
 * @author Rafael
 *
 */
public class ProductNameValidator extends Validator < Product > {

	@Override
	public String validate( Product entity ) {
		msg = null;
		boolean alreadyExists = false;
		try {
			ProductDAO dao = new ProductDAO();
			dao.setSession( SessionThreadLocal.getSession() );

			ProductFilter filter = new ProductFilter();
			filter.setExactlyName( entity.getName() );
			List < Product > productList = dao.find( filter );

			if( ListUtils.isNotEmpty( productList ) ) {
				for( Product product: productList ) {
					if( ! product.equals( entity ) ) {
						alreadyExists = true;
						break;
					}
				}

			}

			if( alreadyExists ) {
				msg = "Já existe um produto com o mesmo nome";
			}

		} catch( Exception e ) {
			msg = "Erro inesperado";
			e.printStackTrace();
		}

		return msg;
	}

}
