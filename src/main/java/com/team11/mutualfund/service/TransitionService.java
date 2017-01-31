package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.*;
import com.team11.mutualfund.model.*;
import com.team11.mutualfund.utils.TransitionFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.time.LocalDate;
import java.util.List;

import static com.team11.mutualfund.utils.Constant.NOFUND;
import static com.team11.mutualfund.utils.Constant.NOFUNDPRICEHISTORY;
import static com.team11.mutualfund.utils.Constant.NOPOSITION;
import static com.team11.mutualfund.utils.TransactionType.*;

@Service
@Transactional
public class TransitionService {

    @Autowired
    private FundDao fundDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private FundPriceHistoryDao fundPriceHistoryDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private FundService fundService;

    public void executeBuyFund(LocalDate date) throws RollbackException {
        List<Transaction> transactionList = transactionDao.listPendingTransactionByType(BUYFUND);
        for (Transaction t : transactionList) {
            // update execute date
            t.setExectuteDate(date);
            // substract cash in customer
            Customer customer = t.getCustomer();
            customer.setCash(customer.getCash() - t.getAmount());
            customer.setPendingCashDecrease(customer.getPendingCashDecrease() - t.getAmount());
            // calculate shares of this amount
            Fund fund = t.getFund();
            FundDate fundDate = new FundDate(fund.getId(), date);
            FundPriceHistory fundPriceHistory = fundPriceHistoryDao.findByFundDate(fundDate);
            if (fundPriceHistory == null)
                throw new RollbackException(NOFUNDPRICEHISTORY);
            double price = fundPriceHistory.getPrice();
            double shares = t.getAmount() / price;
            // add customer shares of this fund in position
            CustomerFund cf = new CustomerFund();
            cf.setCustomerId(customer.getId());
            cf.setFundId(fund.getId());
            Position position = positionDao.findByCustomerFund(cf);
            if (position == null) {
                position = new Position();
                position.setCustomerFund(cf);
                position.setShares(shares);
                position.setCustomer(customer);
                position.setFund(fund);
                positionDao.save(position);
            }
            else {
                position.setShares(position.getShares() + shares);
            }
        }
    }

    public void executeSellFund(LocalDate date) throws RollbackException {
        List<Transaction> transactionList = transactionDao.listPendingTransactionByType(SELLFUND);
        for (Transaction t : transactionList) {
            // update execute date
            t.setExectuteDate(date);
            // substract shares in position
            Customer customer = t.getCustomer();
            Fund fund = t.getFund();
            CustomerFund cf = new CustomerFund();
            cf.setCustomerId(customer.getId());
            cf.setFundId(fund.getId());
            Position position = positionDao.findByCustomerFund(cf);
            if (position == null)
                throw new RollbackException(NOPOSITION);
            position.setShares(position.getShares() - t.getShares());
            position.setPendingShareDecrease(position.getPendingShareDecrease() - t.getShares());
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

    public void executeDepositCheck(LocalDate date) {
        List<Transaction> transactionList = transactionDao.listPendingTransactionByType(DEPOSITCHECK);
        for (Transaction t : transactionList) {
            t.setExectuteDate(date);
            Customer customer = t.getCustomer();
            customer.setCash(customer.getCash() + t.getAmount());
        }
    }

    public void executeRequestCheck(LocalDate date) {
        List<Transaction> transactionList = transactionDao.listPendingTransactionByType(REQUESTCHECK);
        for (Transaction t : transactionList) {
            t.setExectuteDate(date);
            Customer customer = t.getCustomer();
            customer.setCash(customer.getCash() - t.getAmount());
            customer.setPendingCashDecrease(0);
        }
    }

    public void transit(LocalDate date, List<TransitionFund> fundList)
            throws RollbackException {
        // execute request, deposit check
        executeRequestCheck(date);
        executeDepositCheck(date);

        // update all fund price
        for (TransitionFund fd : fundList) {
            fundService.updateFundPrice(fd.getFund(), date, fd.getNewPrice());
        }

        // execute buy, sell fund
        executeBuyFund(date);
        executeSellFund(date);
    }

}
