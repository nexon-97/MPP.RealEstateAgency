package com.services;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.model.RoleId;
import com.model.User;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

import java.util.List;

public class UserServiceImpl extends BaseService implements UserService {

    public UserServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.UserService, sharedResources);
    }

    @Override
    public boolean updateUser(User user) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.update(user);
    }

    @Override
    public List<User> getUsersByRole(RoleId roleId) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getUsersByRole(roleId);
    }

    @Override
    public User getUserByID(int id) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getById(id);
    }


}
