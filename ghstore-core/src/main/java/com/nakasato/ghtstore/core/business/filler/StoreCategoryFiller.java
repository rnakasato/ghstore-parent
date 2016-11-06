package com.nakasato.ghtstore.core.business.filler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.StoreCategoryFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghtstore.core.business.Filler;

public class StoreCategoryFiller extends Filler {

	@ Override
	public String fill( AbstractDomainEntity entity ) {
		ICommand command;
		try {
			if( entity !=null &&entity instanceof Product ) {

				Product product =( Product ) entity;
				if( product.getStoreCategory() !=null &&( product.getStoreCategory().getId() !=null
						|| !StringUtils.isEmpty( product.getStoreCategory().getDescription() ) ) ) {

					StoreCategoryFilter filter =new StoreCategoryFilter();
					filter.setId( product.getStoreCategory().getId() );
					filter.setDescription( product.getStoreCategory().getDescription() );
					command =new FactoryCommand().build( filter, EOperation.FIND );
					List < AbstractDomainEntity > storeCategoryList =command.execute().getEntityList();

					if( !ListUtils.isListEmpty( storeCategoryList ) ) {
						StoreCategory storeCategory =( StoreCategory ) storeCategoryList.get( 0 );
						product.setStoreCategory( storeCategory );
					}
				}

			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		return null;
	}

}
