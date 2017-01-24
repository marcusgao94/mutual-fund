package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.FundDao;
import com.team11.mutualfund.dao.FundPriceHistoryDao;
import com.team11.mutualfund.model.Fund;

import com.team11.mutualfund.model.FundDate;
import com.team11.mutualfund.model.FundPriceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class FundService {

    @Autowired
    private FundDao fundDao;

    @Autowired
    private FundPriceHistoryDao fundPriceHistoryDao;

    public boolean createFund(Fund fund) {
        if (fundDao.getFundByFundId(fund.getId()) != null) {
            return false;
        }
        if (fundDao.getFundByTicker(fund.getTicker()) != null)
            return false;
        fundDao.saveFund(fund);
        return true;
    }

    public void updateFundPrice(Fund fund, LocalDate date, double price) {
        FundPriceHistory fundPriceHistory = new FundPriceHistory();
        fundPriceHistory.setFundDate(new FundDate(fund.getId(), date));
        fundPriceHistory.setPrice(price);
        fundPriceHistoryDao.save(fundPriceHistory);
    }

    //list all available funds for purchasing
    public List<Fund> listFund() {
        return fundDao.listFund();
    }
    //list funds that a customer purchased
}

