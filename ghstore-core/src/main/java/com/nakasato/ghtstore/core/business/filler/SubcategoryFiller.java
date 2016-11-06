package com.nakasato.ghtstore.core.business.filler;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.SubcategoryFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Subcategory;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghtstore.core.business.Filler;

public class SubcategoryFiller extends Filler {

	@ Override
	public String fill( AbstractDomainEntity entity ) {
		ICommand command;
		try {
			if( entity !=null &&entity instanceof Product ) {
				Product product =( Product ) entity;
				if( product.getSubcategory() !=null &&( product.getSubcategory().getId() !=null
						|| !StringUtils.isEmpty( product.getSubcategory().getDescription() ) ) ) {

					SubcategoryFilter filter =new SubcategoryFilter();
					filter.setId( product.getSubcategory().getId() );
					filter.setDescription( product.getSubcategory().getDescription() );
					command =FactoryCommand.build( filter, EOperation.FIND );
					List < AbstractDomainEntity > subcategoryList =command.execute().getEntityList();
					if( !ListUtils.isListEmpty( subcategoryList ) ) {
						Subcategory subcategory =new Subcategory();
						subcategory =( Subcategory ) subcategoryList.get( 0 );
						product.setSubcategory( subcategory );
					} else {
						product.getSubcategory().setInsertDate( new Date() );
						product.getSubcategory().setStoreCategory( product.getStoreCategory() );
					}
				}
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		return null;
	}

}
