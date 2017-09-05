package com.controller;

import javax.servlet.http.HttpServletResponse;
import com.forms.AuthFormData;
import com.forms.RestorePassData;
import com.model.User;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.services.AuthService;

@Controller
public class AuthController extends BaseController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/auth")
    public String visitAuthorizationForm() {
        if (authService.getLoggedUser() != null) {
            return "redirect:/";
        } else {
            return "auth";
        }
    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String authorize(AuthFormData formData, HttpServletResponse response) {
        String login = formData.getLogin();
        String password = formData.getPassword();

        if (login != null && password != null) {
            authService.login(login, password, response);
        }

        return "auth_result";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletResponse response) {
        authService.logout(response);

        return redirect("/");
    }

    @GetMapping(value = "/restorePassword")
    public String restorePassword(Model model) {
        if (!authService.isUserLoggedIn()) {
            return "restore_pass";
        }

        model.addAttribute("msg", "Для восстановления пароля необходимо выйти из аккаунта!");
        return "restore_pass_failure";
    }

    @PostMapping(value = "/restorePassword")
    public String sendEmail(RestorePassData restorePassData, Model model) {
        User userForRestorePass = userService.getUserByLogin(restorePassData.getLogin());
        if (userForRestorePass == null) {
            model.addAttribute("msg", "Пользователя с таким логином не существует!");
            return "restore_pass_failure";
        }

        return "restore_pass_success";
    }
}
