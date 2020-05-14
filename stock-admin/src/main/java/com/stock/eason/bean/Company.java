package com.stock.eason.bean;

import java.io.Serializable;

public class Company implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private Integer companyId;

	private String name;
	private String turnover;
	private String ceo;
	private String boardOfDirectors;
	private String listedInStockExchanges;
	private String sector;
	private String description;
	private String stockCodeineachStockExchange;
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTurnover() {
		return turnover;
	}
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	public String getCeo() {
		return ceo;
	}
	public void setCeo(String ceo) {
		this.ceo = ceo;
	}
	public String getBoardOfDirectors() {
		return boardOfDirectors;
	}
	public void setBoardOfDirectors(String boardOfDirectors) {
		this.boardOfDirectors = boardOfDirectors;
	}
	public String getListedInStockExchanges() {
		return listedInStockExchanges;
	}
	public void setListedInStockExchanges(String listedInStockExchanges) {
		this.listedInStockExchanges = listedInStockExchanges;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStockCodeineachStockExchange() {
		return stockCodeineachStockExchange;
	}
	public void setStockCodeineachStockExchange(String stockCodeineachStockExchange) {
		this.stockCodeineachStockExchange = stockCodeineachStockExchange;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
