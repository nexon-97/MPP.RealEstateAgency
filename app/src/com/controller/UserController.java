package com.controller;

import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.services.AuthServiceImpl;
import com.services.RegisterService;
import com.services.shared.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            List<Property> userProperties = serviceManager.getPropertyService().getPropertiesOwnedByUser(loggedUser);
            List<Offer> userOffers = serviceManager.getOfferService().getUserOffers(loggedUser);

            model.put("userProperties", userProperties);
            model.put("userOffers", userOffers);
        }
        return buildModelAndView("user_roles");
    }
}
