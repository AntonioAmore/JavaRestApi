package com.github.AntonioAmore.JavaRestApi;

import com.github.AntonioAmore.DataStorage.DataStorage;
import com.github.AntonioAmore.DataStorage.User;
import com.github.AntonioAmore.JavaRestApi.controller.TransactionController;
import com.github.AntonioAmore.JavaRestApi.controller.UserController;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restexpress.Format;
import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

public class Configuration
        extends Environment {

    private static final String NAME_PROPERTY = "name";
    private static final String PORT_PROPERTY = "port";
    private static final String DEFAULT_FORMAT_PROPERTY = "defaultFormat";
    private static final String WORKER_COUNT_PROPERTY = "workerCount";
    private static final String EXECUTOR_THREAD_COUNT_PROPERTY = "executorThreadCount";
    private static final String CREATE_TEST_USERS = "createTestUsers";
    private static final String TEST_USERS_NUMBER = "testUsersNumber";

    private static final int DEFAULT_WORKER_COUNT = 0;
    private static final int DEFAULT_EXECUTOR_THREAD_COUNT = 0;

    private int port;
    private String name;
    private String defaultFormat;
    private int workerCount;
    private int executorThreadCount;

    private boolean createTestUsers;
    private int testUsersNumber;

    private TransactionController transactionController = new TransactionController();
    private UserController userController = new UserController();

    @Override
    protected void fillValues(Properties p) {
        this.name = p.getProperty(NAME_PROPERTY, RestExpress.DEFAULT_NAME);
        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
        this.defaultFormat = p.getProperty(DEFAULT_FORMAT_PROPERTY, Format.JSON);
        this.workerCount = Integer.parseInt(p.getProperty(WORKER_COUNT_PROPERTY, String.valueOf(DEFAULT_WORKER_COUNT)));
        this.executorThreadCount = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_COUNT_PROPERTY, String.valueOf(DEFAULT_EXECUTOR_THREAD_COUNT)));
        this.createTestUsers = Boolean.parseBoolean(p.getProperty(CREATE_TEST_USERS, "false"));
        this.testUsersNumber = Integer.parseInt(p.getProperty(TEST_USERS_NUMBER, "0"));

        if (createTestUsers)
        try {
            this.initDatastorage();
        } catch (Exception ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void initDatastorage() throws Exception {

        String names[] = {"Ivan", "Vasiliy", "Petr", "Andrey", "Nikolay", "Svetlana", "Olga", "Zinaida", "Tamara", "Marina"};
        String conames[] = {"Ivanov", "Vasilyev", "Petrov", "Andreyev", "Nikolayev", "Svetlanov", "Olgin", "Zinaidin", "Tamarin", "Marinin"};

        Random random = new Random();
        int nameIndex, conameIndex;
        String userName;

        for (int i = 0; i < this.testUsersNumber; i++) {
            nameIndex = random.nextInt(names.length);
            conameIndex = random.nextInt(conames.length);

            userName = names[nameIndex] + " " + conames[conameIndex];
            if (nameIndex > 4) {
                userName += "a";
            }

            DataStorage.getInstance().getUserRepository().addUser(new User(userName));
        }
    }

    public String getDefaultFormat() {
        return defaultFormat;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    public int getExecutorThreadCount() {
        return executorThreadCount;
    }

    public UserController getUserController() {
        return userController;
    }

    public int getTestUsersNumber() {
        return testUsersNumber;
    }

    public void setTestUsersNumber(int testUsersNumber) {
        this.testUsersNumber = testUsersNumber;
    }

    public TransactionController getTransactionController() {
        return transactionController;
    }
}
