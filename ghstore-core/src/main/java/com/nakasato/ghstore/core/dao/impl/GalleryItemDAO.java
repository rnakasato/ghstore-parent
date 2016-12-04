package com.nakasato.ghstore.core.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.gallery.GalleryItem;

public class GalleryItemDAO extends AbstractDAO < GalleryItem >{

	@Override
	public List < GalleryItem > find( AbstractDomainEntity filter ) throws Exception {
	
		return null;
	}

	@Override
	public List < GalleryItem > findAll() throws Exception {
		List < GalleryItem > itemList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM GalleryItem " );

			Query query = session.createQuery( jpql.toString() );

			itemList = query.getResultList();

			closeSession();
		} catch( RuntimeException e ) {
			cancelSession();
		}
		return itemList;
	}

}
