package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Customer;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao extends AbstractDao<Long, Customer> {

    public void saveCustomer(Customer customer) {
        persist(customer);
    }

    public Customer findById(Long customerId) {
        return getByKey(customerId);
    }

    public Customer findByUserName(String userName) {
        Query query = getSession().createQuery(
                "select c from Customer c where c.userName = :name"
        )
                .setParameter("name", userName);
        return (Customer) query.uniqueResult();
    }
    
    public Customer updatePassword(Customer c, String confirmPassword){
		return c;
    	
    }

    /*
    public void decreaseCash(long cid, double amount) {
        Query query = getSession().createQuery(
                "update Customer c " +
                        "set c.cash = c.cash - :amount " +
                        "where c.id = :cid"
        )
                .setParameter("amount", amount)
                .setParameter("cid", cid);
    }
    */

}
