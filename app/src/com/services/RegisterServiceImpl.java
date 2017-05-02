package com.services;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;

import com.model.Role;
import com.model.RoleId;
import com.model.User;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceManager;
import com.services.shared.ServiceSharedResources;
import com.utils.request.validator.RequestValidationChain;

public class RegisterServiceImpl extends BaseService implements RegisterService {

    public RegisterServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.RegistrationService, sharedResources);
    }

    @Override
    public boolean register(RequestValidationChain requestValidationChain) {
        User user = new User();
        UserDAO userDAO = new UserDAOImpl();

        Role userRole = new Role();
        userRole.setId(RoleId.User.ordinal());
        userRole.setName("User");

        user.setRole(userRole);
        user.setLogin((String) requestValidationChain.getValue("login"));
        user.setPasswordHash( getPasswordHash((String) requestValidationChain.getValue("password")) );
        user.setEmail((String) requestValidationChain.getValue("email"));
        user.setName((String) requestValidationChain.getValue("name"));
        user.setSurname((String) requestValidationChain.getValue("surname"));
        user.setPatronymic((String) requestValidationChain.getValue("patronymic"));
        user.setPhone((String) requestValidationChain.getValue("phone"));

        if (userDAO.getByLogin(user.getLogin()) != null){
            return false;
        }
        return userDAO.save(user);
    }

    @Override
    public boolean checkEmptyLogin(String login) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getByLogin(login) == null;
    }


    private String getPasswordHash(String password){
        String passwordHash = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            passwordHash = new String(md5.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }
}
