package com.github.AntonioAmore.DataStorage;

import java.util.ArrayList;
import org.restexpress.exception.NotFoundException;

public class UserRepository {
    ArrayList <User> users = new ArrayList();

    /**
     *
     * @param data
     * @return
     * @throws Exception
     */
    public User addUser(User data) throws Exception {
        int lastId = 0;
        if (size()!=0) {
            lastId = users.get(users.size()-1).getId();
        }

        data.setId(lastId+1);
        boolean success = users.add(data);

        if (!success) {
            throw new Exception("Cannot add a user");
        }

        return data;
    }

    /**
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    public User getUser(int id) throws Exception {
        User result = null;

        for(User i: users) {
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
     * @param id
     * @param data
     * @return
     */
    public User updateUser(int id, User data) {
        int index=-1;
        User currentUser;

        for(int i=0; i<size(); i++) {
            currentUser = users.get(i);

            if (currentUser.getId() == id) {
                data.setId(id);

                if ("".equals(data.getName())) {
                    data.setName(currentUser.getName());
                }

                users.set(i, data);
                index=i;
                break;
            }
        }

        if(-1==index) {
            throw new NotFoundException("Not found");
        }

        return users.get(index);
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean deleteUser(int id) {
       boolean result =  false;

       for(int i=0; i<size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                result = true;
                break;
            }
       }

       return result;
    }

    /**
     *
     * @return
     */
    public ArrayList getUsers() {
        return users;
    }

    public int size() {
        return users.size();
    }
}
