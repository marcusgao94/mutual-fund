package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Employee;

/**
 * Created by marcusgao on 17/1/19.
 */
public interface CustomerDao {

    void saveCustomer(Customer customer);

    Customer findCustomerByUserName(String userName);
}
