package com.stock.eason.bean;

import java.io.Serializable;

public class IPOsPlanned implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8310699300367555909L;
	private Integer id;
	private String companyName;
	private String stockExchange;
	private String pricepershare;
	private String totalnumberofShares;
	private String openDateTime;
	private String remarks;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStockExchange() {
		return stockExchange;
	}
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	public String getPricepershare() {
		return pricepershare;
	}
	public void setPricepershare(String pricepershare) {
		this.pricepershare = pricepershare;
	}
	public String getTotalnumberofShares() {
		return totalnumberofShares;
	}
	public void setTotalnumberofShares(String totalnumberofShares) {
		this.totalnumberofShares = totalnumberofShares;
	}
	public String getOpenDateTime() {
		return openDateTime;
	}
	public void setOpenDateTime(String openDateTime) {
		this.openDateTime = openDateTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
