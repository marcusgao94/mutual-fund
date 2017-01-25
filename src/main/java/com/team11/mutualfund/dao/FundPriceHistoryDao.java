package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.FundDate;
import com.team11.mutualfund.model.FundPriceHistory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class FundPriceHistoryDao extends AbstractDao<Long, FundPriceHistory> {
    public void save(FundPriceHistory fundPriceHistory) {
        persist(fundPriceHistory);
    }

    @SuppressWarnings("unchecked")
    public List<FundPriceHistory> listByFundId(long fid) {
        Query query = getSession().createQuery(
                "select fph from FundPriceHistory fph where " +
                        "fph.fund.id = :fid " +
                        "order by fundDate.date desc"
        )
                .setParameter("fid", fid);
        return (List<FundPriceHistory>) query.list();
    }

    public FundPriceHistory getFundPriceHistoryByFundTicker(String ft) {
        Query query = getSession().createQuery(
                "select fph from FundPriceHistory fph where " +
                        "fph.fund.ticker = :ft"
        )
                .setParameter("ft", ft);
        return (FundPriceHistory) query.uniqueResult();
    }

    public FundPriceHistory findByFundDate(FundDate fundDate) {
        Query query = getSession().createQuery(
                "select fph from FundPriceHistory fph where " +
                        "fph.fundDate = :fd"
        )
                .setParameter("fd", fundDate);
        return (FundPriceHistory) query.uniqueResult();
    }
}
