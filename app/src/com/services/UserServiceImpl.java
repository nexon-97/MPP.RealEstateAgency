package com.services;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.model.User;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

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
    public User getUserByID(int id) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getById(id);
    }

    @Override
    public User getUserByLogin(String login) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getByLogin(login);
    }


}
