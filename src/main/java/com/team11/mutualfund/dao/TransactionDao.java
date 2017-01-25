package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Transaction;
import com.team11.mutualfund.utils.TransactionType;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team11.mutualfund.utils.TransactionType.BUYFUND;

@Repository
public class TransactionDao extends AbstractDao<Long, Transaction> {
    public void saveTransaction(Transaction transaction) {
        persist(transaction);
    }

    @SuppressWarnings("unchecked")
    public List<Transaction> listPendingTransactionByCustomerIdType(long cid, TransactionType type) {
        Query query = getSession().createQuery(
                "select t from Transaction t where " +
                        "t.executeDate is null and " +
                        "t.customer.id = :cid and " +
                        "t.type = :type"
        )
                .setParameter("cid", cid)
                .setParameter("type", type);
        return (List<Transaction>) query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Transaction> listPendingTransactionByCustomerId(long cid) {
        Query query = getSession().createQuery(
                "select t from Transaction t where " +
                        "t.executeDate is null and " +
                        "t.customer.id = :cid"
        )
                .setParameter("cid", cid);
        return (List<Transaction>) query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Transaction> listPendingBuyFundByFundTicker(String ticker) {
        Query query = getSession().createQuery(
                "select t from Transaction t where " +
                        "t.executeDate is null and " +
                        "t.fund.ticker = :ticker and " +
                        "t.type = :type"
        )
                .setParameter("ticker", ticker)
                .setParameter("type", BUYFUND);
        return (List<Transaction>) query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Transaction> listFinishTransactionByCustomerId(long cid) {
        Query query = getSession().createQuery(
                "select t from Transaction t where " +
                        "t.executeDate is not null and " +
                        "t.customer.id = :cid"
        )
                .setParameter("cid", cid);
        return (List<Transaction>) query.list();
    }
}
