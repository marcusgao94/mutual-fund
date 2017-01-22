package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.FundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundService {
    @Autowired
    private FundDao fundDao;

}
