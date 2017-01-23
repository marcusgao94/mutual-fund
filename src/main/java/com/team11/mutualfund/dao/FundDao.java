package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Fund;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FundDao extends AbstractDao<Long, Fund> {

    public void saveFund(Fund fund) {
        persist(fund);
    }

    public Fund getFundById(long id) {
        return getByKey(id);
    }

    public List<Fund> listFund() {
        return listAll();
    }


}
