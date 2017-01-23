package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.FundDao;
import com.team11.mutualfund.model.Fund;
import com.team11.mutualfund.model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FundService {
    @Autowired
    private FundDao fundDao;

    public boolean createFund(Fund fund, Customer customer) {
		if (fundDao.listFundByCustomerId(customer.getId()) != null) {
			return false;
		}
		fundDao.saveFund(fund,customer.getId());
		return true;
	}
}
