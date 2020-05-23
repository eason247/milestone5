package com.stock.eason.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class StockExchange implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
	private Integer id;
    @Column(name="name")
	private String name;
    @Column(name="brief")
	private String brief;
    @Column(name="contactAddres")
	private String contactAddres;
    @Column(name="remarks")
	private String remarks;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getContactAddres() {
		return contactAddres;
	}
	public void setContactAddres(String contactAddres) {
		this.contactAddres = contactAddres;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
