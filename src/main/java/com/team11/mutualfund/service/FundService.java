package com.team11.mutualfund.service;

import com.team11.mutualfund.dao.FundDao;
import com.team11.mutualfund.model.Fund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FundService {
    @Autowired
    private FundDao fundDao;

}
