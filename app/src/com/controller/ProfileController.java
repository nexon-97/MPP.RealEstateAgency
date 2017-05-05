package com.controller;

import com.model.*;
import com.services.DealService;
import com.services.UserServiceImpl;
import com.services.shared.ServiceManager;
import com.utils.request.validator.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public ModelAndView showProfile(HttpServletResponse response) {
        initControllerResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        ServiceManager serviceManager = ServiceManager.getInstance();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            List<Property> userProperties = serviceManager.getPropertyService().getPropertiesOwnedByUser(loggedUser);
            List<Offer> userOffers = serviceManager.getOfferService().getUserOffers(loggedUser);
            model.put("profileOwner", loggedUser);
            model.put("ownProfile", true);
            model.put("userProperties", userProperties);
            model.put("userOffers", userOffers);

            DealService dealService = serviceManager.getDealService();
            if (loggedUser.getRoleId().equals(RoleId.Rieltor)) {
                List<DealRequest> uncommitedRequests = dealService.listUncommittedRealtorRequests(loggedUser);
                model.put("uncommittedRealtorRequests", uncommitedRequests);
            } else if (loggedUser.getRoleId().equals(RoleId.User)) {
                List<DealRequest> uncommitedRequests = dealService.listUncommittedSellerRequests(loggedUser);
                model.put("uncommittedRealtorRequests", uncommitedRequests);
            }
        }

        return buildModelAndView("profile");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profileEdit")
    public ModelAndView showProfileEditorPage(HttpServletResponse response) {
        initControllerResources(response);
        return buildModelAndView("edit_profile");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profileEdit")
    public ModelAndView commitProfileEditionPage(HttpServletResponse response) {
        initControllerResources(response);
        RequestValidationChain requestValidationChain = buildProfileEditDataValidator();

        if (requestValidationChain.validate()){
            User updatedUser = refreshUserData(requestValidationChain.getValidatedValues());
            ServiceManager.getInstance().getUserService().updateUser(updatedUser);
        } else {
            ServiceManager.getInstance().getSharedResources().getModel().put("msg", "Broken profile edition request");
            return buildModelAndView("../error_message");
        }
        return buildModelAndView("profile");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ModelAndView showUserProfilePage(HttpServletResponse response) {
        initControllerResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        Integer idFromRequest = getIdFromRequest();
        if (idFromRequest != null){

            UserServiceImpl userService = new UserServiceImpl(ServiceManager.getInstance().getSharedResources());
            User requestedUser = userService.getUserByID(idFromRequest);
            if (requestedUser == null){
                ServiceManager.getInstance().getSharedResources().getModel().put("msg", "Пользователя с таким id не существует");
                return buildModelAndView("../error_message");
            }
            User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
            List<Property> userProperties;
            List<Offer> userOffers;
            if ((loggedUser != null) && loggedUser.getId() == requestedUser.getId()){
                model.put("profileOwner", loggedUser);
                model.put("ownProfile", true);
                userProperties = ServiceManager.getInstance().getPropertyService().getPropertiesOwnedByUser(loggedUser);
                userOffers = ServiceManager.getInstance().getOfferService().getUserOffers(loggedUser);
            } else {
                model.put("profileOwner", requestedUser);
                userProperties = ServiceManager.getInstance().getPropertyService().getPropertiesOwnedByUser(requestedUser);
                userOffers = ServiceManager.getInstance().getOfferService().getUserOffers(requestedUser);
            }
            model.put("userProperties", userProperties);
            model.put("userOffers", userOffers);
            return buildModelAndView("profile");
        } else {
            ServiceManager.getInstance().getSharedResources().getModel().put("msg", "Некорректный id.");
            return buildModelAndView("../error_message");
        }
    }


    private RequestValidationChain buildProfileEditDataValidator(){
        return new RequestValidationChain()
                .addValidator(new FullNameStringParameterValidator("name", false))
                .addValidator(new FullNameStringParameterValidator("surname", false))
                .addValidator(new FullNameStringParameterValidator("patronymic", false))
                .addValidator(new EmailStringParameterValidator("email", false))
                .addValidator(new PhoneStringParameterValidator("phone", false))
                .addValidator(new DescriptionStringParameterValidator("info", false));

    }

    private User refreshUserData(Map<String, Object> dataMap) {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        if (loggedUser != null) {
            loggedUser.setSurname((String)dataMap.get("surname"));
            loggedUser.setName((String)dataMap.get("name"));
            loggedUser.setPatronymic((String)dataMap.get("patronymic"));
            loggedUser.setEmail((String)dataMap.get("email"));
            loggedUser.setPhone(((String)dataMap.get("phone")).length() > 0 ? (String)dataMap.get("phone") : null);
            loggedUser.setInfo(((String)dataMap.get("info")).length() > 0 ? (String)dataMap.get("info") : null);
        }
        return loggedUser;
    }
}
