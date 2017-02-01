package com.team11.mutualfund.service;


import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.utils.User;

import static com.team11.mutualfund.utils.Constant.WRONGPASSWORD;

import javax.transaction.RollbackException;

import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.model.Customer;
import static com.team11.mutualfund.utils.Constant.*;

@Service
@Transactional
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

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public Customer updatePassword(Long cid, String originPassword, String newPassword)
			throws RollbackException {
		Customer c =  customerDao.findById(cid);
		if (c == null)
			throw new RollbackException(NOCUSTOMER);
		if (!c.getPassword().equals(originPassword))
			throw new RollbackException(WRONGPASSWORD);
		c.setPassword(newPassword);
		return c;
	}

	public Customer updatePassword(Long cid, String newPassword) throws RollbackException {
		Customer c =  customerDao.findById(cid);
		if (c == null)
			throw new RollbackException(NOCUSTOMER);
		
		c.setPassword(newPassword);
		return c;
	}

	public Customer updatePassword(String userName, String newPassword) throws RollbackException {
		Customer c =  customerDao.findByUserName(userName);
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
