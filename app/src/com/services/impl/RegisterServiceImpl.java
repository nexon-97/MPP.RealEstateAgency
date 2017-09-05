package com.services.impl;

import com.dao.UserDAO;
import com.model.RoleId;
import com.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.services.RegisterService;
import com.services.shared.BaseService;
import com.utils.request.validator.RequestValidationChain;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterServiceImpl extends BaseService implements RegisterService {

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean register(RequestValidationChain requestValidationChain) {
        User user = new User();

        user.setRoleId(RoleId.User);
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
