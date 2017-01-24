package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Fund;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FundDao extends AbstractDao<Long, Fund> {
<<<<<<< HEAD
=======

>>>>>>> 4d8a1c1b55ac495a0aed92e9a6649ffe1caefda2
    public void saveFund(Fund fund) {
        persist(fund);
    }

    public Fund getFundById(long id) {
        return getByKey(id);
    }

    public List<Fund> listFund() {
        return listAll();
    }

<<<<<<< HEAD
    @SuppressWarnings("unchecked")
    public Fund getFundByFundId(long fid) {
        Query query = getSession().createQuery(
                "select p from Fund p where p.id = :fid"
        ).setParameter("fid", fid);
        return (Fund) query.uniqueResult();
    }
=======

>>>>>>> 4d8a1c1b55ac495a0aed92e9a6649ffe1caefda2
}
