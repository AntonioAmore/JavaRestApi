package com.github.AntonioAmore.DataStorage;

import java.math.BigDecimal;

public class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public User(String name) {
        this.name = name;
    }

    public User() {
        id = 0;
        name = "";
    }

    /**
     *
     * @param update set to true if is called inside update method
     * @throws java.lang.Exception
     */
    public void validate(boolean... update) throws Exception {
        boolean isUpdate = update.length>0 ? update[0] : false;

        if (!isUpdate) {
            if ("".equals(name)) {
                throw new Exception("Invalid name");
            }
        }
    }
}
