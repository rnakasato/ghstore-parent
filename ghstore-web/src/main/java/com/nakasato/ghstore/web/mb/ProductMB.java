package com.nakasato.ghstore.web.mb;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.command.impl.Command;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.filter.impl.SubcategoryFilter;
import com.nakasato.ghstore.core.filter.impl.TagFilter;
import com.nakasato.ghstore.core.product.util.ProductSort;
import com.nakasato.ghstore.core.util.ImageUtils;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.core.util.OrderByType;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.domain.Subcategory;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.web.util.Redirector;

@ManagedBean(name = "productMB")
@ViewScoped
public class ProductMB extends BaseMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ProductFilter filter;
	protected String name;
	protected String category;
	protected String subcategory;
	protected Double price;
	protected Double weight;
	protected Integer stock;
	protected String image;
	protected String description;
	protected List<StoreCategory> categoryList;
	protected List<Subcategory> subcategoryList;
	// private List<Subcategory> selectedSubcategoryList;
	protected List<Product> productList;
	protected String ImagePath;
	protected Integer status;
	protected Product product;
	protected List<Tag> tagList;

	protected Integer order;
	protected List<OrderByType> orderTypeList;

	public ProductMB() {
	}

	public List<String> fillSubcategory(String query) {
		SubcategoryFilter filter = new SubcategoryFilter();
		List<String> acSubcategory = null;
		filter.setDescription(query);
		StoreCategory sc = new StoreCategory();
		sc.setDescription(getCategory());
		filter.setStoreCategory(sc);
		try {
			Command command;
			command = FactoryCommand.build(filter, EOperation.FIND);
			List<AbstractDomainEntity> scList = command.execute().getEntityList();
			acSubcategory = new ArrayList<>();
			if (!ListUtils.isListEmpty(scList)) {
				for (AbstractDomainEntity e : scList) {
					Subcategory s = (Subcategory) e;
					acSubcategory.add(s.getDescription());
				}
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return acSubcategory;
	}

	public List<Tag> fillTags(String query) {
		List<Tag> tagList = null;
		TagFilter filter = new TagFilter();
		filter.setDescription(query);
		try {
			Command command;
			command = FactoryCommand.build(filter, EOperation.FIND);
			tagList = command.execute().getEntityList();
			boolean exists = false;

			if (ListUtils.isListEmpty(tagList)) {
				tagList = new ArrayList<>();
			} else {
				for (Tag t : tagList) {
					if (t.getDescription().equals(query)) {
						exists = true;
						break;
					}
				}
			}
			if(!exists){
				Tag tag = new Tag();
				tag.setDescription(query);
				tagList.add(tag);				
			}

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return tagList;
	}

	public void fillSubcategoryByCategory(StoreCategory storeCategory) {
		Subcategory subcategory = new Subcategory();
		subcategory.setStoreCategory(storeCategory);

		try {
			Command command;
			command = FactoryCommand.build(subcategory, EOperation.FIND);

			List<AbstractDomainEntity> scList = command.execute().getEntityList();
			List<Subcategory> subcategoryList = new ArrayList<>();
			for (AbstractDomainEntity sc : scList) {
				Subcategory subcat = (Subcategory) sc;
				subcategoryList.add(subcat);
			}
			this.subcategoryList = subcategoryList;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Arquivo salvo! ", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		try {
			ImageUtils.copyImage(event.getFile().getFileName(), event.getFile().getInputstream());
			image = event.getFile().getFileName();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listProducts() {
		try {
			Command command;
			command = FactoryCommand.build(filter, EOperation.FIND);
			List<Product> products = command.execute().getEntityList();
			if (products != null && !products.isEmpty()) {
				productList = new ArrayList<>();
				for (AbstractDomainEntity e : products) {
					Product pr = (Product) e;
					if (status != null && status != 0) {
						if (pr.getStatus() == status) {
							productList.add(pr);
						}
					} else {
						productList.add(pr);
					}
				}
				if (order != null) {
					ProductSort.sortProducts(productList, order);
				}

			} else {
				productList = null;
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void save() {
		Product p = prepareProduct();
		p.setInsertDate(new Date());
		try {
			Command command;
			command = FactoryCommand.build(p, EOperation.SAVE);
			Result result = command.execute();
			FacesContext ctx = FacesContext.getCurrentInstance();

			if (!StringUtils.isEmpty(result.getMsg())) {
				ctx.addMessage(null, new FacesMessage(result.getMsg(), result.getMsg()));
			} else {
				ctx.addMessage(null, new FacesMessage("Produto cadastrado com código: " + p.getCode()));
				Flash flash = ctx.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				;
				flash.setRedirect(true);
				Redirector.redirectTo(ctx.getExternalContext(), "/admin/productSearch.jsf?faces-redirect=true");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		Product p = prepareProduct();
		p.setId(product.getId());
		if (stock != null) {
			p.setStock(product.getStock() + stock);
		} else {
			p.setStock(product.getStock());
		}

		p.setInsertDate(product.getInsertDate());
		p.setWeight(product.getWeight());
		p.setUpdateDate(new Date());
		p.setCode(product.getCode());

		try {
			Command command;
			command = FactoryCommand.build(p, EOperation.UPDATE);
			Result result = command.execute();
			FacesContext ctx = FacesContext.getCurrentInstance();

			if (!StringUtils.isEmpty(result.getMsg())) {
				ctx.addMessage(null, new FacesMessage(result.getMsg(), result.getMsg()));
			} else {
				ctx.addMessage(null, new FacesMessage("Produto alterado"));
				Flash flash = ctx.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				;
				flash.setRedirect(true);
				Redirector.redirectTo(ctx.getExternalContext(), "/admin/productSearch.jsf?faces-redirect=true");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (product != null && !product.isEmpty()) {
			try {
				Command command;
				command = FactoryCommand.build(product, EOperation.DELETE);
				Result result = command.execute();

				if (!StringUtils.isEmpty(result.getMsg())) {
					ctx.addMessage(null, new FacesMessage("Erro ao excluir produto", result.getMsg()));
				} else {
					ctx.addMessage(null, new FacesMessage("Produto excluído"));
					super.select(null);
				}
				product = null;
				listProducts();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			ctx.addMessage(null, new FacesMessage("Selecione um produto para excluir"));
		}
	}

	public void showProductDetails() {
		if (product != null && !product.isEmpty()) {
			RequestContext ctx = RequestContext.getCurrentInstance();
			ctx.execute("PF('prodDialog').show()");
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Selecione um produto para ver detalhes"));
		}
	}

	/**
	 * Prepara produto para enviar para fachada
	 * 
	 * @return
	 */
	private Product prepareProduct() {
		Product p = new Product();
		p.setName(name);
		p.setDescription(description);
		p.setImage(image);
		p.setPrice(price);
		p.setStock(stock);
		p.setWeight(weight);
		p.setTagList(tagList);
		p.setInsertDate(new Date());
		StoreCategory st = new StoreCategory();
		if (!StringUtils.isEmpty(category)) {
			st.setDescription(category);
		}

		Subcategory sc = new Subcategory();
		if (!StringUtils.isEmpty(subcategory)) {
			sc.setDescription(subcategory);
		}
		sc.setStoreCategory(st);
		p.setStoreCategory(st);
		p.setSubcategory(sc);
		p.setUpdateDate(new Date());
		return p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
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

	public List<StoreCategory> getCategoryList() {
		return categoryList;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImagePath() {
		if (image != null) {
			ImagePath = SaveDirectory.REQUEST_IMG_DIR + image;
		} else {
			ImagePath = "default.jpg";
		}
		return ImagePath;
	}

	public String getImagePath(Product product) {
		String path;
		if (product != null) {
			path = SaveDirectory.REQUEST_IMG_DIR + product.getImage();
		} else {
			path = "default.jpg";
		}
		return path;
	}

	public String getProductImagePath() {
		if (StringUtils.isEmpty(image) && product != null && product.getImage() != null) {
			image = product.getImage();
		}
		return getImagePath();
	}

	public void clearFields() {
		product = null;
		product = new Product();
		name = null;
		category = null;
		subcategory = null;
		price = null;
		stock = null;
		image = null;
		description = null;
		tagList = null;
		weight = null;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMinStock() {
		Integer oldStock = null;
		if (product != null && product.getStock() != null) {
			oldStock = product.getStock() * -1;
		}
		return oldStock;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		if (product != null) {
			this.product = product;
			setImage(product.getImage());
		}
	}

	public void clearTableResults() {
		product = null;
		productList = null;
		super.unSelect(null);
	}

	public void clearFilter() {
		name = null;
		category = null;
		status = null;
		subcategory = null;
		status = 0;
		tagList = null;
		listProducts();
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<OrderByType> getOrderTypeList() {
		return orderTypeList;
	}

	public List<Subcategory> getSubcategoryList() {
		return subcategoryList;
	}

	public ProductFilter getFilter() {
		return filter;
	}

	public void setFilter(ProductFilter filter) {
		this.filter = filter;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
