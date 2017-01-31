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
import static com.team11.mutualfund.utils.TransactionType.DEPOSITCHECK;
import static com.team11.mutualfund.utils.TransactionType.REQUESTCHECK;

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
                position.setCustomer(customer);
                position.setFund(fund);
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
        Customer customer = customerDao.findById(cid);
        List<Transaction> transactionList = transactionDao.listPendingTransactionByCustomerIdType(cid, REQUESTCHECK);
        for (Transaction t : transactionList) {
            t.setExectuteDate(date);
            customer.setCash(customer.getCash() - t.getAmount());
        }
        customer.setPendingCashDecrease(0);
    }

    public void updateAllFundPrice(LocalDate date, List<TransitionFund> fundList) {

    }

}
