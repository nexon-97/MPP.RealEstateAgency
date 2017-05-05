package com.services;

import com.model.User;

import java.util.List;

public interface UserService {
    User getUserByID(int id);
    boolean updateUser(User user);
    User getUserByLogin(String login);
    List<User> getSeveralUsers(int from, int count);
}
