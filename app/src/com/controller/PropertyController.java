package com.controller;

import com.model.Property;
import com.model.PropertyType;
import com.services.PropertyService;
import com.services.shared.ServiceManager;
import com.utils.request.BooleanParameter;
import com.utils.request.FilterParameter;
import com.utils.request.IntegerRangeParameter;
import com.utils.request.PropertyFilterParamId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PropertyController extends BaseController  {

    @RequestMapping(method = RequestMethod.GET, value = "/property")
    public ModelAndView showPropertyInfo(HttpServletResponse response) {
        initControllerResources(context, request, response);
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
        Property property = propertyService.getPropertyById(propertyId);

        if (property == null) {
            model.put("msg", "Can't find requested property!");
            return buildModelAndView("../error_message");
        }
        else
        {
            model.put("property", property);
        }

        return buildModelAndView("property");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addProperty")
    public ModelAndView visitAddPropertyForm(HttpServletResponse response) {
        initControllerResources(context, request, response);
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
        initControllerResources(context, request, response);
        if (ServiceManager.getInstance().getAuthService().getLoggedUser() == null) {
            return buildModelAndView("../unauthorized_view");
        } else {
            PropertyService propertyService = ServiceManager.getInstance().getPropertyService();
            boolean isRegisterCorrect = propertyService.addProperty(request.getParameterMap());
            if (isRegisterCorrect){
                return redirect("/");}
            else {
                return buildModelAndView("addProperty");
            }
        }
    }
}
