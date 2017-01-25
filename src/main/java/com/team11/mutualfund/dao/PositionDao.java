package com.team11.mutualfund.dao;

import com.team11.mutualfund.model.Fund;
import com.team11.mutualfund.model.Position;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PositionDao extends AbstractDao<Long, Position> {

    public void save(Position position) {
        persist(position);
    }

    public Position findByCustomerIdFundId(long cid, long fid) {
        Query query = getSession().createQuery(
                "select p from Position p where " +
                        "p.customer.id = :cid and " +
                        "p.fund.id = :fid"
        )
                .setParameter("cid", cid)
                .setParameter("fid", fid);
        return (Position) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Position> listByCustomerId(long cid) {
        Query query = getSession().createQuery(
                "select p from Position p where p.customer.id = :cid"
        ).setParameter("cid", cid);
        return (List<Position>) query.list();
    }

    public void delete(Position position) {
        super.delete(position);
    }
}
