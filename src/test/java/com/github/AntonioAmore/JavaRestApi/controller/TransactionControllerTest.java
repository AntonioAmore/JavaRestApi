/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.AntonioAmore.JavaRestApi.controller;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.AntonioAmore.DataStorage.Transaction;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.config.JsonConfig.jsonConfig;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import static com.jayway.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import com.jayway.restassured.response.Response;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author anton
 */
public class TransactionControllerTest {

    public TransactionControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setupRestAssured() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
        RestAssured.basePath = "/";

        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(new Jackson2ObjectMapperFactory() {

                    @SuppressWarnings("rawtypes")
                    @Override
                    public com.fasterxml.jackson.databind.ObjectMapper create(Class cls, String charset) {
                        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

                        return objectMapper;
                    }
                }));


        config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
    }

    @After
    public void tearDown() {
    }

    private Response createTransaction(int from, int to, String sum) {
        Transaction transaction = new Transaction(from, to, sum);
        com.jayway.restassured.response.Response response = given().
                contentType("application/json").
                body(transaction).
                when().
                post("/transaction").
                then().
                statusCode(200).
                body("sourceUserId", equalTo(from)).
                body("destinationUserId", equalTo(to)).
                body("sum", equalTo(new BigDecimal(sum))).
                extract().
                response();

        return response;
    }

    /**
     * Test of create method, of class TransactionController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        int from = UserControllerTest.createUser("Test user 1").path("id");
        int to = UserControllerTest.createUser("Test user 2").path("id");
        createTransaction(from, to, "7.40");
    }

    /**
     * Test of delete method, of class TransactionController.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int from = UserControllerTest.createUser("Test user 1").path("id");
        int to = UserControllerTest.createUser("Test user 2").path("id");
        int id = createTransaction(from, to, "7.40").path("id");

        given().
                when().
                delete("/transaction/{transaction_id}", id).
                then().
                statusCode(200);

        given().
                when().
                get("/transaction/{transaction_id}", id).
                then().
                statusCode(404);
    }

    /**
     * Test of read method, of class TransactionController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        int from = UserControllerTest.createUser("Test user 1").path("id");
        int to = UserControllerTest.createUser("Test user 2").path("id");
        int id = createTransaction(from, to, "7.40").path("id");

        given().
                when().
                get("/transaction/{transaction_id}", id).
                then().
                statusCode(200).
                body("sourceUserId", equalTo(from)).
                body("destinationUserId", equalTo(to)).
                body("sum", equalTo(new BigDecimal("7.40")));
    }

    /**
     * Test of readAll method, of class TransactionController.
     */
    @Test
    public void testReadAll() {
        System.out.println("readAll");

        int from = UserControllerTest.createUser("Test user 1").path("id");
        int to = UserControllerTest.createUser("Test user 2").path("id");
        int id = createTransaction(from, to, "7.40").path("id");

        given().
                when().
                get("/transaction").
                then().
                statusCode(200).
                body("size()", greaterThan(1));
    }

    /**
     * Test of getBalance method, of class TransactionController.
     */
    @Test
    public void testGetBalance() {
        System.out.println("getBalance");

        int from = UserControllerTest.createUser("Test user 1").path("id");
        int to = UserControllerTest.createUser("Test user 2").path("id");
        int id1 = createTransaction(from, to, "2.5").path("id");
        int id2 = createTransaction(to, from, "1.0").path("id");

        given().
                when()
                .get("/user/{user_id}/balance", from)
                .then()
                .statusCode(200)
                .body(equalTo("-1.5"));

        given().
                when()
                .get("/user/{user_id}/balance", to)
                .then()
                .statusCode(200)
                .body(equalTo("1.5"));
    }

}
