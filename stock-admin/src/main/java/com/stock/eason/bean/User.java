package com.stock.eason.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
	private Integer id;
    @Column(name="username")
	private String username;
    @Column(name="password")
	private String password;
    @Column(name="userType")
	private String userType;
    @Column(name="email")
	private String email;
    @Column(name="cellphone")
	private String cellphone;
    @Column(name="confirmed")
	private String confirmed;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
