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

}
