package com.team11.mutualfund.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Employee;

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
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Employee> getEmployeeList() {
		@SuppressWarnings("rawtypes")
		Query query = getSession().createQuery(
                "select c from Customer c"
        );
        return (List<Employee>) query.list();
	}

}
