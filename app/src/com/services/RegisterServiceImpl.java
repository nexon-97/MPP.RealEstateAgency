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

            return false;
        }
        boolean isCorrectFields = true;

        user.setLogin( params.get("login")[0]);
        user.setEmail(params.get("email")[0]);
        user.setName(params.get("name")[0]);
        user.setSurname(params.get("surname")[0]);
        user.setPatronymic(params.get("patronymic")[0]);
        user.setPhone(params.get("phone")[0]);
        user.setRoleId(RoleId.User);

        if (checkPassword(params.get("password")[0])){
            user.setPasswordHash(getPasswordHash(params.get("password")[0]));
        }
        else {
            user.setPasswordHash(null);
            isCorrectFields = false;
        }

        if (!checkLogin(user.getLogin())){
            user.setLogin(null);
            isCorrectFields = false;
        }

        if (!checkPersonData(user.getName())){
            user.setName(null);
            isCorrectFields = false;
        }

        if (!checkPersonData(user.getSurname())){
            user.setSurname(null);
            isCorrectFields = false;
        }

        if (!checkPersonData(user.getPatronymic())){
            user.setPatronymic(null);
            isCorrectFields = false;
        }

        if (!checkEmail(user.getEmail())){
            user.setEmail(null);
            isCorrectFields = false;
        }

        if (!checkPhone(user.getPhone())){
            user.setPhone(null);
            isCorrectFields = false;
        }

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
