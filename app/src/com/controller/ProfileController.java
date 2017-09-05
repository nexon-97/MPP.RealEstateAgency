package com.controller;

import com.model.*;
import com.security.annotations.AuthCheck;
import com.services.*;
import com.utils.request.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController extends BaseController {

    @Autowired
    AuthService authService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    OfferService offerService;

    @Autowired
    UserService userService;

    @Autowired
    DealService dealService;

    @Autowired
    DealRequestService dealRequestService;

    @GetMapping(value = "/profile")
    @AuthCheck
    public String showProfile(Model model) {
        User loggedUser = authService.getLoggedUser();
        List<Property> userProperties = propertyService.getPropertiesOwnedByUser(loggedUser);
        List<Offer> userOffers = offerService.getUserOffers(loggedUser);

        model.addAttribute("userProperties", userProperties);
        model.addAttribute("userOffers", userOffers);

        if (loggedUser.getRoleId().equals(RoleId.Rieltor)) {
            List<DealRequest> uncommitedRequests = dealRequestService.listUncommittedRealtorRequests(loggedUser);
            model.addAttribute("uncommittedRealtorRequests", uncommitedRequests);
        } else if (loggedUser.getRoleId().equals(RoleId.Broker)) {
            List<Deal> unsignedDeals = dealService.listUnsignedDeals();
            List<Deal> dealHistory = dealService.listBrokerDeals(loggedUser);
            model.addAttribute("unsignedDeals", unsignedDeals);
            model.addAttribute("dealHistory", dealHistory);
        } else if (loggedUser.getRoleId().equals(RoleId.User)) {
            List<DealRequest> uncommitedRequests = dealRequestService.listUncommittedSellerRequests(loggedUser);
            model.addAttribute("uncommittedRealtorRequests", uncommitedRequests);
        }

        return "profile/profile";
    }

    @GetMapping(value = "/profileEdit")
    @AuthCheck
    public String showProfileEditorPage() {
        return "profile/edit_profile";
    }

    @PostMapping(value = "/profileEdit")
    @AuthCheck
    public String commitProfileEditionPage(HttpServletRequest request, Model model) {
        RequestValidationChain requestValidationChain = buildProfileEditDataValidator();
        if (requestValidationChain.validate(request)) {
            User updatedUser = refreshUserData(requestValidationChain.getValidatedValues());
            boolean updateSuccess = userService.updateUser(updatedUser);

            if (updateSuccess) {
                return redirect("profile");
            }
        }

        model.addAttribute("errors", requestValidationChain.getErrorMessageMap());
        return "profile/edit_profile";
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
        User loggedUser = authService.getLoggedUser();
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
