package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.FundDao;
import com.team11.mutualfund.dao.FundPriceHistoryDao;
import com.team11.mutualfund.model.Fund;
import com.team11.mutualfund.model.Customer;

import com.team11.mutualfund.model.FundDate;
import com.team11.mutualfund.model.FundPriceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class FundService {
    @Autowired
    private FundDao fundDao;

    @Autowired
    private FundPriceHistoryDao fundPriceHistoryDao;

	/*
	have not finished
    public boolean createFund(Fund fund, Customer customer) {
		if (fundDao.listFundByCustomerId(customer.getId()) != null) {
			return false;
		}
		fundDao.saveFund(fund,customer.getId());
		return true;
	}
	*/

	public void updateFundPrice(Fund fund, LocalDate date, double price) {
	    FundPriceHistory fundPriceHistory = new FundPriceHistory();
        fundPriceHistory.setFundDate(new FundDate(fund.getId(), date));
        fundPriceHistory.setPrice(price);
        fundPriceHistoryDao.save(fundPriceHistory);
    }

}
