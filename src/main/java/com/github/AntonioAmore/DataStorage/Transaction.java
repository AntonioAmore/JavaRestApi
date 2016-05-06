package com.github.AntonioAmore.DataStorage;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private int id;
    private int sourceUserId;
    private int destinationUserId;
    private BigDecimal sum;
    private Date date;

    public Transaction(int sourceUserId, int destinationUserId, BigDecimal sum) {
        this.sourceUserId = sourceUserId;
        this.destinationUserId = destinationUserId;
        this.sum = sum;
        date = new Date();
    }

    public Transaction(int sourceUserId, int destinationUserId, String sum) {
        this.sourceUserId = sourceUserId;
        this.destinationUserId = destinationUserId;
        this.sum = new BigDecimal(sum);
        date = new Date();
    }

    public Transaction() {
        sourceUserId = 0;
        destinationUserId = 0;
        date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(int sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public int getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(int destinationUserId) {
        this.destinationUserId = destinationUserId;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void validate() throws Exception {
        if (
            sourceUserId == destinationUserId ||
            DataStorage.getInstance().getUserRepository().getUser(sourceUserId) == null ||
            DataStorage.getInstance().getUserRepository().getUser(destinationUserId) == null
        ) {
            throw new Exception("Invalid users");
        }

        if (null == sum) {
            throw new Exception("Invalid sum");
        }
    }
}
