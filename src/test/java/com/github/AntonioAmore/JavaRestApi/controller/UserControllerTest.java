package com.github.AntonioAmore.JavaRestApi.controller;

import com.github.AntonioAmore.DataStorage.User;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import com.jayway.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class UserControllerTest {

    public UserControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
        RestAssured.basePath = "/";
    }

    @After
    public void tearDown() {
    }

    public static Response createUser(String name) {
        User user = new User(name);

        Response response = given().
                contentType("application/json").
                body(user).
                when().
                post("/user").
                then().
                statusCode(200).
                body("name", equalTo(name)).
                extract().
                response();

        return response;
    }

    /**
     * Test of create method, of class UserController.
     *
     * @throws java.lang.Exception
     */
    @org.junit.Test
    public void testCreate() throws Exception {
        System.out.println("create");
        createUser("Test User");
    }

    /**
     * Test of read method, of class UserController.
     * @throws java.lang.Exception
     */
    @org.junit.Test
    public void testRead() throws Exception {
        System.out.println("read");

        int id = createUser("Test User").path("id");

        given().
                when().
                get("/user/{user_id}", id).
                then().
                statusCode(200).
                body("name", equalTo("Test User"));

    }

    /**
     * Test of delete method, of class UserController.
     */
    @org.junit.Test
    public void testDelete() {
        System.out.println("delete");

        int id = createUser("Test User").path("id");

        given().
                when().
                delete("/user/{user_id}", id).
                then().
                statusCode(200);

        given().
                when().
                get("/user/{user_id}", id).
                then().
                statusCode(404);
    }

    /**
     * Test of update method, of class UserController.
     * @throws java.lang.Exception
     */
    @org.junit.Test
    public void testUpdate() throws Exception {
        System.out.println("update");

        int id = createUser("Test User").path("id");

        Map<String, Object>  jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "New Name");

        given().
                contentType(JSON).
                body(jsonAsMap).
                when().
                put("/user/{user_id}", id).
                then().
                statusCode(200).
                body("name", equalTo("New Name"));

    }

    /**
     * Test of readAll method, of class UserController.
     */
    @org.junit.Test
    public void testReadAll() {
        System.out.println("readAll");

        createUser("Test User");
        given().
                when().
                get("/user").
                then().
                statusCode(200).
                body("size()", greaterThan(1));
    }

}
