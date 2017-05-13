package com.controller;

import com.helper.SystemMessages;
import com.model.*;
import com.permission.EntityOperation;
import com.permission.Operation;
import com.services.OfferService;
import com.services.shared.ServiceManager;
import com.utils.request.filter.FilterParameter;
import com.utils.request.filter.OfferFilterParameters;
import com.utils.request.validator.IntegerParameterValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class OfferController extends AbstractCrudController<Offer> {

    public OfferController() {
        super(Offer.class);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/offer")
    @EntityOperation(type = Operation.Read)
    public ModelAndView showOffer(HttpServletResponse response) {
        initResources(response);

        if (hasPermissionError()) {
            return finalizeController(getResponseData());
        }

        Offer offer = getService().get(getIdFromRequest());
        if (offer != null) {
            putModelItem("offer", offer);

            return finalizeController("offer_view");
        }

        return showOfferNotFoundMessage();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addOffer")
    @EntityOperation(type = Operation.Create)
    public ModelAndView showAddOfferView(HttpServletResponse response) {
        initResources(response);

        if (hasPermissionError()) {
            return finalizeController(getResponseData());
        }

        fillOfferViewModel(null);

        return finalizeController("add_offer_view");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addOffer")
    @EntityOperation(type = Operation.Create)
    public ModelAndView addOfferAction(HttpServletResponse response) {
        initResources(response);

        if (hasPermissionError()) {
            return finalizeController(getResponseData());
        }

        Offer offer = getEntity();
        OfferService offerService = ServiceManager.getInstance().getOfferService();
        if (offerService.add(offer)) {
            return finalizeController(HttpServletResponse.SC_CREATED, "redirect:/profile");
        } else {
            fillOfferViewModel(offer);

            return finalizeController(offerService.getResponseData().getStatus(), "add_offer_view");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/editOffer")
    @EntityOperation(type = Operation.Update)
    public ModelAndView showEditOfferView(HttpServletResponse response) {
        initResources(response);

        if (hasPermissionError()) {
            return finalizeController(getResponseData());
        }

        Offer offer = ServiceManager.getInstance().getOfferService().get(getIdFromRequest());
        if (offer != null) {
            fillOfferViewModel(offer);

            return buildModelAndView("edit_offer_view");
        }

        return showOfferNotFoundMessage();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editOffer")
    @EntityOperation(type = Operation.Update)
    public ModelAndView editOfferAction(HttpServletResponse response) {
        initResources(response);

        if (hasPermissionError()) {
            return finalizeController(getResponseData());
        }

        Offer offer = getEntity();
        if (offer != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();

            if (offerService.update(offer)) {
                return finalizeController("redirect:/offer?id=" + String.valueOf(offer.getId()));
            } else {
                fillOfferViewModel(offer);

                return finalizeController(offerService.getResponseData().getStatus(), "edit_offer_view");
            }
        }

        return showOfferNotFoundMessage();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteOffer")
    @EntityOperation(type = Operation.Delete)
    public ModelAndView deleteOfferAction(HttpServletResponse response) {
        initResources(response);

        Integer id = getIdFromRequest();
        if (id != null) {
            Offer offer = getService().get(id);
            if (offer != null && getService().canDelete(offer)) {
                if (getService().delete(offer)) {
                    return redirectToReferer();
                }
            } else {
                return finalizeController(getService().getResponseData());
            }
        }

        putModelItem("msg", "Не удалось удалить предложение!");
        return finalizeController(HttpServletResponse.SC_BAD_REQUEST, getErrorMessageView());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/offerFilter")
    public ModelAndView showOfferFilter(HttpServletResponse response) {
        initResources(response);

        addOfferTypeValuesModel();

        return buildModelAndView("offer_filter");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/offerFilter")
    public ModelAndView filterOffers(HttpServletResponse response) {
        initResources(response);

        OfferFilterParameters filterParameters = new OfferFilterParameters();
        boolean paramsValid = filterParameters.validate();
        if (paramsValid) {
            List<FilterParameter> filterParams = filterParameters.construct();

            OfferService offerService = ServiceManager.getInstance().getOfferService();
            List<Offer> filteredOffers = offerService.filter(filterParams);

            Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
            model.put("offers", filteredOffers);
            model.put("filterParams", filterParameters.getParamsMap());

            addOfferTypeValuesModel();
        }

        return buildModelAndView("offer_filter");
    }

    private void fillOfferViewModel(Offer offer) {
        addOfferTypeValuesModel();
        addCurrentUserPropertiesModel();
        putModelItem("offer", offer);
    }

    private void addOfferTypeValuesModel() {
        putModelItem("offerTypes", OfferType.values());
    }

    private void addCurrentUserPropertiesModel() {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        List<Property> userProperties = ServiceManager.getInstance().getPropertyService().listUserOwnedProperties(loggedUser);
        putModelItem("userProperties", userProperties);
    }

    private ModelAndView showOfferNotFoundMessage() {
        putModelItem("msg", "Предложение не найдено!");
        return finalizeController(HttpServletResponse.SC_NOT_FOUND, getErrorMessageView());
    }
}
