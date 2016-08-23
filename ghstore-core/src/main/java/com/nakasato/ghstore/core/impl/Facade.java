package com.nakasato.ghstore.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.IFacade;
import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.factory.impl.FactoryCustomStrategy;
import com.nakasato.ghstore.factory.impl.FactoryDAO;
import com.nakasato.ghstore.factory.impl.FactoryPostStrategy;
import com.nakasato.ghstore.factory.impl.FactoryStrategy;

public class Facade implements IFacade {

	private Result result;
	
	public Facade(){				
	
	}
	
	
	@Override
	public Result save(AbstractDomainEntity entity) {
		result = new Result();
		String nmClasse = entity.getClass().getName();	
		
		String msg = runRules(entity, EOperation.SAVE);
		
		
		if(msg == null){
			
			try {
				IDAO dao = FactoryDAO.build(nmClasse);
				dao.save(entity);
				List<AbstractDomainEntity> entityList = new ArrayList<AbstractDomainEntity>();
				entityList.add(entity);
				result.setEntityList(entityList);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				result.setMsg("Não foi possível adicionar o produto!");
				
			}
			runPostRules(entity, EOperation.SAVE);
		}else{
			result.setMsg(msg);
		}
		
		return result;
	}

	@Override
	public Result update(AbstractDomainEntity entity) {
		result = new Result();
		String nmClasse = entity.getClass().getName();	
		
		String msg = runRules(entity, EOperation.UPDATE);
	
		if(msg == null){
			
			try {
				IDAO dao = FactoryDAO.build(nmClasse);
				dao.update(entity);
				List<AbstractDomainEntity> entityList = new ArrayList<AbstractDomainEntity>();
				entityList.add(entity);
				result.setEntityList(entityList);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				result.setMsg("Não foi possível realizar a alteração!");
				
			}
			runPostRules(entity, EOperation.UPDATE);
		}else{
			result.setMsg(msg);
		}
		
		return result;

	}

	@Override
	public Result delete(AbstractDomainEntity entity) {
		result = new Result();
		String classNm = entity.getClass().getName();	
		
		String msg = runRules(entity, EOperation.DELETE);
		
		if(msg == null){
			try {
				IDAO dao = FactoryDAO.build(classNm);			
				dao.delete(entity);
				List<AbstractDomainEntity> entityList = new ArrayList<AbstractDomainEntity>();
				entityList.add(entity);
				result.setEntityList(entityList);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				result.setMsg("Não foi possível deletar o item!");
				
			}
		}else{
			result.setMsg(msg);
		}
		
		return result;

	}

	@Override
	public Result find(AbstractDomainEntity entity) {
		result = new Result();
		String classNm = entity.getClass().getName();	
		
		String msg = runRules(entity, EOperation.FIND);
		
		
		if(msg == null){
			try {
				IDAO dao = FactoryDAO.build(classNm);			
				result.setEntityList(dao.find(entity));				
			} catch (SQLException e) {
				e.printStackTrace();
				result.setMsg("Não foi possível realizar a consulta!");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				result.setMsg("Não foi possível realizar a consulta!");
			}
		}else{
			result.setMsg(msg);
			
		}
		
		return result;
	}
	
	@Override
	public Result view(AbstractDomainEntity entity) {
		result = new Result();
		result.setEntityList(new ArrayList<AbstractDomainEntity>(1));
		result.getEntityList().add(entity);		
		return result;

	}

	
	private String runRules(AbstractDomainEntity entity, String operation){
		StringBuilder msg = null;
		List<IStrategy> rules = FactoryStrategy.build(entity, operation);
		if(rules != null){
			msg = new StringBuilder();
			for(IStrategy s: rules){			
				String m = s.process(entity);
				if(m != null){
					msg.append(m);
					break;
				}			
			}	
		}
		String messages = null; 
		if(msg.length()>0){
			messages = msg.toString();
		}
		return messages;
	}
	
	private String runPostRules(AbstractDomainEntity entity, String operation){
		StringBuilder msg = null;
		List<IStrategy> rules = FactoryPostStrategy.build(entity, operation);
		if(rules != null){
			msg = new StringBuilder();
			for(IStrategy s: rules){			
				String m = s.process(entity);
				if(m != null){
					msg.append(m);
					break;
				}			
			}	
		}
		String messages = null; 
		if(msg.length()>0){
			messages = msg.toString();
		}
		return messages;
	}


	@Override
	public Result findAll(AbstractDomainEntity entity) {
		result = new Result();
		String classNm = entity.getClass().getName();	
		
		String msg = runRules(entity, EOperation.FIND);
		
		
		if(msg == null){
			try {
				IDAO dao = FactoryDAO.build(classNm);			
				result.setEntityList(dao.findAll());				
			} catch (SQLException e) {
				e.printStackTrace();
				result.setMsg("Não foi possível realizar a consulta!");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				result.setMsg("Não foi possível realizar a consulta!");
			}
		}else{
			result.setMsg(msg);
			
		}
		
		return result;
	}


	@Override
	public Result doRules(AbstractDomainEntity entity, Integer parameter) {
		Result result = null;
		StringBuilder msg = null;
		List<IStrategy> rules = FactoryCustomStrategy.build(entity, parameter);
		if(rules != null){
			msg = new StringBuilder();
			for(IStrategy s: rules){			
				String m = s.process(entity);
				if(m != null){
					msg.append(m);
					break;
				}			
			}	
		}
		String messages = null; 
		if(msg.length()>0){
			messages = msg.toString();
		}
		return result;
	}
	
	
}
