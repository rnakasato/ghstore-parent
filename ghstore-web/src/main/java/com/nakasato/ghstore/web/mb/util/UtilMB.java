package com.nakasato.ghstore.web.mb.util;

import javax.faces.bean.ManagedBean;

import com.nakasato.ghstore.core.util.SaveDirectory;

@ManagedBean( name = "utilMB" )
public class UtilMB {
	public String getImagePath( String image ) {
		String ImagePath = null;
		if( image != null ) {
			ImagePath = SaveDirectory.REQUEST_IMG_DIR + image;
		} else {
			ImagePath = "default.jpg";
		}
		return ImagePath;
	}

}
