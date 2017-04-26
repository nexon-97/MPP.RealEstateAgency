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

    private interface ComfortsPutOperation {
        void put(String name);
    }

    private final String[] comfortsNames = {
        "furniture", "tv", "internet", "fridge", "stove", "phone"
    };

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

    @RequestMapping(method = RequestMethod.GET, value = "/propertyFilter")
    public ModelAndView showFilter(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();



        return buildModelAndView("property_filter");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/propertyFilter")
    public ModelAndView filterProperties(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        // Parse filter params
        Map<PropertyFilterParamId, FilterParameter> filterParams = new HashMap<>();
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.Area, "areaMin", "areaMax");
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.RoomCount, "roomCountMin", "roomCountMax");
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.DistanceToTransportStop, "distanceToTransportStopMin", "distanceToTransportStopMax");
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.DistanceToSubway, "distanceToSubwayMin", "distanceToSubwayMax");
        fillComfortsParams(filterParams);

        // Use service to get matching properties
        PropertyService propertyService = ServiceManager.getInstance().getPropertyService();
        List<Property> filteredProperties = propertyService.filterProperties(filterParams);
        model.put("properties", filteredProperties);

        // Put filter params to preserve form inputs
        Map<String, String> filterParamsModel = constructFilterParametersModel();
        Map<String, Boolean> comfortsParamsModel = constructComfortsParametersModel();
        model.put("filterParams", filterParamsModel);
        model.put("comfortsParams", comfortsParamsModel);

        return buildModelAndView("property_filter");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addProperty")
    public ModelAndView visitAddPropertyForm(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        System.out.println("In property controller get");

        PropertyService propService = ServiceManager.getInstance().getPropertyService();
        PropertyType[] types = PropertyType.values();
        model.put("types", types);
        return buildModelAndView("addProperty");
    }

    private Map<String, String> constructFilterParametersModel() {
        String[] filterParamsNames = new String[]{
                "costMin", "costMax",
                "areaMin", "areaMax",
                "roomCountMin", "roomCountMax",
                "distanceToTransportStopMin", "distanceToTransportStopMax",
                "distanceToSubwayMin", "distanceToSubwayMax"
        };

        Map<String, String> filterParamsModel = new HashMap<>();
        for (String paramName : filterParamsNames) {
            filterParamsModel.put(paramName, request.getParameter(paramName));
        }

        return filterParamsModel;
    }

    private Map<String, Boolean> constructComfortsParametersModel() {
        final Map<String, Boolean> comfortsParamsModel = new HashMap<>();

        final String[] comforts = request.getParameterValues("comforts");
        if (comforts != null) {
            ComfortsPutOperation putOperation = (name) -> comfortsParamsModel.put(name, Arrays.asList(comforts).contains(name));

            for (String name : comfortsNames) {
                putOperation.put(name);
            }
        }

        return comfortsParamsModel;
    }

    private void fillComfortsParams(Map<PropertyFilterParamId, FilterParameter> params) {
        String collectionName = "comforts";

        addBooleanRangeFilterParam(params, PropertyFilterParamId.HasFurniture, collectionName, "furniture");
        addBooleanRangeFilterParam(params, PropertyFilterParamId.HasTv, collectionName, "tv");
        addBooleanRangeFilterParam(params, PropertyFilterParamId.HasInternet, collectionName, "internet");
        addBooleanRangeFilterParam(params, PropertyFilterParamId.HasFridge, collectionName, "fridge");
        addBooleanRangeFilterParam(params, PropertyFilterParamId.HasPhone, collectionName, "phone");
        addBooleanRangeFilterParam(params, PropertyFilterParamId.HasStove, collectionName, "stove");
    }

    private void addIntegerRangeFilterParam(Map<PropertyFilterParamId, FilterParameter> params, PropertyFilterParamId paramId, String min, String max) {
        params.put(paramId, new IntegerRangeParameter(request.getParameter(min), request.getParameter(max), paramId));
    }

    private void addBooleanRangeFilterParam(Map<PropertyFilterParamId, FilterParameter> params, PropertyFilterParamId paramId, String collection, String name) {
        String[] paramValues = request.getParameterValues(collection);
        boolean contain = (paramValues != null) && Arrays.asList(paramValues).contains(name);
        params.put(paramId, new BooleanParameter(paramId, contain));
    }
}
