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

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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

    public Pair buyFund(long cid, long fid, double amount) {
        Customer customer = customerDao.getCustomerById(cid);
        Fund fund = fundDao.getFundById(fid);
        if (customer == null)
            return new Pair(false, "customer id " + String.valueOf(cid) + " does not exist");
        if (fund == null)
            return new Pair(false, "fund id " + String.valueOf(fid) + " does not exist");
        if (customer.getCash() < customer.getPendingCashDecrease() + amount)
            return new Pair(false, "no enough cash");

        // need to check this sentence
        customer.setPendingCashDecrease(customer.getPendingCashDecrease() + amount);
        transactionDao.saveTransaction(new Transaction(customer, fund, BUYFUND, null, amount));
        return new Pair(true, "success");
    }

    public Pair sellFund(long cid, long fid, double shares) {
        Customer customer = customerDao.getCustomerById(cid);
        Fund fund = fundDao.getFundById(fid);
        Position position = positionDao.getPositionByCustomerIdFundId(cid, fid);
        if (customer == null)
            return new Pair(false, "customer id " +
                    String.valueOf(cid) + " does not exist");
        if (fund == null)
            return new Pair(false, "fund id " +
                    String.valueOf(fid) + " does not exist");
        if (position == null)
            return new Pair(false, "customer does not have fund " + String.valueOf(fund.getSymbol()));

        /*
        // get other pending sell others
        List<Transaction> pendingSellFund = transactionDao.listPendingTransactionByCustomerIdType(
                customer.getId(), SELLFUND);
        double pendingShare = 0;
        for (Transaction t : pendingSellFund) {
            pendingShare += t.getShares();
        }
        */

        if (position.getShare() < position.getPendingShareDecrease() + shares)
            return new Pair(false, "no enough share");

        position.setPendingShareDecrease(position.getPendingShareDecrease() + shares);
        transactionDao.saveTransaction(new Transaction(customer, fund, SELLFUND, shares, null));
        return new Pair(true, "success");
    }

    public Pair requestCheck(long cid, double amount) {
        Customer customer = customerDao.getCustomerById(cid);
        if (customer == null)
            return new Pair(false, "customer id " +
                    String.valueOf(cid) + " does not exist");
        if (customer.getCash() < customer.getPendingCashDecrease() + amount)
            return new Pair(false, "no enough cash");

        customer.setPendingCashDecrease(customer.getPendingCashDecrease() + amount);
        transactionDao.saveTransaction(new Transaction(customer, null, REQUESTCHECK, null, amount));
        return new Pair(true, "success");
    }

    public Pair depositCheck(long cid, double amount) {
        Customer customer = customerDao.getCustomerById(cid);
        if (customer == null)
            return new Pair(false, "customer id " + String.valueOf(cid) + " does not exist");
        transactionDao.saveTransaction(new Transaction(customer, null, DEPOSITCHECK, null, amount));
        return new Pair(true, "success");
    }

    public List<Transaction> listPendingTransactionByCustomerId(long cid) {
        return transactionDao.listPendingTransactionByCustomerId(cid);
    }

    public List<Transaction> listFinishTransactionByCustomerId(long cid) {
        return transactionDao.listFinishTransactionByCustomerId(cid);
    }

    public void executeDepositCheck(LocalDate date) {

    }

}
