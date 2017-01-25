package com.team11.mutualfund.service;


import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.utils.User;

import static com.team11.mutualfund.utils.Constant.WRONGPASSWORD;

import javax.transaction.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.model.Customer;
import static com.team11.mutualfund.utils.Constant.*;

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

	public Customer getCustomerById(long id) {
		return customerDao.getCustomerById(id);
	}

	public Customer getCustomerByUserName(String userName) {
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
	 * @param id
	 * @param confirmPassword
	 * @return
	 */
	public Customer updateCustomerPassword(Long cid, String confirmPassword) throws RollbackException{
		
		
		Customer c =  customerDao.getCustomerById(cid);
		
		if (!c.getPassword().equals(confirmPassword)) {
			throw new RollbackException(WRONGPASSWORD);
		}
		
		
		c.setPassword(confirmPassword);
		return c;
	}
	
	public void updatePassword(Long cid, String confirmPassword) throws RollbackException {
		
		Customer c =  customerDao.getCustomerById(cid);
		
		if (!c.getPassword().equals(confirmPassword)) {
			throw new RollbackException(WRONGPASSWORD);
		}
		
		customerDao.updatePassword(c, confirmPassword);
	
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

}
