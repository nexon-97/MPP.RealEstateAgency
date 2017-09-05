package com.services.impl;

import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;
import com.model.RoleId;
import com.model.User;
import com.services.PermissionService;
import com.services.UserService;
import com.services.shared.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean updateUser(User user) {
        if (permissionService.canEditUserInfo(user)) {
            UserDAO userDAO = new UserDAOImpl();
            return userDAO.update(user);
        }

        setErrorInfo(HttpServletResponse.SC_FORBIDDEN, "У вас нет прав для изменения информации в профиле!");
        return false;
    }

    @Override
    public User getUserByLogin(String login) {
        return userDAO.getByLogin(login);
    }

    @Override
    public List<User> getUsersByRole(RoleId roleId) {
        return userDAO.getUsersByRole(roleId);
    }    

    @Override
    public List<User> getSeveralUsers(int from, int count) {
        return userDAO.getSeveralUsers(from, count);
    }

    @Override
    public User getUserByID(int id) {
        return userDAO.getById(id);
    }

    public List<User> getList() {
        return userDAO.list();
    }
}
