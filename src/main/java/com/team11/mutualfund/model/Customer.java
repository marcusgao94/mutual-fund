package com.team11.mutualfund.model;

import com.team11.mutualfund.form.CustomerRegisterForm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import java.io.Serializable;

@Entity
public class Customer implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column(nullable = false)
	private String password;

	private String firstName;
	
	private String lastName;

	private String addr_line1;

	private String addr_line2;

	private String city;

	private String state;

	@Column(nullable=true)
	private Integer zip;

	@Column(nullable=false, scale = 2)
	private Double cash = 0d;

	@Column(scale = 2)
	private Double pendingCashDecrease = 0d;

	public Customer() {}
	public Customer(CustomerRegisterForm cf) {
		setUserName(cf.getUserName());
		setPassword(cf.getPassword());
		setFirstName(cf.getFirstName());
		setLastName(cf.getLastName());
		setAddr_line1(cf.getAddr_line1());
		setAddr_line2(cf.getAddr_line2());
		setCity(cf.getCity());
		setState(cf.getState());
		setZip(cf.getZip());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddr_line1() {
		return addr_line1;
	}

	public void setAddr_line1(String addr_line1) {
		this.addr_line1 = addr_line1;
	}

	public String getAddr_line2() {
		return addr_line2;
	}

	public void setAddr_line2(String addr_line2) {
		this.addr_line2 = addr_line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getPendingCashDecrease() {
		return pendingCashDecrease;
	}

	public void setPendingCashDecrease(Double pendingCashDecrease) {
		this.pendingCashDecrease = pendingCashDecrease;
	}
}
