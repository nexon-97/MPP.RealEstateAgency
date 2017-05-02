package com.services;

import com.model.User;

public interface UserService {
    User getUserByID(int id);
    boolean updateUser(User user);
}
