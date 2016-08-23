package com.nakasato.ghtstore.core.business.validator;

import java.sql.SQLException;
import java.util.List;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.factory.impl.FactoryDAO;
import com.nakasato.ghtstore.core.business.Validator;

/**
 * Respons�vel por verificar se a categoria do produto a ser salvo
 * existe no banco de dados, caso n�o exista, dever� ser cancelada a opera��o
 * @author Rafael
 *
 */
public class StoreCategoryValidator extends Validator{

	@Override
	public String validate(AbstractDomainEntity entity) {
		Product p = (Product) entity;		
		msg = null;
		IDAO dao;
		try {
			dao = FactoryDAO.build(StoreCategory.class.getName());
			List<AbstractDomainEntity> scList = dao.find(p.getStoreCategory());
			if(ListUtils.isListEmpty(scList)){
				msg = "Categoria inv�lida";
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return msg;		
	}

}
