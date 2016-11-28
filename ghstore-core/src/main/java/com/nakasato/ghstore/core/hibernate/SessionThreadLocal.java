package com.nakasato.ghstore.core.hibernate;

import org.hibernate.Session;

public class SessionThreadLocal {
	private static final ThreadLocal < Session > sessionThread = new ThreadLocal<>();

	public static Session getSession() {
		if( sessionThread.get() == null ) {
			Session session =  HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			sessionThread.set( session );

		}
		return sessionThread.get();

	}

	public static void commit() {
		sessionThread.get().getTransaction().commit();
	}

	public static void rollback() {
		sessionThread.get().getTransaction().rollback();
	}
	
	public static void closeSession(){
		sessionThread.get().close();
		sessionThread.remove();
	}
}
