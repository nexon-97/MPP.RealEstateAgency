package com.controller;

import com.helper.SystemMessages;
import com.model.*;
import com.services.OfferService;
import com.utils.request.constructor.OfferConstructor;
import com.utils.request.filter.FilterParameter;
import com.utils.request.filter.OfferFilterParameters;
import com.utils.request.validator.*;
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
        /*initControllerResources(response);

        Integer id = getIdFromRequest();
        if (id != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            Offer offer = offerService.getOfferById(id);

            if (offer != null) {
                Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
                model.put("offer", offer);
                return buildModelAndView("offer/offer_view");
            }
        }

        return showErrorMessage(HttpServletResponse.SC_NOT_FOUND, SystemMessages.NoSuchOfferMessage);*/
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addOffer")
    public ModelAndView showAddOfferView(HttpServletResponse response) {
        /*initControllerResources(response);
        ServiceManager serviceManager = ServiceManager.getInstance();

        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            if (serviceManager.getPermissionService().canAddOffers(loggedUser)) {
                addCurrentUserPropertiesModel();
                List<Property> userProperties = (List<Property>) serviceManager.getSharedResources().getModel().get("userProperties");

                if (userProperties.size() > 0) {
                    addOfferTypeValuesModel();

                    return buildModelAndView("offer/add_offer_view");
                } else {
                    return showErrorMessage(SystemMessages.NoPropertyMessage);
                }
            } else {
                return showErrorMessage(HttpServletResponse.SC_FORBIDDEN, "Вы не можете добавлять предложения!");
            }
        }

        return showUnauthorizedMessageView();*/
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addOffer")
    public ModelAndView addOfferAction(HttpServletResponse response) {
        /*initControllerResources(response);

        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        if (loggedUser == null) {
            return showUnauthorizedMessageView();
        } else if (loggedUser.getRoleId() != RoleId.User) {
            return showErrorMessage(HttpServletResponse.SC_FORBIDDEN, "Вы не можете добавлять предложения!");
        }

        boolean offerValid = offerValidationChain.validate();
        Offer offer = constructOfferFromRequest();
        if (offerValid) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();

            if (offerService.addOffer(offer)) {
                return redirect("/profile");
            } else {
                return showErrorMessage(offerService.getErrorCode(), offerService.getErrorMessage());
            }
        }

        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("errors", offerValidationChain.getErrorMessageMap());
        model.put("offer", offer);
        addOfferTypeValuesModel();
        addCurrentUserPropertiesModel();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return buildModelAndView("offer/add_offer_view");*/
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/editOffer")
    public ModelAndView showEditOfferView(HttpServletResponse response) {
        /*initControllerResources(response);
        ServiceManager serviceManager = ServiceManager.getInstance();

        Integer offerId = getIdFromRequest();
        if (offerId != null) {
            Offer offer = serviceManager.getOfferService().getOfferById(offerId);

            if (offer != null) {
                User loggedUser = serviceManager.getAuthService().getLoggedUser();

                if (serviceManager.getPermissionService().canEditOffer(loggedUser, offer)) {
                    Map<String, Object> model = serviceManager.getSharedResources().getModel();
                    model.put("offer", offer);
                    addOfferTypeValuesModel();
                    addCurrentUserPropertiesModel();

                    return buildModelAndView("offer/edit_offer_view");
                } else {
                    return showErrorMessage(HttpServletResponse.SC_FORBIDDEN, SystemMessages.EditOfferInsufficientRightsMessage);
                }
            }
        }

        return showErrorMessage(HttpServletResponse.SC_NOT_FOUND, SystemMessages.NoSuchOfferMessage);*/
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editOffer")
    public ModelAndView editOfferAction(HttpServletResponse response) {
        /*initControllerResources(response);

        boolean offerValid = offerValidationChain.validate();
        Offer offer = constructOfferFromRequest();
        if (offerValid) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();

            User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
            if (ServiceManager.getInstance().getPermissionService().canEditOffer(loggedUser, offer)) {
                if (offerService.updateOffer(offer)) {
                    return redirect("/offer?id=" + String.valueOf(offer.getId()));
                } else {
                    return showErrorMessage(offerService.getErrorCode(), offerService.getErrorMessage());
                }
            } else {
                return showErrorMessage(HttpServletResponse.SC_FORBIDDEN, SystemMessages.UserIsNotOfferOwnerMessage);
            }
        }

        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("errors", offerValidationChain.getErrorMessageMap());
        model.put("offer", offer);
        addOfferTypeValuesModel();
        addCurrentUserPropertiesModel();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return buildModelAndView("offer/edit_offer_view");*/
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteOffer")
    public ModelAndView deleteOfferAction(HttpServletResponse response) {
        /*initControllerResources(response);

        Integer offerId = getIdFromRequest();
        if (offerId != null) {
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            Offer offer = offerService.getOfferById(offerId);

            if (offer != null) {
                boolean deletionSuccess = offerService.deleteOffer(offer);
                if (deletionSuccess) {
                    return redirectToReferer();
                } else {
                    return showErrorMessage(SystemMessages.FailedToDeleteOfferMessage);
                }
            }
        }

        return showErrorMessage(SystemMessages.NoSuchOfferMessage);*/
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/offerFilter")
    public ModelAndView showOfferFilter(HttpServletResponse response) {
        /*initControllerResources(response);

        addOfferTypeValuesModel();

        return buildModelAndView("offer/offer_filter");*/
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/offerFilter")
    public ModelAndView filterOffers(HttpServletResponse response) {
        /*initControllerResources(response);

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

        return buildModelAndView("offer/offer_filter");*/
        return null;
    }

    private RequestValidationChain buildOfferValidationChain() {
        return new RequestValidationChain()
            .addValidator(new CostParameterValidator("cost", false))
            .addValidator(new PropertyParameterValidator("property", false))
            .addValidator(new EnumParameterValidator<>(OfferType.class, "offerType", false));
    }

    private Offer constructOfferFromRequest() {
        OfferConstructor constructor = new OfferConstructor();
        Offer offer = constructor.construct(offerValidationChain.getValidatedValues());

        Integer id = getIdFromRequest();
        if (id != null) {
            offer.setId(id);
        }

        return offer;
    }

    private void addOfferTypeValuesModel() {
        //Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        //model.put("offerTypes", OfferType.values());
    }

    private void addCurrentUserPropertiesModel() {
        /*User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        List<Property> userProperties = ServiceManager.getInstance().getPropertyService().getPropertiesOwnedByUser(loggedUser);

        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("userProperties", userProperties);*/
    }
}
