package com.controller;

import com.model.*;
import com.services.OfferService;
import com.services.PropertyService;
import com.services.shared.ServiceManager;
import com.utils.request.*;
import com.utils.request.validator.BigDecimalParameterValidator;
import com.utils.request.validator.EnumParameterValidator;
import com.utils.request.validator.IntegerParameterValidator;
import com.utils.request.validator.RequestValidationChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class OfferController extends BaseController {

    private RequestValidationChain offerValidationChain;

    private interface ComfortsPutOperation {
        void put(String name);
    }

    private final String[] comfortsNames = {
            "furniture", "tv", "internet", "fridge", "stove", "phone"
    };

    public OfferController() {
        this.offerValidationChain = buildOfferValidationChain();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/offer")
    public ModelAndView showOffer(HttpServletResponse response) {
        initControllerResources(context, request, response);

        Integer id;
        try {
            id = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            id = null;
        }

        if (id != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            Offer offer = offerService.getOfferById(id);

            Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
            model.put("offer", offer);
        }

        return buildModelAndView("offer_view");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addOffer")
    public ModelAndView showAddOfferView(HttpServletResponse response) {
        initControllerResources(context, request, response);
        ServiceManager serviceManager = ServiceManager.getInstance();
        Map<String, Object> model = serviceManager.getSharedResources().getModel();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            PropertyService propertyService = serviceManager.getPropertyService();
            List<Property> userProperties = propertyService.getPropertiesOwnedByUser(loggedUser);
            model.put("userProperties", userProperties);

            OfferType[] offerTypes = OfferType.values();
            model.put("offerTypes", offerTypes);
        }

        return buildModelAndView("add_offer_view");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addOffer")
    public ModelAndView addOfferAction(HttpServletResponse response) {
        initControllerResources(context, request, response);

        Offer offer = constructOfferFromRequest();
        if (offer != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            if (offerService.addOffer(offer)) {
                return redirect("/offer?id=" + String.valueOf(offer.getId()));
            }
        } else {
            return showErrorMessage(offerValidationChain.getErrorMessageMap().getOrDefault("","Some error"));
        }

        return showErrorMessage("Не удалось добавить предложение! Проверьте правильность параметров!");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/editOffer")
    public ModelAndView showEditOfferView(HttpServletResponse response) {
        initControllerResources(context, request, response);
        ServiceManager serviceManager = ServiceManager.getInstance();
        Map<String, Object> model = serviceManager.getSharedResources().getModel();

        if (!serviceManager.getAuthService().isUserLoggedIn()) {
            return buildModelAndView("../unauthorized_view");
        }

        int offerId;
        try {
            offerId = Integer.valueOf(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e) {
            model.put("msg", "Не существует такого предложения!");
            return buildModelAndView("../error_message");
        }

        Offer offer = serviceManager.getOfferService().getOfferById(offerId);
        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (offer != null && serviceManager.getPermissionService().canEditOffer(loggedUser, offer)) {
            model.put("offer", offer);

            PropertyService propertyService = serviceManager.getPropertyService();
            List<Property> userProperties = propertyService.getPropertiesOwnedByUser(loggedUser);
            model.put("userProperties", userProperties);

            OfferType[] offerTypes = OfferType.values();
            model.put("offerTypes", offerTypes);
            model.put("currentOfferType", String.valueOf(offer.getOfferType()));
        } else {
            model.put("msg", "У вас нет прав для редактирования данного предложения!");
            return buildModelAndView("../error_message");
        }

        return buildModelAndView("edit_offer_view");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editOffer")
    public ModelAndView editOfferAction(HttpServletResponse response) {
        initControllerResources(context, request, response);

        Offer offer = constructOfferFromRequest();
        if (offer != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            boolean updateSuccess = offerService.updateOffer(offer);

            if (updateSuccess) {
                return redirect("/offer?id=" + String.valueOf(offer.getId()));
            }
        }

        return showErrorMessage("Не удалось изменить предложение!");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteOffer")
    public ModelAndView deleteOfferAction(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        int offerId = -1;
        try {
            offerId = Integer.valueOf(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e) {
            return showUndefinedOfferMessage();
        }

        OfferService offerService = ServiceManager.getInstance().getOfferService();
        Offer offer = offerService.getOfferById(offerId);
        if (offer != null) {
            boolean deletionSuccess = offerService.deleteOffer(offer);
            if (deletionSuccess) {
                return redirect("/profile");
            }
            else
            {
                model.put("msg", "Не удалось удалить предложение!");
                return buildModelAndView("../error_message");
            }
        }

        return showUndefinedOfferMessage();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/offerFilter")
    public ModelAndView showOfferFilter(HttpServletResponse response) {
        initControllerResources(context, request, response);

        return buildModelAndView("offer_filter");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/offerFilter")
    public ModelAndView filterOffers(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        // Parse filter params
        Map<PropertyFilterParamId, FilterParameter> filterParams = new HashMap<>();
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.Cost, "costMin", "costMax");
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.Area, "areaMin", "areaMax");
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.RoomCount, "roomCountMin", "roomCountMax");
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.DistanceToTransportStop, "distanceToTransportStopMin", "distanceToTransportStopMax");
        addIntegerRangeFilterParam(filterParams, PropertyFilterParamId.DistanceToSubway, "distanceToSubwayMin", "distanceToSubwayMax");
        fillComfortsParams(filterParams);

        // Use service to get matching properties
        OfferService offerService = ServiceManager.getInstance().getOfferService();
        List<Offer> filteredOffers = offerService.filterOffers(filterParams);
        model.put("offers", filteredOffers);

        // Put filter params to preserve form inputs
        Map<String, String> filterParamsModel = constructFilterParametersModel();
        Map<String, Boolean> comfortsParamsModel = constructComfortsParametersModel();
        model.put("filterParams", filterParamsModel);
        model.put("comfortsParams", comfortsParamsModel);

        return buildModelAndView("offer_filter");
    }

    private RequestValidationChain buildOfferValidationChain() {
        return new RequestValidationChain()
            .addValidator(new BigDecimalParameterValidator("cost", false))
            .addValidator(new IntegerParameterValidator("property", false))
            .addValidator(new EnumParameterValidator<>(OfferType.class, "offerType"));
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

    private Offer constructOfferFromRequest() {
        boolean offerValid = offerValidationChain.validate();
        if (offerValid) {
            Integer propertyId = (Integer)offerValidationChain.getValue("property");
            Property property = ServiceManager.getInstance().getPropertyService().getPropertyById(propertyId);

            if (property != null) {
                BigDecimal cost = (BigDecimal)offerValidationChain.getValue("cost");
                OfferType offerType = (OfferType)offerValidationChain.getValue("offerType");

                Offer offer = new Offer();
                offer.setOfferType(offerType);
                offer.setCost(cost);
                offer.setProperty(property);

                // Try loading offer id from request parameter
                Integer id = getIdFromRequest();
                if (id != null) {
                    offer.setId(id);
                }

                return offer;
            }
        }

        return null;
    }

    private Integer getIdFromRequest() {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            return ParseUtils.parseInteger(idParam);
        }

        return null;
    }

    private ModelAndView showUndefinedOfferMessage() {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", "Такого предложения не существует!");
        return buildModelAndView("../error_message");
    }

    private ModelAndView showErrorMessage(String message) {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", message);
        return buildModelAndView("../error_message");
    }
}
