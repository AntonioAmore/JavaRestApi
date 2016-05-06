package com.github.AntonioAmore.DataStorage;

public class DataStorage {

    private static TransactionRepository transactions;
    private static UserRepository users;
    private static DataStorage instance = null;

    protected DataStorage() {
        transactions = new TransactionRepository();
        users = new UserRepository();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public TransactionRepository getTransactionRepository() {
        return DataStorage.transactions;
    }

    public UserRepository getUserRepository() {
        return DataStorage.users;
    }
}
