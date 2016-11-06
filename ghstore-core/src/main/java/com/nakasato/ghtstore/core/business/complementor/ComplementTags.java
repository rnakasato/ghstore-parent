package com.nakasato.ghtstore.core.business.complementor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.domain.filter.impl.TagFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Tag;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementTags extends Complementor < Product > {

	@ Override
	public String complement( Product entity ) {
		Product p =( Product ) entity;
		ICommand commandFind;
		List < Tag > tagList =new ArrayList < Tag >();
		try {

			if( p.getTagList() !=null ) {
				for( Tag t: p.getTagList() ) {
					TagFilter filter =new TagFilter();
					filter.setDescription( t.getDescription() );
					commandFind =FactoryCommand.build( filter, EOperation.FIND );
					List < Tag > tags =commandFind.execute().getEntityList();
					if( tags !=null && !tags.isEmpty() ) {
						for( Tag tag: tags ) {
							if( tag.getDescription().equals( filter.getDescription() ) ) {
								tagList.add( tag );
							}
						}
					} else {
						t.setInsertDate( new Date() );
						tagList.add( t );
					}
				}
				p.setTagList( tagList );
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		return null;
	}

}
