package com.nakasato.web.util;

import java.io.IOException;

import javax.faces.context.ExternalContext;

public class Redirector {
	public static void redirectTo( ExternalContext context, String pageToRedirect ) {
		String url = context.getRequestContextPath() + pageToRedirect;
		try {
			context.redirect( url );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}

	public static void redirectToExternalPage( ExternalContext context, String pageToRedirect ) {
		String url = pageToRedirect;
		try {
			context.redirect( url );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
}
