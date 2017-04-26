package com.services;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.model.Role;
import com.model.User;


import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterServiceImpl extends BaseService implements RegisterService {

    public RegisterServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.RegistrationService, sharedResources);
    }

    @Override
    public boolean register(Map<String, String[]> params) {
        User user = new User();
        UserDAO userDAO = new UserDAOImpl();
        boolean isCorrectFields = true;

        String str = new String(params.get("name")[0].getBytes(),Charset.defaultCharset());

        user.setLogin( params.get("login")[0]);
        user.setEmail(params.get("email")[0]);
        user.setName(params.get("name")[0]);
        user.setSurname(params.get("surname")[0]);
        user.setPatronymic(params.get("patronymic")[0]);
        user.setPhone(params.get("phone")[0]);
        Role userRole = new Role();
        userRole.setId(3);
        userRole.setName("User");
        user.setRole(userRole);

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

        Map<String, Object> model = getSharedResources().getModel();
        model.put("registerUser", user);
        if (isCorrectFields){
            userDAO.save(user);
        }
        return isCorrectFields;
    }

    private boolean checkPassword(String password) {
        return checkDataLength(password, 5, 20);
    }

    private static boolean checkDataLength(String data, int minLength, int maxLength) {
        return (data.length() >= minLength) && (data.length() <= maxLength);
    }


    private  boolean checkPersonData(String data) {
        return checkDataLength(data, 2, 30) && checkPersonDataContent(data);
    }

    private boolean checkPersonDataContent(String data) {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яёА-ЯЁ][a-zA-Zа-яёА-ЯЁ\\-'\\s]*");
        Matcher matcher = pattern.matcher(data);
        return matcher.find() && matcher.group(0).length() == data.length();

    }

    private  boolean checkLogin(String data) {
        return checkDataLength(data, 3, 20) && checkLoginContent(data);
    }

    private boolean checkLoginContent(String data) {
        Pattern pattern = Pattern.compile("[a-zA-Z(\\d)][a-zA-Z(\\d)_]*");
        Matcher matcher = pattern.matcher(data);
        return matcher.find() && matcher.group(0).length() == data.length();
    }

    private  boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\..+");
        Matcher matcher = pattern.matcher(email);
        return matcher.find() && matcher.group(0).length() == email.length();

    }

    private static boolean checkPhone(String phone) {
        Pattern pattern = Pattern.compile("^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
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
