package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.*;
import com.team11.mutualfund.model.*;
import com.team11.mutualfund.utils.Pair;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.jvm.hotspot.runtime.posix.POSIXSignals;

import javax.persistence.RollbackException;
import javax.swing.*;
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

    public List<Transaction> listPendingTransactionByCustomerId(long cid) {
        return transactionDao.listPendingTransactionByCustomerId(cid);
    }

    public List<Transaction> listFinishTransactionByCustomerId(long cid) {
        return transactionDao.listFinishTransactionByCustomerId(cid);
    }

    public void buyFund(long cid, String ticker, double amount) throws RollbackException {
        Customer customer = customerDao.getCustomerById(cid);
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
        Customer customer = customerDao.getCustomerById(cid);
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


    public void executeBuyFund(String ticker, LocalDate date) throws RollbackException {
        Fund fund = fundDao.findByTicker(ticker);
        if (fund == null)
            throw new RollbackException(NOFUND);
        List<Transaction> transactionList = transactionDao.listPendingBuyFundByFundTicker(ticker);
        for (Transaction t : transactionList) {
            // update execute date
            t.setExectuteDate(date);
            // substract cash in customer
            Customer customer = t.getCustomer();
            customer.setCash(customer.getCash() - t.getAmount());
            customer.setPendingCashDecrease(0);
            // calculate shares of this amount
            FundDate fundDate = new FundDate(fund.getId(), date);
            FundPriceHistory fundPriceHistory = fundPriceHistoryDao.findByFundDate(fundDate);
            if (fundPriceHistory == null)
                throw new RollbackException(NOFUNDPRICEHISTORY);
            double price = fundPriceHistory.getPrice();
            double shares = t.getAmount() / price;
            // add customer shares of this fund in position
            Position position = positionDao.findByCustomerIdFundId(customer.getId(), fund.getId());
            if (position == null) {
                position = new Position();
                CustomerFund customerFund = new CustomerFund();
                customerFund.setCustomerId(customer.getId());
                customerFund.setFundId(t.getFund().getId());
                position.setCustomerFund(customerFund);
                position.setShares(shares);
                positionDao.save(position);
            }
            else {
                position.setShares(position.getShares() + shares);
            }
        }
    }

    public void executeSellFund(String ticker, LocalDate date) throws RollbackException {
        Fund fund = fundDao.findByTicker(ticker);
        if (fund == null)
            throw new RollbackException(NOFUND);
        List<Transaction> transactionList = transactionDao.listPendingBuyFundByFundTicker(ticker);
        for (Transaction t : transactionList) {
            // update execute date
            t.setExectuteDate(date);
            // substract shares in position
            Customer customer = t.getCustomer();
            Position position = positionDao.findByCustomerIdFundId(customer.getId(), fund.getId());
            if (position == null)
                throw new RollbackException(NOPOSITION);
            position.setShares(position.getShares() - t.getShares());
            position.setPendingShareDecrease(0);
            if (Math.abs(position.getShares()) < 1e-10)
                positionDao.delete(position);

            // calculate amount of this share
            FundDate fundDate = new FundDate(fund.getId(), date);
            FundPriceHistory fundPriceHistory = fundPriceHistoryDao.findByFundDate(fundDate);
            if (fundPriceHistory == null)
                throw new RollbackException(NOFUNDPRICEHISTORY);
            double price = fundPriceHistory.getPrice();
            double amount = price * t.getShares();
            // add customer shares of this fund in position
            customer.setCash(customer.getCash() + amount);
        }
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
