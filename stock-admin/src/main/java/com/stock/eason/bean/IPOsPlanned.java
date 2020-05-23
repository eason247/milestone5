package com.stock.eason.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class IPOsPlanned implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8310699300367555909L;
    @Id
    @Column(name="id")
	private Integer id;
    @Column(name="companyName")
	private String companyName;
    @Column(name="stockExchange")
	private String stockExchange;
    @Column(name="pricepershare")
	private String pricepershare;
    @Column(name="totalnumberofShares")
	private String totalnumberofShares;
    @Column(name="openDateTime")
	private String openDateTime;
    @Column(name="remarks")
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
