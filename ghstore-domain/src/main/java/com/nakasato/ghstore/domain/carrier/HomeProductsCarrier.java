package com.nakasato.ghstore.domain.carrier;

import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.EntityCarrier;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.group.MostSoldProduct;
import com.nakasato.ghstore.domain.user.Customer;

public class HomeProductsCarrier extends EntityCarrier {
	public static final Integer MAX_RESULTS = 5;

	private List < MostSoldProduct > recommendedProducts;
	private List < Product > newProducts;
	private List < MostSoldProduct > mostSold;

	private Customer customer;

	public List < MostSoldProduct > getRecommendedProducts() {
		return recommendedProducts;
	}

	public void setRecommendedProducts( List < MostSoldProduct > recommendedProducts ) {
		this.recommendedProducts = recommendedProducts;
	}

	public List < Product > getNewProducts() {
		return newProducts;
	}

	public void setNewProducts( List < Product > newProducts ) {
		this.newProducts = newProducts;
	}

	public List < MostSoldProduct > getMostSold() {
		return mostSold;
	}

	public void setMostSold( List < MostSoldProduct > mostSold ) {
		this.mostSold = mostSold;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer = customer;
	}

}
