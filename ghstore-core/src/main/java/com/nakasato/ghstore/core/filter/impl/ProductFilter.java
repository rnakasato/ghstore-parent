package com.nakasato.ghstore.core.filter.impl;

import java.util.ArrayList;
import java.util.List;

import com.nakasato.ghstore.core.filter.Filter;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.domain.Subcategory;

public class ProductFilter extends Filter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private StoreCategory category;
	private Subcategory subcategory;
	private Double price;
	private Integer stock;
	private String description;
	private Integer status;
	private Product product;
	private List<Subcategory> subcategoryList;
	private List<String> selectedSubcategoryIds;
	private List<StoreCategory> storeCategoryList;
	
	public ProductFilter(){
		category = new StoreCategory();
		subcategory = new Subcategory();
		subcategory.setStoreCategory(new StoreCategory());
		subcategoryList = new ArrayList<>();
		storeCategoryList = new ArrayList<>();
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public StoreCategory getCategory() {
		return category;
	}

	public void setCategory(StoreCategory category) {
		this.category = category;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Subcategory> getSubcategoryList() {
		return subcategoryList;
	}

	public void setSubcategoryList(List<Subcategory> subcategoryList) {
		this.subcategoryList = subcategoryList;
	}

	public List<StoreCategory> getStoreCategoryList() {
		return storeCategoryList;
	}

	public void setStoreCategoryList(List<StoreCategory> storeCategoryList) {
		this.storeCategoryList = storeCategoryList;
	}

	public List<String> getSelectedSubcategoryIds() {
		return selectedSubcategoryIds;
	}

	public void setSelectedSubcategoryIds(List<String> selectedSubcategoryIds) {
		this.selectedSubcategoryIds = selectedSubcategoryIds;
	}
	
}