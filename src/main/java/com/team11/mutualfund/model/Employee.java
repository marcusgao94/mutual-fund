package com.team11.mutualfund.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.team11.mutualfund.form.EmployeeRegisterForm;

@Entity
public class Employee implements Serializable {

	@Id
	private String userName;

	@Column(nullable = false)
	private String password;

	private String firstName;
	
	private String lastName;

	public Employee() {}

	public Employee(EmployeeRegisterForm ef) {
		setUserName(ef.getUserName());
		setPassword(ef.getPassword());
		setFirstName(ef.getFirstName());
		setLastName(ef.getLastName());
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

}
