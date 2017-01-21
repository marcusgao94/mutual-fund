package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Fund;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FundDao extends AbstractDao<Long, Fund> {
    public void saveDao(Fund fund) {
        persist(fund);
    }

    public List<Fund> listFund() {
        return listAll();
    }

    @SuppressWarnings("unchecked")
    public List<Fund> listFundByCustomerId(long cid) {
        Query query = getSession().createQuery(
                "select p from Position p where p.customer.id = :cid"
        ).setParameter("cid", cid);
        return (List<Fund>) query.list();
    }
}
