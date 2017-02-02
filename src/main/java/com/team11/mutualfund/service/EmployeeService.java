package com.team11.mutualfund.service;

import java.util.List;

import javax.transaction.RollbackException;

import com.team11.mutualfund.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team11.mutualfund.model.Employee;
import static com.team11.mutualfund.utils.Constant.*;

@Service
@Transactional // transaction for service layer
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	public boolean createEmployee(Employee employee) {
		if (employeeDao.findByUserName(employee.getUserName()) != null) {
			return false;
		}
		employeeDao.saveEmployee(employee);
		return true;
	}

	public Employee getEmployeeByUserName(String userName) {
		return employeeDao.findByUserName(userName);
	}
	
	public List<Employee> getEmployeeList() {
		return employeeDao.getEmployeeList();
	}
	
	
	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateEmployee(Employee employee) {
		Employee entity = employeeDao.findByUserName(employee.getUserName());
		if(entity!=null){
			entity.setFirstName("edit success");
		}
	}

	public Boolean updatePassword(String name, String originPassword,
								  String newPassword) throws RollbackException {
		Employee e = employeeDao.findByUserName(name);
		if (!e.getPassword().equals(originPassword))
			throw new RollbackException(WRONGPASSWORD);
		e.setPassword(newPassword);
		return true;
	}
}
