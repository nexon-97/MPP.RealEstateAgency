package com.controller;

import com.helper.SystemMessages;
import com.model.*;
import com.services.OfferService;
import com.services.PropertyService;
import com.services.UserService;
import com.services.shared.ServiceManager;
import com.utils.request.ParseUtils;
import com.utils.request.constructor.OfferConstructor;
import com.utils.request.filter.FilterParameter;
import com.utils.request.filter.OfferFilterParameters;
import com.utils.request.validator.BigDecimalParameterValidator;
import com.utils.request.validator.EnumParameterValidator;
import com.utils.request.validator.IntegerParameterValidator;
import com.utils.request.validator.RequestValidationChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class OfferController extends BaseController {

    private RequestValidationChain offerValidationChain;

    public OfferController() {
        this.offerValidationChain = buildOfferValidationChain();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/offer")
    public ModelAndView showOffer(HttpServletResponse response) {
        initControllerResources(response);

        Integer id = getIdFromRequest();
        if (id != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            Offer offer = offerService.getOfferById(id);

            if (offer != null) {
                Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
                model.put("offer", offer);
                return buildModelAndView("offer_view");
            }
        }

        return showErrorMessage(SystemMessages.NoSuchOfferMessage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addOffer")
    public ModelAndView showAddOfferView(HttpServletResponse response) {
        initControllerResources(response);
        ServiceManager serviceManager = ServiceManager.getInstance();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            PropertyService propertyService = serviceManager.getPropertyService();
            List<Property> userProperties = propertyService.getPropertiesOwnedByUser(loggedUser);

            if (userProperties.size() > 0) {
                OfferType[] offerTypes = OfferType.values();

                Map<String, Object> model = serviceManager.getSharedResources().getModel();
                model.put("offerTypes", offerTypes);
                model.put("userProperties", userProperties);

                return buildModelAndView("add_offer_view");
            } else {
                return showErrorMessage(SystemMessages.NoPropertyMessage);
            }
        }

        return showUnauthorizedMessageView();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addOffer")
    public ModelAndView addOfferAction(HttpServletResponse response) {
        initControllerResources(response);

        Offer offer = constructOfferFromRequest();
        if (offer != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            if (offerService.addOffer(offer)) {
                return redirect("/offer?id=" + String.valueOf(offer.getId()));
            }
        } else {
            return showErrorMessage(SystemMessages.AddOfferRequestBrokenMessage);
        }

        return showErrorMessage(SystemMessages.AddOfferFailedMessage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/editOffer")
    public ModelAndView showEditOfferView(HttpServletResponse response) {
        initControllerResources(response);
        ServiceManager serviceManager = ServiceManager.getInstance();

        if (!serviceManager.getAuthService().isUserLoggedIn()) {
            return showUnauthorizedMessageView();
        }

        Integer offerId = getIdFromRequest();
        if (offerId != null) {
            Offer offer = serviceManager.getOfferService().getOfferById(offerId);

            if (offer != null) {
                User loggedUser = serviceManager.getAuthService().getLoggedUser();

                if (serviceManager.getPermissionService().canEditOffer(loggedUser, offer)) {
                    Map<String, Object> model = serviceManager.getSharedResources().getModel();
                    model.put("offer", offer);

                    PropertyService propertyService = serviceManager.getPropertyService();
                    List<Property> userProperties = propertyService.getPropertiesOwnedByUser(loggedUser);
                    model.put("userProperties", userProperties);

                    OfferType[] offerTypes = OfferType.values();
                    model.put("offerTypes", offerTypes);
                    model.put("currentOfferType", String.valueOf(offer.getOfferType()));

                    return buildModelAndView("edit_offer_view");
                } else {
                    return showErrorMessage(SystemMessages.EditOfferInsufficientRightsMessage);
                }
            }
        }

        return showErrorMessage(SystemMessages.NoSuchOfferMessage);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editOffer")
    public ModelAndView editOfferAction(HttpServletResponse response) {
        initControllerResources(response);

        Offer offer = constructOfferFromRequest();
        if (offer != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            boolean updateSuccess = offerService.updateOffer(offer);

            if (updateSuccess) {
                return redirect("/offer?id=" + String.valueOf(offer.getId()));
            }
        }

        return showErrorMessage(SystemMessages.FailedToEditOfferMessage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteOffer")
    public ModelAndView deleteOfferAction(HttpServletResponse response) {
        initControllerResources(response);

        Integer offerId = getIdFromRequest();
        if (offerId != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            Offer offer = offerService.getOfferById(offerId);

            if (offer != null) {
                boolean deletionSuccess = offerService.deleteOffer(offer);
                if (deletionSuccess) {
                    return redirect("/profile");
                } else {
                    return showErrorMessage(SystemMessages.FailedToDeleteOfferMessage);
                }
            }
        }

        return showErrorMessage(SystemMessages.NoSuchOfferMessage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/offerFilter")
    public ModelAndView showOfferFilter(HttpServletResponse response) {
        initControllerResources(response);

        addOfferTypeValuesModel();

        return buildModelAndView("offer_filter");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/offerFilter")
    public ModelAndView filterOffers(HttpServletResponse response) {
        initControllerResources(response);

        OfferFilterParameters filterParameters = new OfferFilterParameters();
        boolean paramsValid = filterParameters.validate();
        if (paramsValid) {
            List<FilterParameter> filterParams = filterParameters.construct();

            OfferService offerService = ServiceManager.getInstance().getOfferService();
            List<Offer> filteredOffers = offerService.filterOffers(filterParams);

            Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
            model.put("offers", filteredOffers);
            model.put("filterParams", filterParameters.getParamsMap());

            addOfferTypeValuesModel();
        }

        return buildModelAndView("offer_filter");
    }

    private RequestValidationChain buildOfferValidationChain() {
        return new RequestValidationChain()
            .addValidator(new BigDecimalParameterValidator("cost", false))
            .addValidator(new IntegerParameterValidator("property", false))
            .addValidator(new EnumParameterValidator<>(OfferType.class, "offerType", false));
    }

    private Offer constructOfferFromRequest() {
        boolean offerValid = offerValidationChain.validate();
        if (offerValid) {
            OfferConstructor constructor = new OfferConstructor();
            Offer offer = constructor.construct(offerValidationChain.getValidatedValues());

            Integer id = getIdFromRequest();
            if (id != null) {
                offer.setId(id);
            }

            return offer;
        }

        return null;
    }

    private void addOfferTypeValuesModel() {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("offerTypes", OfferType.values());
    }
}
