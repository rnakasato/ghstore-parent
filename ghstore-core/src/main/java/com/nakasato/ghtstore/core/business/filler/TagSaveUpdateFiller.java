package com.nakasato.ghtstore.core.business.filler;

import java.util.Date;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Tag;
import com.nakasato.ghtstore.core.business.Filler;

public class TagSaveUpdateFiller extends Filler {

	@ Override
	public String fill( AbstractDomainEntity entity ) {
		if( entity instanceof Product ) {
			Product product =( Product ) entity;
			for( Tag tag: product.getTagList() ) {
				if( tag.getId() !=null &&tag.getInsertDate() ==null ) {
					tag.setInsertDate( new Date() );
				}
			}
		}
		return null;
	}

}
