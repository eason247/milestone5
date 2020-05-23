package com.stock.eason.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;
@Entity
public class StockPricedetailsExcel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
	private Integer id;
    @Column(name="companyCode")
	private String companyCode;
    @Column(name="currentPrice")
	private String currentPrice;
    @Column(name="dateoftheStockPrice")
	private String dateoftheStockPrice;
    @Column(name="stockPriceatthisSpecifictime")
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
