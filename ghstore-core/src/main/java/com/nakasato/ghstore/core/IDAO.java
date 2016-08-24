package com.nakasato.ghstore.core;

import java.util.List;

import com.nakasato.ghstore.core.filter.Filter;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public interface IDAO <T extends AbstractDomainEntity> {
	public void save(T entity) throws Exception;
	public void delete(T entity) throws Exception;
	public void update(T entity) throws Exception;
	public T findById(T entity) throws Exception;
	public List<T> find(AbstractDomainEntity filter) throws Exception;
	public List<T> findAll() throws Exception;

}
