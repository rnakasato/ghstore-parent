package com.nakasato.ghstore.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.nakasato.ghstore.core.thread.PromotionInitializerThread;

@WebListener
public class PromotionListener implements ServletContextListener {
	private PromotionInitializerThread t = PromotionInitializerThread.getInstance();

	@Override
	public void contextInitialized( ServletContextEvent sce ) {
		System.out.println( "Inicializando listener de Promo��o" );

		try {
			t.start();
		} catch( Throwable e ) {
			
		}

	}

	@Override
	public void contextDestroyed( ServletContextEvent sce ) {
		System.out.println( "Finalizando listener de Promo��o" );

		t.finishThread();

	}
}
