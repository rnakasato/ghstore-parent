package com.nakasato.ghstore.web.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.nakasato.ghstore.domain.Product;

@ManagedBean( name = "productDetailMB")
@ViewScoped
public class ProductDetailMB extends ProductMB{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer amount;
	private Integer maxValue;
	
	@PostConstruct
	public void init(){		
		product = (Product) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("product");
		if (product != null) {
			name = product.getName();
			category = product.getStoreCategory().getDescription();
			subcategory = product.getSubcategory().getDescription();
			price = product.getPrice();
			image = product.getImage();
			description = product.getDescription();
			status = product.getStatus();
		}
		amount = 1;
		maxValue = product.getStock();
	}
	
	public void addAmount(){
		if(amount == null){
			amount = 1;
		}
		if(amount < product.getStock()){
			amount += 1;
		}
	}
	
	public void removeAmount(){
		if(amount == null){
			amount = 1;
		}
		if(amount > 1){
			amount -= 1;
		}
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	
	public void resetPage(){
		amount = 1;
	}
	
}
