package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Transaction;
import com.team11.mutualfund.utils.TransactionType;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDao extends AbstractDao<Long, Transaction> {
    public void saveTransaction(Transaction transaction) {
        persist(transaction);
    }

    @SuppressWarnings("unchecked")
    public List<Transaction> listPendingTransactionByCustomerIdType(long cid, TransactionType type) {
        Query query = getSession().createQuery(
                "select t from Transaction t where " +
                        "t.customer.id = :cid and " +
                        "t.executeDate is null and " +
                        "t.type = :type"
        )
                .setParameter("cid", cid)
                .setParameter("type", type);
        return (List<Transaction>) query.list();
    }
}
