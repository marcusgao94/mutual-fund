package com.team11.mutualfund.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.query.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.team11.mutualfund.dao.*;
import com.team11.mutualfund.model.*;

@Repository
public class EmployeeDaoImpl extends AbstractDao<Integer, Employee> implements EmployeeDao {

	public void saveEmployee(Employee employee) {
		persist(employee);
	}

	public Employee findEmployeeByUserName(String userName) {
		Query query = getSession().createQuery(
				"select e from Employee e where e.userName = :name")
				.setParameter("name", userName);
		return (Employee) query.uniqueResult();
	}




	@SuppressWarnings("unchecked")
	public List<Employee> findAllEmployees() {
		Query query = getSession().createQuery(
				"from Employee "
		);
		return (List<Employee>) query.list();
	}
}
