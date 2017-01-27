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
		if (customerDao.findByUserName(customer.getUserName()) != null) {
			return false;
		}
		customerDao.saveCustomer(customer);
		return true;
	}

	public Customer getCustomerById(long id) {
		return customerDao.findById(id);
	}

	public Customer getCustomerByUserName(String userName) {
		return customerDao.findByUserName(userName);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends.
	 */
	public void updateCustomer(Customer customer) {
		Customer entity = customerDao.findByUserName(customer.getUserName());
		if(entity!=null){
			entity.setFirstName("edit success");
		}
	}

	public Customer updatePassword(Long cid, String originPassword, String newPassword)
			throws RollbackException {
		Customer c =  customerDao.findById(cid);
		if (c == null)
			throw new javax.persistence.RollbackException(NOCUSTOMER);
		if (!c.getPassword().equals(originPassword))
			throw new RollbackException(WRONGPASSWORD);
		c.setPassword(newPassword);
		return c;
	}
	
	
	public Customer updatePassword(Long cid, String newPassword)
			throws RollbackException {
		Customer c =  customerDao.findById(cid);
		if (c == null)
			throw new RollbackException(NOCUSTOMER);
		
		c.setPassword(newPassword);
		return c;
	}
	
	public Customer findCustomerbyId(long id) {
		return customerDao.findById(id);
	}
	
	public boolean checkCustomerbyId(long id) {
		
		Customer c = customerDao.findById(id);
		
		if (c == null) {
			return false;
		}
		
		return true;
	}

}
