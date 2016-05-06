package com.github.AntonioAmore.JavaRestApi.controller;

import com.github.AntonioAmore.DataStorage.DataStorage;
import com.github.AntonioAmore.DataStorage.User;
import com.github.AntonioAmore.DataStorage.UserRepository;
import java.util.ArrayList;
import org.restexpress.Request;
import org.restexpress.Response;

public class UserController {

    private static final String USER_ID_HEADER = "user_id";

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public User create(Request request, Response response) throws Exception {
        User user = request.getBodyAs(User.class, "User data aren't provided");
        user.validate();
        return DataStorage.getInstance().getUserRepository().addUser(user);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Boolean delete(Request request, Response response) {
        int id = Integer.parseInt(request.getHeader(USER_ID_HEADER));
        return DataStorage.getInstance().getUserRepository().deleteUser(id);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public User read(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.getHeader(USER_ID_HEADER));
        return DataStorage.getInstance().getUserRepository().getUser(id);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public User update(Request request, Response response) throws Exception {
        User user = request.getBodyAs(User.class, "User data aren't provided");
        int id = Integer.parseInt(request.getHeader(USER_ID_HEADER));
        user.validate(true);
        return DataStorage.getInstance().getUserRepository().updateUser(id, user);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public ArrayList <User> readAll(Request request, Response response) {
        return DataStorage.getInstance().getUserRepository().getUsers();
    }
}
