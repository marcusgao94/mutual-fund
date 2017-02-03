package com.team11.mutualfund.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import com.team11.mutualfund.model.Employee;

import javax.persistence.LockModeType;

@Repository
public class EmployeeDao extends AbstractDao<Integer, Employee> {

	public void saveEmployee(Employee employee) {
		persist(employee);
	}

	public Employee findByUserName(String userName) {
		Query query = getSession().createQuery(
				"select e from Employee e where e.userName = :name")
				.setParameter("name", userName);
		return (Employee) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Employee findByUserNameForUpdate(String userName) {
		Query query = getSession().createQuery(
				"select e from Employee e where e.userName = :name")
				.setParameter("name", userName)
				.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		return (Employee) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Employee> getEmployeeList() {
		Query query = getSession().createQuery(
                "select c from Employee c"
        );
        return (List<Employee>) query.list();
	}

}
