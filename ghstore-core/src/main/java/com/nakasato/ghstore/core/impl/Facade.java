package com.nakasato.ghstore.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.IFacade;
import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.EntityCarrier;
import com.nakasato.ghstore.factory.impl.FactoryDAO;
import com.nakasato.ghstore.factory.impl.FactoryStrategy;

public class Facade < T extends AbstractDomainEntity > implements IFacade < T > {

	private Result result;

	public Facade() {

	}

	@Override
	public Result < T > save( T entity ) {
		Result < T > result = new Result < T >();
		String nmClasse = entity.getClass().getName();

		String msg = runRules( entity, EOperation.SAVE );

		if( msg == null ) {

			try {
				Session session = SessionThreadLocal.getSession();
				IDAO dao = FactoryDAO.build( nmClasse, session );
				dao.save( entity );
				List < T > entityList = new ArrayList < T >();
				entityList.add( entity );
				result.setEntityList( entityList );
				SessionThreadLocal.commit();
			} catch( Exception e ) {
				SessionThreadLocal.rollback();
				e.printStackTrace();
				result.setMsg( "Erro inesperado ao salvar!" );
			} finally {
				SessionThreadLocal.closeSession();
			}
		} else {
			result.setMsg( msg );
		}

		return result;
	}

	@Override
	public Result < T > update( T entity ) {
		Result < T > result = new Result < T >();
		String nmClasse = entity.getClass().getName();

		String msg = runRules( entity, EOperation.UPDATE );

		if( msg == null ) {

			try {
				Session session = SessionThreadLocal.getSession();
				IDAO dao = FactoryDAO.build( nmClasse, session );
				dao.update( entity );
				List < T > entityList = new ArrayList < T >();
				entityList.add( entity );
				result.setEntityList( entityList );
				SessionThreadLocal.commit();
			} catch( Exception e ) {
				e.printStackTrace();
				result.setMsg( "Não foi possível realizar a alteração!" );
			} finally {
				SessionThreadLocal.closeSession();
			}
		} else {
			result.setMsg( msg );
		}

		return result;

	}

	@Override
	public Result < T > delete( T entity ) {
		Result < T > result = new Result < T >();
		String classNm = entity.getClass().getName();

		String msg = runRules( entity, EOperation.DELETE );

		if( msg == null ) {
			try {
				Session session = SessionThreadLocal.getSession();
				IDAO dao = FactoryDAO.build( classNm, session );
				dao.delete( entity );
				List < T > entityList = new ArrayList < T >();
				entityList.add( entity );
				result.setEntityList( entityList );
				SessionThreadLocal.commit();
			} catch( Exception e ) {
				e.printStackTrace();
				result.setMsg( "Não foi possível deletar o item!" );
			} finally {
				SessionThreadLocal.closeSession();
			}
		} else {
			result.setMsg( msg );
		}

		return result;

	}

	@Override
	public Result < T > find( T entity ) {
		Result < T > result = new Result < T >();
		String classNm = entity.getClass().getName();

		String msg = runRules( entity, EOperation.FIND );

		if( msg == null && ! ( entity instanceof EntityCarrier ) ) {
			try {
				Session session = SessionThreadLocal.getSession();
				IDAO dao = FactoryDAO.build( classNm, session );
				result.setEntityList( dao.find( entity ) );
			} catch( Exception e ) {
				e.printStackTrace();
				result.setMsg( "Não foi possível realizar a consulta!" );
			} finally {
				SessionThreadLocal.closeSession();
			}
		} else {
			result.setMsg( msg );

		}

		return result;
	}

	public String runRules( T entity, String operation ) {
		StringBuilder msg = null;
		List < IStrategy > rules = FactoryStrategy.build( entity, operation );
		if( rules != null ) {
			msg = new StringBuilder();
			for( IStrategy s: rules ) {
				String m = s.process( entity );
				if( StringUtils.isNotEmpty( m ) ) {
					msg.append( m );
					break;
				}
			}
		}
		String messages = null;
		if( msg != null && msg.length() > 0 ) {
			messages = msg.toString();
		}
		return messages;
	}

	@Override
	public Result < T > findAll( T entity ) {
		Result < T > result = new Result < T >();
		String classNm = entity.getClass().getName();

		String msg = runRules( entity, EOperation.FINDALL );

		if( msg == null ) {
			try {
				Session session = SessionThreadLocal.getSession();
				IDAO dao = FactoryDAO.build( classNm, session );
				result.setEntityList( dao.findAll() );
			} catch( Exception e ) {
				result.setMsg( "Não foi possível realizar a consulta!" );
				SessionThreadLocal.rollback();
			} finally {
				SessionThreadLocal.closeSession();
			}
		} else {
			result.setMsg( msg );

		}

		return result;
	}

}
