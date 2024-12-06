package com.rays.pro4.Bean;

import java.util.Date;

public class StockBean extends BaseBean {

	private int quantity;
	private long purchasePrice;
	private Date purchaseDate;
	private String orderType;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(long purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Override
	public String getValue() {
		// Return a meaningful description, if needed
		return null;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return null;
	}
}
