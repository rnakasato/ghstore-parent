package com.nakasato.ghstore.web.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import com.nakasato.ghstore.domain.user.User;

public class AccessListener implements PhaseListener {

	@Override
	public void afterPhase( PhaseEvent event ) {
		FacesContext context = event.getFacesContext();
		String currentPage = context.getViewRoot().getViewId();

		boolean isLoginPage = currentPage.contains( "login.xhtml" );
		boolean isClientPage = currentPage.contains( "clientuser" );
		boolean isAdminPage = currentPage.contains( "admin" );
		HttpSession session = ( HttpSession ) context.getExternalContext().getSession( true );
		Object currentUser = session.getAttribute( User.LOGGED_USER );

		if( !isLoginPage && ( currentUser == null || currentUser == "" ) ) {
			NavigationHandler nh = context.getApplication().getNavigationHandler();
			if( isClientPage && currentPage.contains( "logged" ) ) {
				nh.handleNavigation( context, null, "/clientuser/login.xhtml" );
			}
		}

	}

	@Override
	public void beforePhase( PhaseEvent event ) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
