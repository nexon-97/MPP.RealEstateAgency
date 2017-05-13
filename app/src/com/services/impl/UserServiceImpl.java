package com.services.impl;

import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;
import com.model.RoleId;
import com.model.User;
import com.services.UserService;
import com.services.shared.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserServiceImpl extends AbstractCrudService<User> implements UserService {

    public UserServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.UserService, sharedResources, User.class);
    }

    @Override
    public User getUserByLogin(String login) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getByLogin(login);
    }

    @Override
    public List<User> getUsersByRole(RoleId roleId) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getUsersByRole(roleId);
    }    

    @Override
    public List<User> listUsersRange(int from, int count) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getUsersRange(from, count);
    }
}
