package com.nakasato.ghstore.domain.gallery;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class GalleryItem extends AbstractDomainEntity {
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage( String image ) {
		this.image = image;
	}

	@Override
	public boolean equals( Object obj ) {
		boolean same = false;
		if( obj instanceof GalleryItem ) {
			GalleryItem item = ( GalleryItem ) obj;
			if( item.getImage() != null && this.getImage() != null ) {
				same = item.getImage().equals( this.getImage() );
			}
		}
		return same;
	}

}
