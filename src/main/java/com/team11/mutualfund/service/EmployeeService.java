package com.team11.mutualfund.service;

import java.util.ArrayList;
import java.util.List;

import com.team11.mutualfund.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team11.mutualfund.dao.EmployeeDao;
import com.team11.mutualfund.model.Employee;

@Service
@Transactional // transaction for service layer
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	public Pair<Boolean, String> createEmployee(Employee employee) {
		Pair<Boolean, String> pair = new Pair();
		if (employeeDao.findEmployeeByUserName(employee.getUserName()) != null) {
			pair.setLeft(false);
			pair.setRight("user name already exists");
		}
		else {
			employeeDao.saveEmployee(employee);
			pair.setLeft(true);
			pair.setRight("");
		}
		return pair;
	}

	public Employee findEmployeeByUserName(String userName) {
		return employeeDao.findEmployeeByUserName(userName);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateEmployee(Employee employee) {
		Employee entity = employeeDao.findEmployeeByUserName(employee.getUserName());
		if(entity!=null){
			entity.setFirstName("edit success");
		}
	}


	public List<Employee> findAllEmployees() {
		return employeeDao.findAllEmployees();
	}

}
