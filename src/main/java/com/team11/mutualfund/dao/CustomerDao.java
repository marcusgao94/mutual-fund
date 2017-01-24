package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Customer;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao extends AbstractDao<Long, Customer> {

    public void saveCustomer(Customer customer) {
        persist(customer);
    }

    public Customer getCustomerById(Long customerId) {
        return getByKey(customerId);
    }

    public Customer getCustomerByUserName(String userName) {
        Query query = getSession().createQuery(
                "select c from Customer c where c.userName = :name")
                .setParameter("name", userName);
        return (Customer) query.uniqueResult();
    }
    
    public Customer updatePassword(Customer c, String confirmPassword){
		return c;
    	
    }

}
