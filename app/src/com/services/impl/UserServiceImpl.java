package com.services.impl;

import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;
import com.model.RoleId;
import com.model.User;
import com.services.UserService;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceManager;
import com.services.shared.ServiceSharedResources;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserServiceImpl extends BaseService implements UserService {

    public UserServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.UserService, sharedResources);
    }

    @Override
    public boolean updateUser(User user) {
        if (ServiceManager.getInstance().getPermissionService().canEditUserInfo(user)) {
            UserDAO userDAO = new UserDAOImpl();
            return userDAO.update(user);
        }

        setErrorInfo(HttpServletResponse.SC_FORBIDDEN, "У вас нет прав для изменения информации в профиле!");
        return false;
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
    public List<User> getSeveralUsers(int from, int count) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getSeveralUsers(from, count);
    }

    @Override
    public User getUserByID(int id) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getById(id);
    }

    public List<User> getList() {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.list();
    }
}
