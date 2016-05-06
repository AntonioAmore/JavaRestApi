package com.github.AntonioAmore.DataStorage;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.restexpress.exception.NotFoundException;

public class TransactionRepository {

    ArrayList<Transaction> transactions = new ArrayList();

    /**
     *
     * @param data
     * @return
     * @throws Exception
     */
    public Transaction addTransaction(Transaction data) throws Exception {
        int lastId = 0;
        if (size() != 0) {
            lastId = transactions.get(transactions.size() - 1).getId();
        }

        data.setId(lastId + 1);
        boolean success = transactions.add(data);

        if (!success) {
            throw new Exception("Cannot add a transaction");
        }

        return data;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean rollbackTransaction(int id) {
        boolean result = false;
        for (int i = 0; i < size(); i++) {
            if (transactions.get(i).getId() == id) {
                transactions.remove(i);
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     *
     * @param index
     * @return
     */
    public ArrayList<Transaction> getIncomingTransactionsForUser(int index) {
        ArrayList<Transaction> result = new ArrayList();

        for (Transaction i : transactions) {
            if (index == i.getDestinationUserId()) {
                result.add(i);
            }
        }

        return result;
    }

    /**
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    public Transaction getTransaction(int id) throws Exception {
        Transaction result = null;

        for(Transaction i: transactions) {
            if (i.getId() == id) {
                result = i;
                break;
            }
        }

        if(null==result) {
            throw new NotFoundException("Not found");
        }

        return result;
    }

    /**
     *
     * @return
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param index
     * @return
     */
    public ArrayList<Transaction> getOutcomingTransactionsForUser(int index) {
        ArrayList<Transaction> result = new ArrayList();

        for (Transaction i : transactions) {
            if (index == i.getSourceUserId()) {
                result.add(i);
            }
        }

        return result;
    }

    /**
     *
     * @param index
     * @return
     */
    public BigDecimal getUserBalance(int index) {
        BigDecimal result = new BigDecimal("0.0");

        for (Transaction i : this.getIncomingTransactionsForUser(index)) {
            result = result.add(i.getSum());
        }

        for (Transaction i : this.getOutcomingTransactionsForUser(index)) {
            result = result.subtract(i.getSum());
        }

        return result;
    }

    /**
     *
     * @return
     */
    public int size() {
        return transactions.size();
    }
}
