package com.nakasato.ghstore.core.thread;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class PromotionInitializerThread extends Thread {
	private static PromotionInitializerThread thread;

	private PromotionInitializerThread() {
	}

	public static PromotionInitializerThread getInstance() {
		if( thread == null ) {
			thread = new PromotionInitializerThread();
		}
		return thread;
	}

	@Override
	public void run() {
		try {
			while( true ) {
				ICommand commandFind = FactoryCommand.build( new Promotion(), EOperation.FINDALL );
				List < Promotion > promoList = commandFind.execute().getEntityList();
				Date now = new Date();
				for( Promotion promotion: promoList ) {
					boolean update = false;
					if( promotion.getEndDate().before( now ) && promotion.getActive() ) {
						promotion.setActive( false );
						promotion.setDisable( true );
						update = true;

						System.out.println( "Encerrando promoção: " + promotion.getDescription() );
					} else if( promotion.getStartDate().equals( now )
							|| ( promotion.getStartDate().before( now ) && promotion.getEndDate().after( now ) ) ) {
						promotion.setActive( true );
						update = true;

						System.out.println( "Ativando promoção: " + promotion.getDescription() );
					}

					if( update ) {
						ICommand commandUpdate = FactoryCommand.build( promotion, EOperation.UPDATE );
						String msg = commandUpdate.execute().getMsg();
						if( StringUtils.isNotEmpty( msg ) ) {
							System.out.println( msg );
						}
					}
				}

				getInstance().sleep( 10000 );

			}
		} catch( ClassNotFoundException | InterruptedException e ) {
			e.printStackTrace();
		}

	}
	
	public static void finishThread(){
		thread = null;
	}

}
