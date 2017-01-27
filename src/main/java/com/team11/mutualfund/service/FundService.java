package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.CustomerDao;
import com.team11.mutualfund.dao.FundDao;
import com.team11.mutualfund.dao.FundPriceHistoryDao;
import com.team11.mutualfund.dao.PositionDao;
import com.team11.mutualfund.model.*;

import com.team11.mutualfund.utils.Positionvalue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.team11.mutualfund.utils.Constant.*;

@Service
@Transactional
public class FundService {

    @Autowired
    private FundDao fundDao;

    @Autowired
    private FundPriceHistoryDao fundPriceHistoryDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private CustomerDao customerDao;

    public void createFund(Fund fund) throws RollbackException {
        if (fundDao.findByTicker(fund.getTicker()) != null)
            throw new RollbackException(DUPLICATEFUNDTICKER);
        fundDao.saveFund(fund);
    }

    public Fund getFundByTicker(String ticker) {
        return fundDao.findByTicker(ticker);
    }

    public void updateFundPrice(String ticker, LocalDate date, double price)
            throws RollbackException {
        Fund fund = fundDao.findByTicker(ticker);
        if (fund == null)
            throw new RollbackException(NOFUND);
        FundPriceHistory fundPriceHistory = new FundPriceHistory();
        FundDate fundDate = new FundDate(fund.getId(), date);
        if (fundPriceHistoryDao.findByFundDate(fundDate) != null)
            throw new RollbackException(DUPLICATEFUNDPRICEHISTORY);
        fundPriceHistory.setFundDate(new FundDate(fund.getId(), date));
        fundPriceHistory.setPrice(price);
        fundPriceHistory.setFund(fund);
        fundPriceHistoryDao.save(fundPriceHistory);
    }

    //get fund history by fundticker
    public FundPriceHistory getFundPriceHistoryByTicker(String ticker){
        return fundPriceHistoryDao.findByFundTicker(ticker);
    }

    //list all available funds for purchasing
    public List<Fund> listFund() {
        return fundDao.listFund();
    }
    //list funds that a customer purchased

    public List<Positionvalue> listPositionvalueByCustomerId(long cid) {
        List<Positionvalue> positionvalueList = new LinkedList();
        List<Position> positionList = positionDao.listByCustomerId(cid);
        for (Position p : positionList) {
            List<FundPriceHistory> fph = fundPriceHistoryDao.listByFundId(p.getFund().getId());
            double price = fph.get(0).getPrice();
            Positionvalue pv = new Positionvalue();
            pv.setFund(p.getFund());
            pv.setShares(p.getShares());
            pv.setPrice(price);
            pv.setValue(p.getShares() * price);
            positionvalueList.add(pv);
        }
        return positionvalueList;
    }


}

