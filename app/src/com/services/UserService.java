package com.services;

import com.model.User;

import java.util.List;

public interface UserService {
    boolean updateUser(User user);
    User getUserByLogin(String login);
    List<User> getSeveralUsers(int from, int count);
}
