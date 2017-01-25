package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.FundPriceHistory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class FundPriceHistoryDao extends AbstractDao<Long, FundPriceHistory> {
    public void save(FundPriceHistory fundPriceHistory) {
        persist(fundPriceHistory);
    }

    public FundPriceHistory getFundPriceHistoryByFundTicker(String ft) {
        Query query = getSession().createQuery(
                "select fph from FundPriceHistory fph where " +
                        "fph.fund.id = :ft"
        )
                .setParameter("ft", ft);
        return (FundPriceHistory) query.uniqueResult();
    }
}
