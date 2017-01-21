package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Customer;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by marcusgao on 17/1/19.
 */
@Repository
public class CustomerDaoImpl extends AbstractDao<Long, Customer>
        implements CustomerDao {
    public void saveCustomer(Customer customer) {
        persist(customer);
    }

    public Customer findCustomerByUserName(String userName) {
        Query query = getSession().createQuery(
                "select c from Customer c where c.userName = :name")
                .setParameter("name", userName);
        return (Customer) query.uniqueResult();
    }


}
