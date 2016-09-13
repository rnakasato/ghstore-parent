package com.nakasato.ghstore.factory.impl;

import java.util.HashMap;
import java.util.Map;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.dao.impl.CityDAO;
import com.nakasato.ghstore.core.dao.impl.CustomerDAO;
import com.nakasato.ghstore.core.dao.impl.OrderDAO;
import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.core.dao.impl.ShoppingCartDAO;
import com.nakasato.ghstore.core.dao.impl.StateDAO;
import com.nakasato.ghstore.core.dao.impl.StoreCategoryDAO;
import com.nakasato.ghstore.core.dao.impl.SubcategoryDAO;
import com.nakasato.ghstore.core.dao.impl.SysUserDAO;
import com.nakasato.ghstore.core.dao.impl.TagDAO;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.filter.impl.StoreCategoryFilter;
import com.nakasato.ghstore.core.filter.impl.SubcategoryFilter;
import com.nakasato.ghstore.core.filter.impl.TagFilter;
import com.nakasato.ghstore.domain.City;
import com.nakasato.ghstore.domain.Customer;
import com.nakasato.ghstore.domain.Order;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.ShoppingCart;
import com.nakasato.ghstore.domain.State;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.domain.Subcategory;
import com.nakasato.ghstore.domain.SysUser;
import com.nakasato.ghstore.domain.Tag;

public class FactoryDAO {

	private static Map<String, IDAO> daoMap;

	private static void initMap() {
		if (daoMap == null) {
			daoMap = new HashMap<>();
			daoMap.put(Product.class.getName(), new ProductDAO());
			daoMap.put(StoreCategory.class.getName(), new StoreCategoryDAO());
			daoMap.put(Subcategory.class.getName(), new SubcategoryDAO());
			daoMap.put(Tag.class.getName(), new TagDAO());
			daoMap.put(Customer.class.getName(), new CustomerDAO());
			daoMap.put(SysUser.class.getName(), new SysUserDAO());
			daoMap.put(City.class.getName(), new CityDAO());
			daoMap.put(State.class.getName(), new StateDAO());
			daoMap.put(Order.class.getName(), new OrderDAO());
			daoMap.put(ShoppingCart.class.getName(), new ShoppingCartDAO());

			// TEMPORÁRIO ATÉ ADIÇÃO DOS FILTROS
			daoMap.put(ProductFilter.class.getName(), new ProductDAO());
			daoMap.put(TagFilter.class.getName(), new TagDAO());
			daoMap.put(StoreCategoryFilter.class.getName(), new StoreCategoryDAO());
			daoMap.put(SubcategoryFilter.class.getName(), new SubcategoryDAO());

		}
	}

	/**
	 * @param className
	 * @return retorna instância de DAO para o Objeto
	 */
	public static IDAO build(String className) throws ClassNotFoundException {
		initMap();
		IDAO dao = daoMap.get(className);
		if (dao == null) {
			throw new ClassNotFoundException();
		}
		return dao;
	}
}
