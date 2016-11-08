package com.nakasato.ghtstore.core.business.filler;

import java.util.ArrayList;
import java.util.List;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.TagFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Tag;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghtstore.core.business.Filler;

public class TagFiller extends Filler {

	@Override
	public String fill( AbstractDomainEntity entity ) {
		ICommand command;
		try {
			Product product = ( Product ) entity;
			TagFilter filter = new TagFilter();
			filter.setProductId( product.getId() );
			command = FactoryCommand.build( filter, EOperation.FIND );
			List < AbstractDomainEntity > resultList = command.execute().getEntityList();
			List < Tag > tagList = new ArrayList<>();
			for( AbstractDomainEntity e: resultList ) {
				Tag t = ( Tag ) e;
				tagList.add( t );
			}
			product.setTagList( tagList );

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		return null;
	}

}
