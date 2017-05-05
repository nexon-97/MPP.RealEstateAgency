package com.services;

import com.model.RoleId;
import com.model.User;

import java.util.List;

public interface UserService {
    User getUserByID(int id);
    boolean updateUser(User user);
    List<User> getUsersByRole(RoleId roleId);
}
