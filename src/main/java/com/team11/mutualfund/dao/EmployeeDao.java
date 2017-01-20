package com.team11.mutualfund.dao;

import java.util.List;

import com.team11.mutualfund.model.*;

public interface EmployeeDao {

	void saveEmployee(Employee employee);

	Employee findEmployeeByUserName(String userName);
	
	List<Employee> findAllEmployees();

}
