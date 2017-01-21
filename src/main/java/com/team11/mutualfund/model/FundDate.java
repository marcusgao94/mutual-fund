package com.team11.mutualfund.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class FundDate implements Serializable {
    private long fundId;
    private Date date;

    public FundDate() {
    }

    public FundDate(long fid, Date d) {
        fundId = fid;
        date = d;
    }

    @Override
    public boolean equals(Object o) {
        FundDate fdp = (FundDate) o;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String datestr1 = df.format(date);
        String datestr2 = df.format(fdp.getDate());
        double delta = 1e-10;
        return fundId == fdp.getFundId() && datestr1.equals(datestr2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fundId, date);
    }

    public long getFundId() {
        return fundId;
    }

    public void setFundId(long fundId) {
        this.fundId = fundId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
