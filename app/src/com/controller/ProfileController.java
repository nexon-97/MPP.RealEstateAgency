package com.controller;

import com.model.*;
import com.services.DealRequestService;
import com.services.DealService;
import com.services.shared.ServiceManager;
import com.utils.request.validator.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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
            model.put("userProperties", userProperties);
            model.put("userOffers", userOffers);

            DealRequestService dealRequestService = serviceManager.getDealRequestService();
            DealService dealService = serviceManager.getDealService();
            if (loggedUser.getRoleId().equals(RoleId.Rieltor)) {
                List<DealRequest> uncommitedRequests = dealRequestService.listUncommittedRealtorRequests(loggedUser);
                model.put("uncommittedRealtorRequests", uncommitedRequests);
            } else if (loggedUser.getRoleId().equals(RoleId.Broker)) {
                List<Deal> unsignedDeals = dealService.listUnsignedDeals();
                List<Deal> dealHistory = dealService.listBrokerDeals(loggedUser);
                model.put("unsignedDeals", unsignedDeals);
                model.put("dealHistory", dealHistory);
            } else if (loggedUser.getRoleId().equals(RoleId.User)) {
                List<DealRequest> uncommitedRequests = dealRequestService.listUncommittedSellerRequests(loggedUser);
                model.put("uncommittedRealtorRequests", uncommitedRequests);
            }

            return buildModelAndView("profile");
        }

        return showUnauthorizedMessageView();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profileEdit")
    public ModelAndView showProfileEditorPage(HttpServletResponse response) {
        initControllerResources(response);

        if (ServiceManager.getInstance().getAuthService().isUserLoggedIn())
        {
            return buildModelAndView("edit_profile");
        } else {
            return showUnauthorizedMessageView();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profileEdit")
    public ModelAndView commitProfileEditionPage(HttpServletResponse response) {
        initControllerResources(response);

        if (!ServiceManager.getInstance().getAuthService().isUserLoggedIn()) {
            return showUnauthorizedMessageView();
        }

        RequestValidationChain requestValidationChain = buildProfileEditDataValidator();
        if (requestValidationChain.validate()){
            User updatedUser = refreshUserData(requestValidationChain.getValidatedValues());
            boolean updateSuccess = ServiceManager.getInstance().getUserService().updateUser(updatedUser);

            if (updateSuccess) {
                return redirect("/profile");
            }
        }

        putErrorMessagesMap(requestValidationChain.getErrorMessageMap());
        return buildModelAndView(HttpServletResponse.SC_BAD_REQUEST, "edit_profile");
    }

    private RequestValidationChain buildProfileEditDataValidator(){
        return new RequestValidationChain()
                .addValidator(new FullNameStringParameterValidator("name", false))
                .addValidator(new FullNameStringParameterValidator("surname", false))
                .addValidator(new FullNameStringParameterValidator("patronymic", false))
                .addValidator(new EmailStringParameterValidator("email", false))
                .addValidator(new PhoneStringParameterValidator("phone", false))
                .addValidator(new StringParameterValidator("info", true));

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

    private void putErrorMessagesMap(Map<String, String> errorMessagesMap) {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("errors", errorMessagesMap);
    }
}
