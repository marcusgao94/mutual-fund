package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.dao.FundDao;
import com.team11.mutualfund.dao.PositionDao;
import com.team11.mutualfund.dao.TransactionDao;
import com.team11.mutualfund.model.Customer;
import com.team11.mutualfund.model.Fund;
import com.team11.mutualfund.model.Position;
import com.team11.mutualfund.model.Transaction;
import com.team11.mutualfund.utils.Pair;
import javafx.scene.AmbientLight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.team11.mutualfund.utils.Constant.NOENOUGHCASH;
import static com.team11.mutualfund.utils.Constant.NOENOUGHSHARE;
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

    /*
    public double getPendingCashDecreaseByCustomerId(long cid) {
        List<Transaction> pendingBuyFund = transactionDao.listPendingTransactionByCustomerIdType(cid, BUYFUND);
        double pendingCash = 0;
        for (Transaction t : pendingBuyFund) {
            pendingCash += t.getAmount();
        }
        // get other check requests
        List<Transaction> pendingRequestCheck = transactionDao.listPendingTransactionByCustomerIdType(cid, REQUESTCHECK);
        double pendingCheck = 0;
        for (Transaction t : pendingRequestCheck) {
            pendingCheck += t.getAmount();
        }
        return pendingCash + pendingCheck;
    }
    */

    public void buyFund(long cid, String ticker, double amount) throws RollbackException {
        Customer customer = customerDao.getCustomerById(cid);
        Fund fund = fundDao.getFundByTicker(ticker);
        if (customer == null)
            throw new RollbackException("customer id " + String.valueOf(cid) + " does not exist");
        if (fund == null)
            throw new RollbackException("fund ticker " + String.valueOf(ticker) + " does not exist");
        if (customer.getCash() < customer.getPendingCashDecrease() + amount)
            throw new RollbackException(NOENOUGHCASH);

        // need to check this sentence
        customer.setPendingCashDecrease(customer.getPendingCashDecrease() + amount);
        transactionDao.saveTransaction(new Transaction(customer, fund, BUYFUND, null, amount));
    }

    public void sellFund(long cid, String ticker, double shares) throws RollbackException {
        Customer customer = customerDao.getCustomerById(cid);
        Fund fund = fundDao.getFundByTicker(ticker);
        if (customer == null)
            throw new RollbackException("customer id " + String.valueOf(cid) + " does not exist");
        if (fund == null)
            throw new RollbackException("fund ticker " + String.valueOf(ticker) + " does not exist");
        Position position = positionDao.getPositionByCustomerIdFundId(cid, fund.getId());
        if (position == null)
            throw new RollbackException("customer does not have fund " +
                    String.valueOf(fund.getTicker()));
        if (position.getShare() < position.getPendingShareDecrease() + shares)
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

    public void requestCheck(long cid, double amount) throws RollbackException {
        Customer customer = customerDao.getCustomerById(cid);
        if (customer == null)
            throw new RollbackException("customer id " + String.valueOf(cid) + " does not exist");

        // check enough cash
        if (customer.getCash() < customer.getPendingCashDecrease() + amount)
            throw new RollbackException(NOENOUGHCASH);

        customer.setPendingCashDecrease(customer.getPendingCashDecrease() + amount);
        transactionDao.saveTransaction(new Transaction(customer, null, REQUESTCHECK, null, amount));
    }

    public void depositCheck(long cid, double amount) throws RollbackException {
        Customer customer = customerDao.getCustomerById(cid);
        if (customer == null)
            throw new RollbackException("customer id " + String.valueOf(cid) + " does not exist");
        transactionDao.saveTransaction(new Transaction(customer, null, DEPOSITCHECK, null, amount));
    }

    public List<Transaction> listPendingTransactionByCustomerId(long cid) {
        return transactionDao.listPendingTransactionByCustomerId(cid);
    }

    public List<Transaction> listFinishTransactionByCustomerId(long cid) {
        return transactionDao.listFinishTransactionByCustomerId(cid);
    }

    public void executeDepositCheck(long cid, LocalDate date) {
        List<Transaction> transactionList = transactionDao.listPendingTransactionByCustomerIdType(cid, DEPOSITCHECK);
        for (Transaction t : transactionList) {
            t.setExectuteDate(date);
            Customer customer = t.getCustomer();
            customer.setCash(customer.getCash() + t.getAmount());
        }
    }

    public void executeRequestCheck(long cid, LocalDate date) {
        Customer customer = customerDao.getCustomerById(cid);
        List<Transaction> transactionList = transactionDao.listPendingTransactionByCustomerIdType(cid, REQUESTCHECK);
        for (Transaction t : transactionList) {
            t.setExectuteDate(date);
            customer.setCash(customer.getCash() - t.getAmount());
        }
        customer.setPendingCashDecrease(0);
    }
}
