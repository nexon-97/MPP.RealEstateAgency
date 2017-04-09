package com.controller;

public interface AuthController {
    boolean checkUser(String login);
    boolean login(String login, String password);
    boolean logout();
}
