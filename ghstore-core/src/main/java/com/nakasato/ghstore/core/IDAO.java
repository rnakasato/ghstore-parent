package com.nakasato.ghstore.core;

import java.sql.SQLException;
import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public interface IDAO {
	public void save(AbstractDomainEntity entity) throws SQLException;
	public void update(AbstractDomainEntity entity) throws SQLException;
	public void delete(AbstractDomainEntity entity) throws SQLException;
	public List<AbstractDomainEntity> find(AbstractDomainEntity entity)throws SQLException;
	public List<AbstractDomainEntity> findAll()throws SQLException;

}
