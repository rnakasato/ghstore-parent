package com.nakasato.ghstore.core.impl;

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

public class Facade<T extends AbstractDomainEntity> implements IFacade<T> {

	private Result result;

	public Facade() {

	}

	@Override
	public Result<T> save(T entity) {
		Result<T> result = new Result<T>();
		String nmClasse = entity.getClass().getName();

		String msg = runRules(entity, EOperation.SAVE);

		if (msg == null) {

			try {
				IDAO dao = FactoryDAO.build(nmClasse);
				dao.save(entity);
				List<T> entityList = new ArrayList<T>();
				entityList.add(entity);
				result.setEntityList(entityList);
			} catch (Exception e) {
				e.printStackTrace();
				result.setMsg("Erro inesperado ao salvar!");
			}
			runPostRules(entity, EOperation.SAVE);
		} else {
			result.setMsg(msg);
		}

		return result;
	}

	@Override
	public Result<T> update(T entity) {
		Result<T> result = new Result<T>();
		String nmClasse = entity.getClass().getName();

		String msg = runRules(entity, EOperation.UPDATE);

		if (msg == null) {

			try {
				IDAO dao = FactoryDAO.build(nmClasse);
				dao.update(entity);
				List<T> entityList = new ArrayList<T>();
				entityList.add(entity);
				result.setEntityList(entityList);
			} catch (Exception e) {
				e.printStackTrace();
				result.setMsg("Não foi possível realizar a alteração!");
			}
			runPostRules(entity, EOperation.UPDATE);
		} else {
			result.setMsg(msg);
		}

		return result;

	}

	@Override
	public Result<T> delete(T entity) {
		Result<T> result = new Result<T>();
		String classNm = entity.getClass().getName();

		String msg = runRules(entity, EOperation.DELETE);

		if (msg == null) {
			try {
				IDAO dao = FactoryDAO.build(classNm);
				dao.delete(entity);
				List<T> entityList = new ArrayList<T>();
				entityList.add(entity);
				result.setEntityList(entityList);
			} catch (Exception e) {
				e.printStackTrace();
				result.setMsg("Não foi possível deletar o item!");
			}
		} else {
			result.setMsg(msg);
		}

		return result;

	}

	@Override
	public Result<T> find(T entity) {
		Result<T> result = new Result<T>();
		String classNm = entity.getClass().getName();

		String msg = runRules(entity, EOperation.FIND);

		if (msg == null) {
			try {
				IDAO dao = FactoryDAO.build(classNm);
				result.setEntityList(dao.find(entity));
			} catch (Exception e) {
				e.printStackTrace();
				result.setMsg("Não foi possível realizar a consulta!");
			}
		} else {
			result.setMsg(msg);

		}

		return result;
	}

	@Override
	public Result<T> view(T entity) {
		Result<T> result = new Result<T>();
		result.setEntityList(new ArrayList<T>(1));
		result.getEntityList().add(entity);
		return result;

	}

	private String runRules(T entity, String operation) {
		StringBuilder msg = null;
		List<IStrategy> rules = FactoryStrategy.build(entity, operation);
		if (rules != null) {
			msg = new StringBuilder();
			for (IStrategy s : rules) {
				String m = s.process(entity);
				if (m != null) {
					msg.append(m);
					break;
				}
			}
		}
		String messages = null;
		if (msg.length() > 0) {
			messages = msg.toString();
		}
		return messages;
	}

	private String runPostRules(T entity, String operation) {
		StringBuilder msg = null;
		List<IStrategy> rules = FactoryPostStrategy.build(entity, operation);
		if (rules != null) {
			msg = new StringBuilder();
			for (IStrategy s : rules) {
				String m = s.process(entity);
				if (m != null) {
					msg.append(m);
					break;
				}
			}
		}
		String messages = null;
		if (msg.length() > 0) {
			messages = msg.toString();
		}
		return messages;
	}

	@Override
	public Result<T> findAll(T entity) {
		Result<T> result = new Result<T>();
		String classNm = entity.getClass().getName();

		String msg = runRules(entity, EOperation.FIND);

		if (msg == null) {
			try {
				IDAO dao = FactoryDAO.build(classNm);
				result.setEntityList(dao.findAll());
			} catch (Exception e) {
				e.printStackTrace();
				result.setMsg("Não foi possível realizar a consulta!");
			}
		} else {
			result.setMsg(msg);

		}

		return result;
	}

	@Override
	public Result<T> doRules(T entity, Integer parameter) {
		Result<T> result = new Result<T>();
		StringBuilder msg = null;
		List<IStrategy> rules = FactoryCustomStrategy.build(entity, parameter);
		if (rules != null) {
			msg = new StringBuilder();
			for (IStrategy s : rules) {
				String m = s.process(entity);
				if (m != null) {
					msg.append(m);
					break;
				}
			}
		}
		String messages = null;
		if (msg.length() > 0) {
			messages = msg.toString();
			result.setMsg(messages);
		}
		return result;
	}

}
