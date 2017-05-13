package com.controller;

import com.model.Offer;
import com.model.Property;
import com.model.RoleId;
import com.model.User;
import com.services.*;
import com.services.shared.ServiceManager;
import com.utils.request.validator.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/user_roles")
    public ModelAndView showUserRoles(HttpServletResponse response) {
        initResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        ServiceManager serviceManager = ServiceManager.getInstance();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            if (loggedUser.getRoleId().equals(RoleId.Admin)){
                int minUserId = getMinUserIdFromRequest();
                List<User> users = ServiceManager.getInstance().getUserService().listUsersRange(minUserId, 30);
                RoleId[] roles = RoleId.values();
                model.put("roles", roles);
                model.put("userList", users);
            } else {
                return showErrorMessage("Вы не администратор!");
            }
        }

        return buildModelAndView("user_roles");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user_roles")
    public ModelAndView changeUserRole(HttpServletResponse response) {
        initResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        ServiceManager serviceManager = ServiceManager.getInstance();
        HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            if (loggedUser.getRoleId().equals(RoleId.Admin)){

                RequestValidationChain roleValidationChain = buildRoleValidationChain();
                if (roleValidationChain.validate()){
                    UserService userService = ServiceManager.getInstance().getUserService();
                    User user = userService.getUserByLogin((String)roleValidationChain.getValue("user_login"));
                    RoleId oldRole = user.getRoleId();
                    user.setRoleId((RoleId)roleValidationChain.getValue("user_role"));
                    if (!user.equals(loggedUser)) {
                            if (userService.update(user)){
                                model.put("success", "Роль пользователя "+user.getLogin()+" сменилась с "+oldRole+" на "+user.getRoleId());
                            }
                    } else {
                        model.put("error", "Вы не можете изменить роль самому себе!");
                    }
                }


                int minUserId = getMinUserIdFromRequest();
                List<User> users = ServiceManager.getInstance().getUserService().listUsersRange(minUserId, 30);
                RoleId[] roles = RoleId.values();
                model.put("roles", roles);
                model.put("userList", users);
            } else {
                return showErrorMessage("Вы не администратор!");
            }
        }

        return buildModelAndView("user_roles");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ModelAndView showUserProfilePage(HttpServletResponse response) {
        initResources(response);

        Integer userId = getIdFromRequest();
        if (userId != null) {
                UserService userService = ServiceManager.getInstance().getUserService();
                User requestedUser = userService.get(userId);

                User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();

                if (Objects.equals(loggedUser, requestedUser)) {
                    return redirect("/profile");
                } else {
                    ServiceManager serviceManager = ServiceManager.getInstance();
                    List<Property> userProperties = serviceManager.getPropertyService().listUserOwnedProperties(requestedUser);
                    List<Offer> userOffers = serviceManager.getOfferService().getUserOffers(requestedUser);

                    Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
                    model.put("selectedUser", requestedUser);
                    model.put("userProperties", userProperties);
                    model.put("userOffers", userOffers);

                    return buildModelAndView("user");
                }
        }

        return showBadRequestView("Такого пользователя не существует!");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/team")
    public ModelAndView showTeamPage(HttpServletResponse response) {
        initResources(response);

        UserService userService = ServiceManager.getInstance().getUserService();
        List<User> adminsList = userService.getUsersByRole(RoleId.Admin);
        List<User> realtorsList = userService.getUsersByRole(RoleId.Rieltor);
        List<User> brokersList = userService.getUsersByRole(RoleId.Broker);

        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("adminsList", adminsList);
        model.put("realtorsList", realtorsList);
        model.put("brokersList", brokersList);

        return buildModelAndView("team");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public ModelAndView visitRegistrationForm(HttpServletResponse response) {
        initResources(response);
        if (ServiceManager.getInstance().getAuthService().getLoggedUser() != null){
            return buildModelAndView("register/register_logged");
        }
        else {
            return buildModelAndView("register/register");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ModelAndView register(HttpServletResponse response) {
        initResources(response);
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

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    public ModelAndView visitAuthorizationForm(HttpServletResponse response) {
        initResources(response);

        AuthService authService = ServiceManager.getInstance().getAuthService();
        if (authService.getLoggedUser() != null) {
            return redirect("/");
        } else {
            return buildModelAndView("auth");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth")
    public ModelAndView authorize(HttpServletResponse response) {
        initResources(response);

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login != null && password != null) {
            AuthService authService = ServiceManager.getInstance().getAuthService();
            authService.login(login, password);
        }

        return buildModelAndView("auth_result");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ModelAndView logout(HttpServletResponse response) {
        initResources(response);

        ServiceManager.getInstance().getAuthService().logout();

        return redirect("/");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/restorePassword")
    public ModelAndView restorePassword(HttpServletResponse response) {
        initResources(response);
        if (ServiceManager.getInstance().getAuthService().getLoggedUser() == null){
            return buildModelAndView("restore_pass");
        }
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", "Для восстановления пароля необходимо выйти из аккаунта!");
        return buildModelAndView("restore_pass_failure");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/restorePassword")
    public ModelAndView sendEmail(HttpServletResponse response) {
        initResources(response);
        String loginFromRequest = request.getParameter("login");
        User userForRestorePass = ServiceManager.getInstance().getUserService().getUserByLogin(loginFromRequest);
        if ( userForRestorePass == null){
            Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
            model.put("msg", "Пользователя с таким логином не существует!");
            return buildModelAndView("restore_pass_failure");
        }
        String email = userForRestorePass.getEmail();
        //sendMail(email);
        return buildModelAndView("restore_pass_success");
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

    private int getMinUserIdFromRequest(){
        int userId;
        try {
            userId = Integer.valueOf(request.getParameter("from"));
            if (userId < 0) {
                userId = 0;
            }
        } catch (NullPointerException | NumberFormatException e) {
            userId = 0;
        }
        return userId;
    }

    private RequestValidationChain buildRoleValidationChain(){
        return new RequestValidationChain()
                .addValidator(new EnumParameterValidator<>(RoleId.class, "user_role", false))
                .addValidator(new LoginStringParameterValidator("user_login", false));
    }
}
