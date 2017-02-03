package com.team11.mutualfund.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import com.team11.mutualfund.model.Employee;

import javax.persistence.LockModeType;

@Repository
public class EmployeeDao extends AbstractDao<Integer, Employee> {

	public void saveEmployee(Employee employee) {
		persist(employee);
	}

	@SuppressWarnings("deprecation")
	public Employee findByUserName(String userName) {
		@SuppressWarnings("rawtypes")
		Query query = getSession().createQuery(
				"select e from Employee e where e.userName = :name")
				.setParameter("name", userName);
		return (Employee) query.uniqueResult();
	}

	@SuppressWarnings("deprecation")
	public Employee findByUserNameForUpdate(String userName) {
		@SuppressWarnings("rawtypes")
		Query query = getSession().createQuery(
				"select e from Employee e where e.userName = :name")
				.setParameter("name", userName)
				.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		return (Employee) query.uniqueResult();
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Employee> getEmployeeList() {
		@SuppressWarnings("rawtypes")
		Query query = getSession().createQuery(
                "select c from Employee c"
        );
        return (List<Employee>) query.list();
	}

}
