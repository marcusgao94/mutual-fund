package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.model.Customer;
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

	public Customer updatePassword(Customer e, String confirmPassword) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
