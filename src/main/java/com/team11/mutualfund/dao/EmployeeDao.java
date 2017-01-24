package com.team11.mutualfund.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.team11.mutualfund.model.Employee;

@Repository
public class EmployeeDao extends AbstractDao<Integer, Employee> {

	public void saveEmployee(Employee employee) {
		persist(employee);
	}

	public Employee findEmployeeByUserName(String userName) {
		Query query = getSession().createQuery(
				"select e from Employee e where e.userName = :name")
				.setParameter("name", userName);
		return (Employee) query.uniqueResult();
	}

}
