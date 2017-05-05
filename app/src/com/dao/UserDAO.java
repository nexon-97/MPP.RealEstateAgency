package com.dao;

import com.model.RoleId;
import com.model.User;

import java.util.List;

public interface UserDAO {
    User getById(int id);
    User getByLogin(String login);
    List<User> getUsersByRole(RoleId roleId);
    boolean save(User user);
    boolean update(User user);
    List<User> getSeveralUsers(int from, int count);
    List<User> list();
}
