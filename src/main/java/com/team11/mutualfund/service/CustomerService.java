package com.team11.mutualfund.service;


import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.utils.User;

import static com.team11.mutualfund.utils.Constant.WRONGPASSWORD;

import java.util.List;

import javax.transaction.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

	public void createCustomer(Customer customer) throws DataIntegrityViolationException {
		customerDao.saveCustomer(customer);
	}

	public Customer getCustomerById(long id) {
		return customerDao.findById(id);
	}

	public Customer getCustomerByUserName(String userName) {
		return customerDao.findByUserName(userName);
	}

	public List<Customer> getCustomerList() {
		return customerDao.getCustomerList();
	}

    @Transactional(isolation = Isolation.SERIALIZABLE)
	public Customer updatePassword(Long cid, String originPassword, String newPassword)
			throws RollbackException {
		Customer c =  customerDao.findByIdForUpdate(cid);
		if (c == null)
			throw new RollbackException(NOCUSTOMER);
		if (!c.getPassword().equals(originPassword))
			throw new RollbackException(WRONGPASSWORD);
		c.setPassword(newPassword);
		return c;
	}

	public Customer updatePassword(String userName, String originPassword, String newPassword) throws RollbackException {
		Customer c =  customerDao.findByUserNameForUpdate(userName);
		if (c == null)
			throw new RollbackException(NOCUSTOMER);
		if (!c.getPassword().equals(originPassword))
			throw new RollbackException("Password has been reset by other employees");
		c.setPassword(newPassword);
		return c;
	}

}
