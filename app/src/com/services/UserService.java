package com.services;

import com.model.RoleId;
import com.model.User;

import java.util.List;

public interface UserService extends CrudService<User> {
    List<User> getUsersByRole(RoleId roleId);
    User getUserByLogin(String login);
    List<User> listUsersRange(int from, int count);
}
