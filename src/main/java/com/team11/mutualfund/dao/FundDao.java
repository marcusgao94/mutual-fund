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

    public List<Fund> listFund() {
        return listAll();
    }

    @SuppressWarnings("unchecked")
    public Fund getFundByFundId(long fid) {
        Query query = getSession().createQuery(
                "select p from Fund p where p.id = :fid"
        ).setParameter("fid", fid);
        return (Fund) query.uniqueResult();
    }
}
