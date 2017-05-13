package com.dao;

import com.model.RoleId;
import com.model.User;

import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    User getByLogin(String login);
    List<User> getUsersByRole(RoleId roleId);
    List<User> getUsersRange(int from, int count);
}
