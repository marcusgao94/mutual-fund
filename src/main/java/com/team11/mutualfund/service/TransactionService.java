package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.*;
import com.team11.mutualfund.model.*;
import com.team11.mutualfund.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.team11.mutualfund.utils.Constant.*;
import static com.team11.mutualfund.utils.TransactionType.*;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private FundDao fundDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private FundPriceHistoryDao fundPriceHistoryDao;

    public List<Transaction> listPendingTransactionByCustomerId(long cid) {
        return transactionDao.listPendingTransactionByCustomerId(cid);
    }

    public List<Transaction> listFinishTransactionByCustomerId(long cid) {
        return transactionDao.listFinishTransactionByCustomerId(cid);
    }

    public void buyFund(long cid, String ticker, double amount) throws RollbackException {
        Customer customer = customerDao.findById(cid);
        Fund fund = fundDao.findByTicker(ticker);
        if (customer == null)
            throw new RollbackException("customer id " + String.valueOf(cid) + " does not exist");
        if (fund == null)
            throw new RollbackException("fund ticker " + String.valueOf(ticker) + " does not exist");
        if (customer.getCash() < customer.getPendingCashDecrease() + amount)
            throw new RollbackException(NOENOUGHCASH);

        customer.setPendingCashDecrease(customer.getPendingCashDecrease() + amount);
        transactionDao.saveTransaction(new Transaction(customer, fund, BUYFUND, null, amount));
    }

    public void sellFund(long cid, String ticker, double shares) throws RollbackException {
        Customer customer = customerDao.findById(cid);
        Fund fund = fundDao.findByTicker(ticker);
        if (customer == null)
            throw new RollbackException("customer id " + String.valueOf(cid) + " does not exist");
        if (fund == null)
            throw new RollbackException("fund ticker " + String.valueOf(ticker) + " does not exist");
        Position position = positionDao.findByCustomerIdFundId(cid, fund.getId());
        if (position == null)
            throw new RollbackException("customer does not have fund " +
                    String.valueOf(fund.getTicker()));
        if (position.getShares() < position.getPendingShareDecrease() + shares)
            throw new RollbackException(NOENOUGHSHARE);
        /*
        // get other pending sell others
        List<Transaction> pendingSellFund = transactionDao.listPendingTransactionByCustomerIdType(
                customer.getId(), SELLFUND);
        double pendingShare = 0;
        for (Transaction t : pendingSellFund) {
            pendingShare += t.getShares();
        }
        */
        position.setPendingShareDecrease(position.getPendingShareDecrease() + shares);
        transactionDao.saveTransaction(new Transaction(customer, fund, SELLFUND, shares, null));
    }

    public void requestCheck(String userName, double amount) throws RollbackException {
        Customer customer = customerDao.findByUserName(userName);
        if (customer == null)
            throw new RollbackException("customer username " + String.valueOf(userName) + " does not exist");

        // check enough cash
        if (customer.getCash() < customer.getPendingCashDecrease() + amount)
            throw new RollbackException(NOENOUGHCASH);

        customer.setPendingCashDecrease(customer.getPendingCashDecrease() + amount);
        transactionDao.saveTransaction(new Transaction(customer, null, REQUESTCHECK, null, amount));
    }

    public void depositCheck(long cid, double amount) throws RollbackException {
        Customer customer = customerDao.findById(cid);
        if (customer == null)
            throw new RollbackException("customer id " + String.valueOf(cid) + " does not exist");
        transactionDao.saveTransaction(new Transaction(customer, null, DEPOSITCHECK, null, amount));
    }









}
