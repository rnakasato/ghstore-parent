package com.nakasato.ghstore.web.mb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.filter.impl.CustomerFilter;
import com.nakasato.ghstore.core.payment.utils.PaymentUtil;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.core.util.Parser;
import com.nakasato.ghstore.domain.Address;
import com.nakasato.ghstore.domain.Customer;
import com.nakasato.ghstore.domain.Order;
import com.nakasato.ghstore.domain.Phone;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.ShoppingCart;
import com.nakasato.ghstore.domain.ShoppingCartItem;
import com.nakasato.ghstore.domain.User;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.web.util.Redirector;

@ManagedBean(name = "userSessionMB")
@SessionScoped
public class UserSessionMB extends BaseMB {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Customer loggedUser;
	private ShoppingCart cart;
	private ShoppingCartItem selectedItem;
	private String oldPage;
	private String newPage;

	@PostConstruct
	public void init() {
		// Temporário para testes
		if (loggedUser == null) {
			try{
				CustomerFilter f = new CustomerFilter();
				ICommand commandFindAll = FactoryCommand.build(f, EOperation.FINDALL);
				Result result = commandFindAll.execute();
				loggedUser  = (Customer)result.getEntityList().get(0);
				
				Phone phone = new Phone();
				phone.setDdd(11);
				phone.setNumber(47221177);
				
				List<Phone> phoneList = new ArrayList<>();
				phoneList.add(phone);
				loggedUser.setPhoneList(phoneList);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if (cart == null) {
			cart = new ShoppingCart();
			cart.setShoppingCartList(new ArrayList<>());
			cart.setTotalValue(0d);
			cart.setTotalWeight(0L);
			cart.setOwner(loggedUser);
		}
		Address address = loggedUser.getDeliveryAddressList().get(0);
		
		cart.setAddress(address);
	}

	public void finishPayment() {
		try{
			
			if (this.cart != null && !ListUtils.isListEmpty(cart.getShoppingCartList())) {
				String pagseguroPage = PaymentUtil.createPayment(cart, loggedUser);
				Order order = Parser.parseShoppingCartToOrder(cart);
				List<Order> orderList = loggedUser.getOrderList();
				if(orderList == null){
					orderList = new ArrayList<>();
				}
				orderList.add(order);
				loggedUser.setOrderList(orderList);
				ICommand commandUpdate = FactoryCommand.build(loggedUser, EOperation.UPDATE);
				commandUpdate.execute();
				
				
				cart = new ShoppingCart();
				cart.setShoppingCartList(new ArrayList<>());
				cart.setTotalValue(0d);
				cart.setTotalWeight(0L);
				cart.setOwner(loggedUser);
				
				Redirector.redirectToExternalPage(FacesContext.getCurrentInstance().getExternalContext(), pagseguroPage);
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não há itens no carrinho de compras"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void removeCartItem(Product product) {
		FacesMessage msg;

		List<ShoppingCartItem> itemList = cart.getShoppingCartList();

		boolean alreadyExists = false;
		int index = 0;
		for (ShoppingCartItem item : itemList) {
			if (item.getProduct().getId() == product.getId()) {
				alreadyExists = true;
				break;
			}
			index++;
		}
		if (alreadyExists) {
			ShoppingCartItem scItem = itemList.get(index);
			cart.getShoppingCartList().remove(scItem);
			msg = new FacesMessage("O produto não existe no carrinho de compras");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			msg = new FacesMessage("O produto foi removido do carrinho de compras");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void addCartItem(Product product, Integer amount) {
		FacesMessage msg;

		if (amount > 0) {
			List<ShoppingCartItem> itemList = cart.getShoppingCartList();
			boolean alreadyExists = false;
			int index = 0;
			for (ShoppingCartItem item : itemList) {
				if (item.getProduct().getId() == product.getId()) {
					alreadyExists = true;
					break;
				}
				index++;
			}

			if (alreadyExists) {
				ShoppingCartItem cartItem = itemList.get(index);

				// remove peso e valor do total do carrinho
				cart.setTotalValue(cart.getTotalValue() - cartItem.getTotalValue());
				cart.setTotalWeight(cart.getTotalWeight() - cartItem.getTotalWeigth());

				// carrega novos valores do item
				Integer totalAmount = cartItem.getAmount() + amount;
				cartItem.setAmount(totalAmount);
				cartItem.setTotalValue(totalAmount * cartItem.getProduct().getPrice());
				Long weight = cartItem.getProduct().getWeight().longValue();
				cartItem.setTotalWeigth(totalAmount * weight);

				cart.setTotalValue(cart.getTotalValue() + cartItem.getTotalValue());
				cart.setTotalWeight(cart.getTotalWeight() + cartItem.getTotalWeigth());
			} else {
				ShoppingCartItem cartItem = new ShoppingCartItem();
				cartItem.setAmount(amount);
				cartItem.setProduct(product);
				cartItem.setTotalValue(amount * product.getPrice());

				Long weight = product.getWeight().longValue();
				cartItem.setTotalWeigth(amount * weight);

				cart.addItem(cartItem);

				cart.setTotalValue(cart.getTotalValue() + cartItem.getTotalValue());

				cart.setTotalWeight(cart.getTotalWeight() + cartItem.getTotalWeigth());

			}
			StringBuilder message = new StringBuilder();
			message.append("Produto adicionado no carrinho de compras");
			msg = new FacesMessage("Produto adicionado ao carrinho");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			msg = new FacesMessage("Deve ser adicionado ao menos uma unidade do produto para adicionar ao carrinho");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public ShoppingCartItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ShoppingCartItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Customer getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Customer loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getOldPage() {
		return oldPage;
	}

	public void setOldPage(String oldPage) {
		this.oldPage = oldPage;
	}

	public String getNewPage() {
		return newPage;
	}

	public void setNewPage(String newPage) {
		this.newPage = newPage;
	}

}
