package com.stock.eason.bean;

import java.io.Serializable;

public class StockPricedetailsExcel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String companyCode;
	private String currentPrice;
	private String dateoftheStockPrice;
	private String stockPriceatthisSpecifictime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getDateoftheStockPrice() {
		return dateoftheStockPrice;
	}
	public void setDateoftheStockPrice(String dateoftheStockPrice) {
		this.dateoftheStockPrice = dateoftheStockPrice;
	}
	public String getStockPriceatthisSpecifictime() {
		return stockPriceatthisSpecifictime;
	}
	public void setStockPriceatthisSpecifictime(String stockPriceatthisSpecifictime) {
		this.stockPriceatthisSpecifictime = stockPriceatthisSpecifictime;
	}
	

	
}
