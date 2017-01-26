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

import static com.team11.mutualfund.utils.Constant.DUPLICATEFUNDTICKER;
import static com.team11.mutualfund.utils.Constant.NOCUSTOMER;
import static com.team11.mutualfund.utils.Constant.NOFUNDPRICE;

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

    public void updateFundPrice(Fund fund, LocalDate date, double price) {
        FundPriceHistory fundPriceHistory = new FundPriceHistory();
        fundPriceHistory.setFundDate(new FundDate(fund.getId(), date));
        fundPriceHistory.setPrice(price);
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

    public List<Positionvalue> listPositionvalueByCustomerId(long cid) throws RollbackException {
        List<Positionvalue> positionvalueList = new LinkedList();
        List<Position> positionList = positionDao.listByCustomerId(cid);
        for (Position p : positionList) {
            List<FundPriceHistory> fph = fundPriceHistoryDao.listByFundId(p.getFund().getId());
            if (fph == null)
                throw new RollbackException(NOFUNDPRICE);
            double price = fph.get(0).getPrice();
            Positionvalue pv = new Positionvalue();
            pv.setFund(p.getFund());
            pv.setShares(p.getShares());
            pv.setValue(p.getShares() * price);
            positionvalueList.add(pv);
        }
        return positionvalueList;
    }


}

