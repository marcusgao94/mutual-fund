package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.FundDao;
import com.team11.mutualfund.model.Fund;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
//create fund in the position table
public class FundService {
    @Autowired
    private FundDao fundDao;

    public boolean createFund(Fund fund) {
		if (fundDao.getFundByFundId(fund.getId()) != null) {
			return false;
		}
		fundDao.saveFund(fund);
		return true;
	}
    //list all available funds for purchasing
    public List<Fund> listFund() {
        return fundDao.listFund();
    }
    //list funds that a customer purchased
    


}