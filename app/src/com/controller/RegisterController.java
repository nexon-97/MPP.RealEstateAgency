package com.controller;

import com.model.RoleId;
import com.security.annotations.RoleCheck;
import com.services.RegisterService;
import com.utils.request.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController extends BaseController{

    @Autowired
    RegisterService registerService;

    @GetMapping(value = "/register")
    @RoleCheck(RoleId.Guest)
    public String visitRegistrationForm() {
        return "register/register";
    }

    @PostMapping(value = "/register")
    @RoleCheck(RoleId.Guest)
    public String register(Model model) {
        RequestValidationChain requestValidator = buildRegisterDataValidator();
        if (requestValidator.validate(request)) {
            boolean isAdded = registerService.register(requestValidator);
            if (isAdded) {
                return "register/register_success";
            } else {
                model.addAttribute("values", requestValidator.getValidatedValues());
                model.addAttribute("loginEmpty", false);

                return "register/register";
            }
        } else {
            return getViewWithErrors(model, "errors", requestValidator.getErrorMessageMap(), requestValidator.getValidatedValues());
        }
    }

    private String getViewWithErrors(Model model, String key, Object error, Object values){
        model.addAttribute(key, error);
        model.addAttribute("values", values);
        return "register/register";
    }

    private RequestValidationChain buildRegisterDataValidator(){
        return new RequestValidationChain()
                .addValidator(new LoginStringParameterValidator("login", false))
                .addValidator(new PasswordStringParameterValidator("password", false))
                .addValidator(new EmailStringParameterValidator("email", false))
                .addValidator(new FullNameStringParameterValidator("name", false))
                .addValidator(new FullNameStringParameterValidator("surname", false))
                .addValidator(new FullNameStringParameterValidator("patronymic", false))
                .addValidator(new PhoneStringParameterValidator("phone", false));
    }
}
