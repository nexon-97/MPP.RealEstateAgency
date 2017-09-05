package com.controller;

import com.exception.GenericException;
import com.model.Offer;
import com.model.Property;
import com.model.RoleId;
import com.model.User;
import com.security.annotations.RoleCheck;
import com.services.OfferService;
import com.services.PropertyService;
import com.services.UserService;
import com.utils.request.validator.EnumParameterValidator;
import com.utils.request.validator.LoginStringParameterValidator;
import com.utils.request.validator.RequestValidationChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    OfferService offerService;

    @GetMapping(value = "/user_roles")
    @RoleCheck(RoleId.Admin)
    public String showUserRoles(@RequestParam(value = "from", required = false, defaultValue = "0") Integer minUserId, Model model) {
        List<User> users = userService.getSeveralUsers(minUserId, 30);
        RoleId[] roles = RoleId.values();
        model.addAttribute("roles", roles);
        model.addAttribute("userList", users);

        return "user/user_roles";
    }

    @PostMapping(value = "/user_roles")
    @RoleCheck(RoleId.Admin)
    public String changeUserRole(Model model) {
        RequestValidationChain roleValidationChain = buildRoleValidationChain();
        if (roleValidationChain.validate(request)) {
            User user = userService.getUserByLogin((String)roleValidationChain.getValue("user_login"));
            RoleId oldRole = user.getRoleId();
            user.setRoleId((RoleId)roleValidationChain.getValue("user_role"));

            User loggedUser = authService.getLoggedUser();
            if (!user.equals(loggedUser)) {
                if (userService.updateUser(user)) {
                    model.addAttribute("success", "Роль пользователя " + user.getLogin() + " сменилась с " + oldRole + " на " + user.getRoleId());
                } else {
                    throw new GenericException("Произошла ошибка при изменении роли пользователя " + user.getLogin());
                }
            } else {
                throw new GenericException("Вы не можете изменить роль самому себе!");
            }
        }

        return redirectToReferer();
    }

    @GetMapping(value = "/user")
    public String showUserProfilePage(@RequestParam("id") int userId, Model model) {
        User requestedUser = userService.getUserByID(userId);
        if (requestedUser != null) {
            User loggedUser = authService.getLoggedUser();

            if (Objects.equals(loggedUser, requestedUser)) {
                return redirect("/profile");
            } else {
                List<Property> userProperties = propertyService.getPropertiesOwnedByUser(requestedUser);
                List<Offer> userOffers = offerService.getUserOffers(requestedUser);

                model.addAttribute("selectedUser", requestedUser);
                model.addAttribute("userProperties", userProperties);
                model.addAttribute("userOffers", userOffers);

                return "user/user";
            }
        }

        throw new GenericException("Такого пользователя не существует!");
    }

    @GetMapping(value = "/team")
    public String showTeamPage(Model model) {
        List<User> adminsList = userService.getUsersByRole(RoleId.Admin);
        List<User> realtorsList = userService.getUsersByRole(RoleId.Realtor);
        List<User> brokersList = userService.getUsersByRole(RoleId.Broker);

        model.addAttribute("adminsList", adminsList);
        model.addAttribute("realtorsList", realtorsList);
        model.addAttribute("brokersList", brokersList);

        return "user/team";
    }

    private RequestValidationChain buildRoleValidationChain(){
        return new RequestValidationChain()
                .addValidator(new EnumParameterValidator<>(RoleId.class, "user_role", false))
                .addValidator(new LoginStringParameterValidator("user_login", false));
    }
}
