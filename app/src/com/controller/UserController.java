package com.controller;

import com.model.Offer;
import com.model.Property;
import com.model.RoleId;
import com.model.User;
import com.services.*;
import com.utils.request.validator.EnumParameterValidator;
import com.utils.request.validator.LoginStringParameterValidator;
import com.utils.request.validator.RequestValidationChain;
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
    public ModelAndView visitRegistrationForm(HttpServletResponse response) {
        /*initControllerResources(response);

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            if (loggedUser.getRoleId().equals(RoleId.Admin)){
                int minUserId = getMinUserIdFromRequest();
                List<User> users = ServiceManager.getInstance().getUserService().getSeveralUsers( minUserId, 30);
                RoleId[] roles = RoleId.values();
                model.put("roles", roles);
                model.put("userList", users);
            } else {
                return showErrorMessage("Вы не администратор!");
            }
        }

        return buildModelAndView("user_roles");*/
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user_roles")
    public ModelAndView changeUserRole(HttpServletResponse response) {
        /*initControllerResources(response);
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
                        if (userService.updateUser(user)){
                            model.put("success", "Роль пользователя "+user.getLogin()+" сменилась с "+oldRole+" на "+user.getRoleId());
                        } else {
                            model.put("error", "Произошла ошибка при изменении роли пользователя "+user.getLogin());
                        }
                    } else {
                        model.put("error", "Вы не можете изменить роль самому себе!");
                    }
                }


                int minUserId = getMinUserIdFromRequest();
                List<User> users = ServiceManager.getInstance().getUserService().getSeveralUsers( minUserId, 30);
                RoleId[] roles = RoleId.values();
                model.put("roles", roles);
                model.put("userList", users);
            } else {
                return showErrorMessage("Вы не администратор!");
            }
        }

        return buildModelAndView("user_roles");*/

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ModelAndView showUserProfilePage(HttpServletResponse response) {
        /*initControllerResources(response);

        Integer userId = getIdFromRequest();
        if (userId != null) {
            UserService userService = ServiceManager.getInstance().getUserService();
            User requestedUser = userService.getUserByID(userId);

            if (requestedUser != null) {
                User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();

                if (Objects.equals(loggedUser, requestedUser)) {
                    return redirect("/profile");
                } else {
                    ServiceManager serviceManager = ServiceManager.getInstance();
                    List<Property> userProperties = serviceManager.getPropertyService().getPropertiesOwnedByUser(requestedUser);
                    List<Offer> userOffers = serviceManager.getOfferService().getUserOffers(requestedUser);

                    Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
                    model.put("selectedUser", requestedUser);
                    model.put("userProperties", userProperties);
                    model.put("userOffers", userOffers);

                    return buildModelAndView("user");
                }
            }
        }

        return showBadRequestView("Такого пользователя не существует!");*/

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/team")
    public ModelAndView showTeamPage(HttpServletResponse response) {
        /*initControllerResources(response);

        UserService userService = ServiceManager.getInstance().getUserService();
        List<User> adminsList = userService.getUsersByRole(RoleId.Admin);
        List<User> realtorsList = userService.getUsersByRole(RoleId.Rieltor);
        List<User> brokersList = userService.getUsersByRole(RoleId.Broker);

        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("adminsList", adminsList);
        model.put("realtorsList", realtorsList);
        model.put("brokersList", brokersList);

        return buildModelAndView("team");*/

        return null;
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
