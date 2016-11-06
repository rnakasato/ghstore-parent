package com.nakasato.ghtstore.core.business.validator;

import java.util.List;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.StoreCategoryFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.factory.impl.FactoryDAO;
import com.nakasato.ghtstore.core.business.Validator;

/**
 * Responsável por verificar se a categoria do produto a ser salvo existe no
 * banco de dados, caso não exista, deverá ser cancelada a operação
 * 
 * @author Rafael
 *
 */
public class StoreCategoryValidator extends Validator {

	@ Override
	public String validate( AbstractDomainEntity entity ) {
		Product p =( Product ) entity;
		msg =null;
		IDAO dao;
		try {
			dao =FactoryDAO.build( StoreCategory.class.getName() );
			StoreCategoryFilter filter =new StoreCategoryFilter();
			filter.setDescription( p.getStoreCategory().getDescription() );
			filter.setId( p.getStoreCategory().getId() );

			List < StoreCategory > scList =( List < StoreCategory > ) dao.find( filter );
			if( ListUtils.isListEmpty( scList ) ) {
				msg ="Categoria inválida";
			} else {
				p.setStoreCategory( scList.get( 0 ) );
			}
		} catch( Exception e ) {
			e.printStackTrace();
			msg ="Erro inesperado";
		}
		return msg;
	}

}
