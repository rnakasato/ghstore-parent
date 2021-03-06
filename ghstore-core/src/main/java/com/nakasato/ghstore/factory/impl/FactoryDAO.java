package com.nakasato.ghstore.factory.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.dao.impl.AddressDAO;
import com.nakasato.ghstore.core.dao.impl.AdministratorDAO;
import com.nakasato.ghstore.core.dao.impl.CityDAO;
import com.nakasato.ghstore.core.dao.impl.CustomerDAO;
import com.nakasato.ghstore.core.dao.impl.DiscountCouponDAO;
import com.nakasato.ghstore.core.dao.impl.ExchangeStatusDAO;
import com.nakasato.ghstore.core.dao.impl.GalleryItemDAO;
import com.nakasato.ghstore.core.dao.impl.OperatorDAO;
import com.nakasato.ghstore.core.dao.impl.OrderDAO;
import com.nakasato.ghstore.core.dao.impl.OrderItemDAO;
import com.nakasato.ghstore.core.dao.impl.OrderStatusDAO;
import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.core.dao.impl.ProductExchangeDAO;
import com.nakasato.ghstore.core.dao.impl.ProductReturnDAO;
import com.nakasato.ghstore.core.dao.impl.PromotionDAO;
import com.nakasato.ghstore.core.dao.impl.ReturnStatusDAO;
import com.nakasato.ghstore.core.dao.impl.StateDAO;
import com.nakasato.ghstore.core.dao.impl.StoreCategoryDAO;
import com.nakasato.ghstore.core.dao.impl.SubcategoryDAO;
import com.nakasato.ghstore.core.dao.impl.TagDAO;
import com.nakasato.ghstore.core.dao.impl.UserTypeDAO;
import com.nakasato.ghstore.core.dao.impl.WishlistDAO;
import com.nakasato.ghstore.domain.filter.impl.AdministratorFilter;
import com.nakasato.ghstore.domain.filter.impl.CityFilter;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.filter.impl.DiscountCouponFilter;
import com.nakasato.ghstore.domain.filter.impl.ExchangeStatusFilter;
import com.nakasato.ghstore.domain.filter.impl.OperatorFilter;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.filter.impl.OrderStatusFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductExchangeFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.filter.impl.PromotionFilter;
import com.nakasato.ghstore.domain.filter.impl.ReturnStatusFilter;
import com.nakasato.ghstore.domain.filter.impl.StateFilter;
import com.nakasato.ghstore.domain.filter.impl.StoreCategoryFilter;
import com.nakasato.ghstore.domain.filter.impl.SubcategoryFilter;
import com.nakasato.ghstore.domain.filter.impl.TagFilter;
import com.nakasato.ghstore.domain.filter.impl.UserTypeFilter;
import com.nakasato.ghstore.domain.filter.impl.WishlistFilter;
import com.nakasato.ghstore.domain.gallery.GalleryItem;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.order.OrderStatus;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.product.Subcategory;
import com.nakasato.ghstore.domain.product.Tag;
import com.nakasato.ghstore.domain.productexchange.DiscountCoupon;
import com.nakasato.ghstore.domain.productexchange.ExchangeStatus;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnStatus;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Operator;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.domain.user.UserType;
import com.nakasato.ghstore.domain.user.Wishlist;

public class FactoryDAO {

	private static Map < String, IDAO > daoMap;

	private static void initMap() {
		if( daoMap == null ) {
			daoMap = new HashMap<>();
			
			// DAOs do conjunto de produto
			daoMap.put( Product.class.getName(), new ProductDAO() );
			daoMap.put( StoreCategory.class.getName(), new StoreCategoryDAO() );
			daoMap.put( Subcategory.class.getName(), new SubcategoryDAO() );
			daoMap.put( Tag.class.getName(), new TagDAO() );
			daoMap.put( Promotion.class.getName(), new PromotionDAO() );
			
			// DAOs do conjunto de usu�rio
			daoMap.put( Customer.class.getName(), new CustomerDAO() );
			daoMap.put( Administrator.class.getName(), new AdministratorDAO() );
			daoMap.put( Operator.class.getName(), new OperatorDAO() );
			daoMap.put( UserType.class.getName(), new UserTypeDAO() );
			daoMap.put( Address.class.getName(), new AddressDAO() );
			daoMap.put( City.class.getName(), new CityDAO() );
			daoMap.put( State.class.getName(), new StateDAO() );
			
			// DAOs do conjunto de pedido
			daoMap.put( Order.class.getName(), new OrderDAO() );
			daoMap.put( OrderItem.class.getName(), new OrderItemDAO() );
			
			// DAOs do conjunto de devolu��o e troca
			daoMap.put( ProductReturn.class.getName(), new ProductReturnDAO() );
			daoMap.put( ProductExchange.class.getName(), new ProductExchangeDAO() );
			daoMap.put( DiscountCoupon.class.getName(), new DiscountCouponDAO() );
			
			// DAOs de status
			daoMap.put( OrderStatus.class.getName(), new OrderStatusDAO() );
			daoMap.put( ReturnStatus.class.getName(), new ReturnStatusDAO() );
			daoMap.put( ExchangeStatus.class.getName(), new ExchangeStatusDAO() );
			
			// Outros DAOs
			daoMap.put( Wishlist.class.getName(), new WishlistDAO() );
			daoMap.put( GalleryItem.class.getName(), new GalleryItemDAO() );


			// Filtros
			daoMap.put( ProductFilter.class.getName(), new ProductDAO() );
			daoMap.put( TagFilter.class.getName(), new TagDAO() );
			daoMap.put( StoreCategoryFilter.class.getName(), new StoreCategoryDAO() );
			daoMap.put( SubcategoryFilter.class.getName(), new SubcategoryDAO() );
			daoMap.put( PromotionFilter.class.getName(), new PromotionDAO() );
			
			daoMap.put( CustomerFilter.class.getName(), new CustomerDAO() );
			daoMap.put( AdministratorFilter.class.getName(), new AdministratorDAO() );
			daoMap.put( OperatorFilter.class.getName(), new OperatorDAO() );
			daoMap.put( UserTypeFilter.class.getName(), new UserTypeDAO() );
			daoMap.put( CityFilter.class.getName(), new CityDAO() );
			daoMap.put( StateFilter.class.getName(), new StateDAO() );

			daoMap.put( OrderFilter.class.getName(), new OrderDAO() );
			
			daoMap.put( ProductReturnFilter.class.getName(), new ProductReturnDAO() );
			daoMap.put( ProductExchangeFilter.class.getName(), new ProductExchangeDAO() );
			daoMap.put( DiscountCouponFilter.class.getName(), new DiscountCouponDAO() );
			
			daoMap.put( WishlistFilter.class.getName(), new WishlistDAO() );
			
			daoMap.put( OrderStatusFilter.class.getName(), new OrderStatusDAO() );
			daoMap.put( ReturnStatusFilter.class.getName(), new ReturnStatusDAO() );
			daoMap.put( ExchangeStatusFilter.class.getName(), new ExchangeStatusDAO() );

		}
	}

	/**
	 * @param className
	 * @return retorna inst�ncia de DAO para o Objeto
	 */
	public static IDAO build( String className, Session session ) throws ClassNotFoundException {
		initMap();
		IDAO dao = daoMap.get( className );
		dao.setSession( session );
		if( dao == null ) {
			throw new ClassNotFoundException();
		}
		return dao;
	}
}
