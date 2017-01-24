package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.utils.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // transaction for service layer
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	public boolean createCustomer(Customer customer) {
		if (customerDao.getCustomerByUserName(customer.getUserName()) != null) {
			return false;
		}
		customerDao.saveCustomer(customer);
		return true;
	}

	public Customer findCustomerByUserName(String userName) {
		return customerDao.getCustomerByUserName(userName);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateCustomer(Customer customer) {
		Customer entity = customerDao.getCustomerByUserName(customer.getUserName());
		if(entity!=null){
			entity.setFirstName("edit success");
		}
	}
	/**
	 * Used to tell whether the customer exist and if yes, update the password
	 * @param c
	 * @param confirmPassword
	 * @return
	 */
	public Customer updateCustomerPassword(Long id, String confirmPassword) {
		Customer c = findCustomerbyId(id);

		customerDao.updatePassword(c, confirmPassword);
		return c;
	}
	
	public boolean updatePassword(Customer c, String confirmPassword) {
		customerDao.updatePassword(c, confirmPassword);
		return true;
	}
	
	public Customer findCustomerbyId(long id) {
		return customerDao.getCustomerById(id);
	}
	
	public boolean checkCustomerbyId(long id) {
		
		Customer c = customerDao.getCustomerById(id);
		
		if (c == null) {
			return false;
		}
		
		return true;
	}

	public boolean matchPassword(Customer c, String oldPassword) {
		
		if (!c.getPassword().equals(oldPassword)) {
			return false;
		}
		
		return true;
	}
	
}
