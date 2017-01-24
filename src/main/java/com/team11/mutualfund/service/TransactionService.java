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
import com.team11.mutualfund.utils.TransactionForm;
import com.team11.mutualfund.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private MessageSource messageSource;

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

    public Pair buyFund(TransactionForm transactionForm) {
        Customer customer = customerDao.getCustomerById(transactionForm.getCustomerId());
        Fund fund = fundDao.getFundById(transactionForm.getFundId());
        if (customer == null)
            return new Pair(false, "customer id " + String.valueOf(transactionForm.getCustomerId()) + " does not exist");
        if (fund == null)
            return new Pair(false, "fund id " + String.valueOf(transactionForm.getFundId()) + " does not exist");
        if (customer.getCash() < customer.getPendingCashDecrease() + transactionForm.getAmount())
            return new Pair(false, "no enough cash");

        customer.setPendingCashDecrease(customer.getPendingCashDecrease() + transactionForm.getAmount());
        Transaction transaction = new Transaction(transactionForm);
        transaction.setCustomer(customer);
        transaction.setFund(fund);
        transactionDao.saveTransaction(transaction);
        // need to check this sentence
        return new Pair(true, "success");
    }

    public Pair sellFund(TransactionForm transactionForm) {
        Customer customer = customerDao.getCustomerById(transactionForm.getCustomerId());
        Fund fund = fundDao.getFundById(transactionForm.getFundId());
        Position position = positionDao.getPositionByCustomerIdFundId(
                transactionForm.getCustomerId(), transactionForm.getFundId());
        if (customer == null)
            return new Pair(false, "customer id " +
                    String.valueOf(transactionForm.getCustomerId()) + " does not exist");
        if (fund == null)
            return new Pair(false, "fund id " +
                    String.valueOf(transactionForm.getFundId()) + " does not exist");
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

        if (position.getShare() < position.getPendingShareDecrease() + transactionForm.getShares())
            return new Pair(false, "no enough share");

        position.setPendingShareDecrease(position.getPendingShareDecrease() + transactionForm.getShares());
        Transaction transaction = new Transaction(transactionForm);
        transaction.setCustomer(customer);
        transaction.setFund(fund);
        transactionDao.saveTransaction(transaction);
        return new Pair(true, "success");
    }

    public Pair requestCheck(TransactionForm transactionForm) {
        Customer customer = customerDao.getCustomerById(transactionForm.getCustomerId());
        if (customer == null)
            return new Pair(false, "customer id " +
                    String.valueOf(transactionForm.getCustomerId()) + " does not exist");
        if (customer.getCash() < customer.getPendingCashDecrease() + transactionForm.getAmount())
            return new Pair(false, "no enough cash");

        customer.setPendingCashDecrease(customer.getPendingCashDecrease() + transactionForm.getAmount());
        Transaction transaction = new Transaction(transactionForm);
        transaction.setCustomer(customer);
        transactionDao.saveTransaction(transaction);
        return new Pair(true, "success");
    }

    public Pair depositCheck(TransactionForm transactionForm) {
        Customer customer = customerDao.getCustomerById(transactionForm.getCustomerId());
        if (customer == null)
            return new Pair(false, "customer id " +
                    String.valueOf(transactionForm.getCustomerId()) + " does not exist");
        Transaction transaction = new Transaction(transactionForm);
        transaction.setCustomer(customer);
        transactionDao.saveTransaction(transaction);
        return new Pair(true, "success");
    }

    public List<TransactionForm> listPendingTransactionByCustomerId(long cid) {
        List<Transaction> pendingTransactionList = transactionDao.listPendingTransactionByCustomerId(cid);
        List<TransactionForm> pendingTransactionFormList = new LinkedList<>();
        for (Transaction t : pendingTransactionList) {
            pendingTransactionFormList.add(new TransactionForm(t));
        }
        return pendingTransactionFormList;
    }

    public List<TransactionForm> listFinishTransactionByCustomerId(long cid) {
        List<Transaction> finishTransactionList = transactionDao.listFinishTransactionByCustomerId(cid);
        List<TransactionForm> finishTransactionFormList = new LinkedList<>();
        for (Transaction t : finishTransactionList) {
            finishTransactionFormList.add(new TransactionForm(t));
        }
        return finishTransactionFormList;
    }
}
