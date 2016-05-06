package com.github.AntonioAmore.JavaRestApi.controller;

import com.github.AntonioAmore.DataStorage.DataStorage;
import com.github.AntonioAmore.DataStorage.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.restexpress.Request;
import org.restexpress.Response;

public class TransactionController {
    private static final String TRANSACTION_ID_HEADER = "transaction_id";
    private static final String USER_ID_HEADER = "user_id";

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public Transaction create(Request request, Response response) throws Exception {
        Transaction transaction = request.getBodyAs(Transaction.class, "Transaction data aren't provided");
        transaction.validate();

        return DataStorage.getInstance().getTransactionRepository().addTransaction(transaction);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Boolean delete(Request request, Response response) {
        int id = Integer.parseInt(request.getHeader(TRANSACTION_ID_HEADER));
        return DataStorage.getInstance().getTransactionRepository().rollbackTransaction(id);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public Transaction read(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.getHeader(TRANSACTION_ID_HEADER));
        return DataStorage.getInstance().getTransactionRepository().getTransaction(id);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public ArrayList <Transaction> readAll(Request request, Response response) {
        return DataStorage.getInstance().getTransactionRepository().getTransactions();
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public BigDecimal getBalance(Request request, Response response) {
        int id = Integer.parseInt(request.getHeader(USER_ID_HEADER));
        return DataStorage.getInstance().getTransactionRepository().getUserBalance(id);
    }
}
