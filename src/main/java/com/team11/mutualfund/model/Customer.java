package com.team11.mutualfund.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;

	private String addr_line1;

	private String addr_line2;

	private String city;

	private String state;

	private int zip;

	private double cash;

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

}
