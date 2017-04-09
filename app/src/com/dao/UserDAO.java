package com.dao;

import com.model.User;

import java.util.List;

public interface UserDAO {
    User get(int id);
    User get(String login);
    boolean save(User user);
    boolean update(User user);
    List<User> list();
}
