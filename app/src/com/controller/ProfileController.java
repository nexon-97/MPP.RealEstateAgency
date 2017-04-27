package com.controller;

import com.model.Property;
import com.model.User;
import com.services.shared.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Controller
public class ProfileController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public ModelAndView showProfile(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        ServiceManager serviceManager = ServiceManager.getInstance();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            List<Property> userProperties = serviceManager.getPropertyService().getPropertiesOwnedByUser(loggedUser);
            model.put("userProperties", userProperties);
        }

        return buildModelAndView("profile");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profileEdit")
    public ModelAndView showProfileEditorPage(HttpServletResponse response) {
        initControllerResources(context, request, response);

        return buildModelAndView("edit_profile");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profileEdit")
    public ModelAndView commitProfileEditionPage(HttpServletResponse response) {
        initControllerResources(context, request, response);

        Map<String, String[]> parameters = request.getParameterMap();
        String[] parametersToFetch = new String[] { "surname", "name", "patronymic", "email", "phone", "info" };

        Map<String, String> parsedRequestParams = new HashMap<>();
        boolean parsingSucceeded = true;
        for (String param : parametersToFetch) {
            if (!validateRequestParameter(parameters, parsedRequestParams, param)) {
                parsingSucceeded = false;
                break;
            }
        }

        if (parsingSucceeded) {
            User updatedUser = refreshUserData(parsedRequestParams);
            ServiceManager.getInstance().getUserService().updateUser(updatedUser);
        } else {
            ServiceManager.getInstance().getSharedResources().getModel().put("msg", "Broken profile edition request");
            return buildModelAndView("../error_message");
        }

        return buildModelAndView("profile");
    }

    private boolean validateRequestParameter(Map<String, String[]> parameters, Map<String, String> parsedParams, String paramName) {
        String[] value = parameters.getOrDefault(paramName, null);
        if (value != null) {
            parsedParams.put(paramName, value[0]);
            return true;
        }

        return false;
    }

    private User refreshUserData(Map<String, String> dataMap) {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        if (loggedUser != null) {
            loggedUser.setSurname(dataMap.get("surname"));
            loggedUser.setName(dataMap.get("name"));
            loggedUser.setPatronymic(dataMap.get("patronymic"));
            loggedUser.setEmail(dataMap.get("email"));
            loggedUser.setPhone(dataMap.get("phone").length() > 0 ? dataMap.get("phone") : null);
            loggedUser.setInfo(dataMap.get("info").length() > 0 ? dataMap.get("info") : null);
        }

        return loggedUser;
    }
}
