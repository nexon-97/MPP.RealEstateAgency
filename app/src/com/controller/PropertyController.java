package com.controller;

import com.helper.SystemMessages;
import com.model.Property;
import com.model.PropertyType;
import com.services.PropertyService;
import com.services.shared.ServiceManager;
import com.utils.request.validator.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class PropertyController extends BaseController  {

    @RequestMapping(method = RequestMethod.GET, value = "/property")
    public ModelAndView showPropertyInfo(HttpServletResponse response) {
        initResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        // Retrieve property id from request
        int propertyId = -1;
        try {
            propertyId = Integer.valueOf(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e) {
            model.put("msg", "Invalid property id!");
            return buildModelAndView("../error_message");
        }

        // Load property
        PropertyService propertyService = ServiceManager.getInstance().getPropertyService();
        Property property = propertyService.get(propertyId);

        model.put("property", property);
        return buildModelAndView("property");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addProperty")
    public ModelAndView visitAddPropertyForm(HttpServletResponse response) {
        initResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        if (ServiceManager.getInstance().getAuthService().getLoggedUser() != null) {
            PropertyService propService = ServiceManager.getInstance().getPropertyService();
            PropertyType[] types = PropertyType.values();
            model.put("types", types);

            return buildModelAndView("addProperty");
        } else {
            return buildModelAndView("../unauthorized_view");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addProperty")
    public ModelAndView register(HttpServletResponse response) {
        initResources(response);

        if (!ServiceManager.getInstance().getAuthService().isUserLoggedIn()) {
            return showUnauthorizedMessageView();
        } else {
            RequestValidationChain requestValidator = buildPropertyValidationChain();
            if (requestValidator.validate()){
                PropertyService propertyService = ServiceManager.getInstance().getPropertyService();
                boolean isAdded = propertyService.add(requestValidator);
                if (isAdded) {
                    return redirect("/profile");
                } else {
                    return getViewWithErrors("addError", "Ошибка при добавлении собственности", requestValidator.getValidatedValues());
                }
            } else {
                return getViewWithErrors("errors", requestValidator.getErrorMessageMap(), requestValidator.getValidatedValues());
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteProperty")
    public ModelAndView deleteOfferAction(HttpServletResponse response) {
        initResources(response);

        Integer propertyId = getIdFromRequest();
        if (propertyId != null) {
                PropertyService propertyService = ServiceManager.getInstance().getPropertyService();
                Property property = propertyService.get(propertyId);
                propertyService.delete(property);

                return redirect("/profile");
        }

        return showErrorMessage(SystemMessages.NoSuchPropertyMessage);
    }

    private RequestValidationChain buildPropertyValidationChain() {
        return new RequestValidationChain()
                .addValidator(new EnumParameterValidator<>(PropertyType.class, "type", false))
                .addValidator(new PropertyStringParameterValidator("city",  false))
                .addValidator(new PropertyStringParameterValidator("street", false))
                .addValidator(new IntegerParameterValidator("houseNumber", false))
                .addValidator(new IntegerParameterValidator("blockNumber", true))
                .addValidator(new IntegerParameterValidator("flatNumber", true))
                .addValidator(new IntegerParameterValidator("roomsCount", true))
                .addValidator(new IntegerParameterValidator("area", false))
                .addValidator(new IntegerParameterValidator("subway", true))
                .addValidator(new IntegerParameterValidator("bus",true))
                .addValidator(new BooleanParameterValidator("furniture"))
                .addValidator(new BooleanParameterValidator("internet"))
                .addValidator(new BooleanParameterValidator("tv"))
                .addValidator(new BooleanParameterValidator("phone"))
                .addValidator(new BooleanParameterValidator("fridge"))
                .addValidator(new BooleanParameterValidator("stove"))
                .addValidator(new StringParameterValidator("description", false));
    }

    private ModelAndView getViewWithErrors(String key, Object error, Object values) {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        PropertyType[] types = PropertyType.values();
        model.put(key, error);
        model.put("values", values);
        model.put("types", types);
        return buildModelAndView("addProperty");
    }
}
