package com.team11.mutualfund.service;

import java.util.List;

import javax.transaction.RollbackException;

import com.team11.mutualfund.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.team11.mutualfund.model.Employee;
import static com.team11.mutualfund.utils.Constant.*;

@Service
@Transactional // transaction for service layer
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	// @Transactional(isolation = Isolation.SERIALIZABLE)
	public void createEmployee(Employee employee) throws DataIntegrityViolationException {
		employeeDao.saveEmployee(employee);
	}

	public Employee getEmployeeByUserName(String userName) {
		return employeeDao.findByUserName(userName);
	}

	public List<Employee> getEmployeeList() {
		return employeeDao.getEmployeeList();
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Boolean updatePassword(String name, String originPassword,
								  String newPassword) throws RollbackException {
		Employee e = employeeDao.findByUserNameForUpdate(name);
		if (!e.getPassword().equals(originPassword))
			throw new RollbackException(WRONGPASSWORD);
		e.setPassword(newPassword);
		return true;
	}
}
