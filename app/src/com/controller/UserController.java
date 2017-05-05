package com.controller;

import com.model.Offer;
import com.model.Property;
import com.model.RoleId;
import com.model.User;
import com.services.*;
import com.services.shared.PermissionId;
import com.services.shared.ServiceManager;
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

@Controller
public class UserController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/user_roles")
    public ModelAndView visitRegistrationForm(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        ServiceManager serviceManager = ServiceManager.getInstance();
        Map<String, String[]> map = request.getParameterMap();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {

            PermissionService permissionService = ServiceManager.getInstance().getPermissionService();
            if (permissionService.hasPermission(loggedUser.getRoleId(), permissionService.getPermissionById(PermissionId.UpdateRole))){
                int minUserId = getMinUserIdFromRequest();
                List<User> users = ServiceManager.getInstance().getUserService().getSeveralUsers( minUserId, 30);
                RoleId[] roles = RoleId.values();
                model.put("roles", roles);

                model.put("userList", users);
            }

        }

        return buildModelAndView("user_roles");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user_roles")
    public ModelAndView changeUserRole(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        ServiceManager serviceManager = ServiceManager.getInstance();
        HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {

            PermissionService permissionService = ServiceManager.getInstance().getPermissionService();
            if (permissionService.hasPermission(loggedUser.getRoleId(), permissionService.getPermissionById(PermissionId.UpdateRole))){

                RequestValidationChain roleValidationChain = buildRoleValidationChain();
                if (roleValidationChain.validate()){
                    UserService userService = ServiceManager.getInstance().getUserService();
                    User user = userService.getUserByLogin((String)roleValidationChain.getValue("user_login"));
                    user.setRoleId((RoleId)roleValidationChain.getValue("user_role"));
                    userService.updateUser(user);
                }

                int minUserId = getMinUserIdFromRequest();
                List<User> users = ServiceManager.getInstance().getUserService().getSeveralUsers( minUserId, 30);
                RoleId[] roles = RoleId.values();
                model.put("roles", roles);
                model.put("userList", users);
            }
        }

        return buildModelAndView("user_roles");
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
                .addValidator(new EnumParameterValidator<>(RoleId.class, "user_role"))
                .addValidator(new LoginStringParameterValidator("user_login", false));
    }
}
