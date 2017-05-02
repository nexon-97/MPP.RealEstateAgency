package com.controller;

import com.model.PropertyType;
import com.services.RegisterService;
import com.services.shared.ServiceManager;
import com.utils.request.validator.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class RegisterController extends BaseController{

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public ModelAndView visitRegistrationForm(HttpServletResponse response) {
        initControllerResources(context, request, response);
        if (ServiceManager.getInstance().getAuthService().getLoggedUser() != null){
            return buildModelAndView("register/register_logged");
        }
        else {
            return buildModelAndView("register/register");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ModelAndView register(HttpServletResponse response) {
        initControllerResources(context, request, response);
        RegisterService registerService = ServiceManager.getInstance().getRegisterService();
        RequestValidationChain requestValidator = buildRegisterDataValidator();
        if (requestValidator.validate()){
            boolean isAdded = registerService.register(requestValidator);
            if (isAdded){
                return buildModelAndView("register/register_success");
            }
            else {
                Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
                model.put("values", requestValidator.getValidatedValues());
                model.put("loginEmpty", false);
                return buildModelAndView("register/register");
            }
        } else {
            return getViewWithErrors("errors", requestValidator.getErrorMessageMap(), requestValidator.getValidatedValues());
        }
    }

    private ModelAndView getViewWithErrors(String key, Object error, Object values){
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put(key, error);
        model.put("values", values);
        return buildModelAndView("register/register");
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
