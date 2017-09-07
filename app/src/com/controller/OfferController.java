package com.controller;

import com.exception.AccessLevelException;
import com.exception.EntityNotFoundException;
import com.exception.GenericException;
import com.helper.SystemMessages;
import com.model.*;
import com.security.annotations.RoleCheck;
import com.services.OfferService;
import com.services.PermissionService;
import com.services.PropertyService;
import com.utils.request.constructor.OfferConstructor;
import com.utils.request.filter.FilterParameter;
import com.utils.request.filter.OfferFilterParameters;
import com.utils.request.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class OfferController extends BaseController {

    @Autowired
    OfferService offerService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    PermissionService permissionService;

    @GetMapping(value = "/offer")
    public String showOffer(@RequestParam("id") int offerId, Model model) {
        Offer offer = offerService.getOfferById(offerId);
        if (offer == null) {
            throw new EntityNotFoundException(SystemMessages.NoSuchOfferMessage);
        }

        model.addAttribute("offer", offer);
        return "offer/offer_view";
    }

    @GetMapping(value = "/addOffer")
    @RoleCheck(RoleId.User)
    public String showAddOfferView(Model model) {
        addCurrentUserPropertiesModel(model);
        List<Property> userProperties = (List<Property>) model.asMap().get("userProperties");

        if (userProperties.size() > 0) {
            addOfferTypeValuesModel(model);
            return "offer/add_offer_view";
        } else {
            throw new GenericException(SystemMessages.NoPropertyMessage);
        }
    }

    @PostMapping(value = "/addOffer")
    @RoleCheck(RoleId.User)
    public String addOfferAction(@RequestParam(value = "id", required = false) Integer offerId, Model model) {
        RequestValidationChain offerValidationChain = buildOfferValidationChain();
        Offer offer = null;

        boolean offerValid = offerValidationChain.validate(request);
        if (offerValid) {
            OfferConstructor constructor = new OfferConstructor();
            offer = constructor.construct(offerValidationChain.getValidatedValues());

            if (offerService.addOffer(offer)) {
                return redirect("/profile");
            } else {
                throw new GenericException(offerService.getErrorMessage());
            }
        }

        model.addAttribute("errors", offerValidationChain.getErrorMessageMap());
        model.addAttribute("offer", offer);
        addOfferTypeValuesModel(model);
        addCurrentUserPropertiesModel(model);

        return "offer/add_offer_view";
    }

    @GetMapping(value = "/editOffer")
    @RoleCheck(RoleId.User)
    public String showEditOfferView(@RequestParam("id") int offerId, Model model) {
        Offer offer = offerService.getOfferById(offerId);
        if (offer != null) {
            User loggedUser = authService.getLoggedUser();

            if (permissionService.canEditOffer(loggedUser, offer)) {
                model.addAttribute("offer", offer);
                addOfferTypeValuesModel(model);
                addCurrentUserPropertiesModel(model);

                return "offer/edit_offer_view";
            } else {
                throw new AccessLevelException();
            }
        }

        throw new EntityNotFoundException(SystemMessages.NoSuchOfferMessage);
    }

    @PostMapping(value = "/editOffer")
    @RoleCheck(RoleId.User)
    public String editOfferAction(@RequestParam("id") int offerId, Model model) {
        RequestValidationChain offerValidationChain = buildOfferValidationChain();
        boolean offerValid = offerValidationChain.validate(request);
        OfferConstructor constructor = new OfferConstructor();
        Offer offer = constructor.construct(offerValidationChain.getValidatedValues());
        offer.setId(offerId);

        if (offerValid) {
            User loggedUser = authService.getLoggedUser();
            if (permissionService.canEditOffer(loggedUser, offer)) {
                if (offerService.updateOffer(offer)) {
                    return redirect("/offer?id=" + String.valueOf(offer.getId()));
                } else {
                    throw new GenericException(offerService.getErrorMessage());
                }
            } else {
                throw new AccessLevelException();
            }
        }

        model.addAttribute("errors", offerValidationChain.getErrorMessageMap());
        model.addAttribute("offer", offer);
        addOfferTypeValuesModel(model);
        addCurrentUserPropertiesModel(model);

        return "offer/edit_offer_view";
    }

    @GetMapping(value = "/deleteOffer")
    @RoleCheck(RoleId.User)
    public String deleteOfferAction(@RequestParam("id") int offerId, Model model) {
        Offer offer = offerService.getOfferById(offerId);
        if (offer != null) {
            boolean deletionSuccess = offerService.deleteOffer(offer);
            if (deletionSuccess) {
                return redirectToReferer();
            } else {
                throw new GenericException(SystemMessages.FailedToDeleteOfferMessage);
            }
        }

        throw new EntityNotFoundException(SystemMessages.NoSuchOfferMessage);
    }

    @GetMapping(value = "/offerFilter")
    public String showOfferFilter(Model model) {
        addOfferTypeValuesModel(model);
        return "offer/offer_filter";
    }

    @PostMapping(value = "/offerFilter")
    public String filterOffers(Model model) {
        OfferFilterParameters filterParameters = new OfferFilterParameters();
        boolean paramsValid = filterParameters.validate(request);

        if (paramsValid) {
            List<FilterParameter> filterParams = filterParameters.construct();
            List<Offer> filteredOffers = offerService.filterOffers(filterParams);

            model.addAttribute("offers", filteredOffers);
            model.addAttribute("filterParams", filterParameters.getParamsMap());

            addOfferTypeValuesModel(model);
        }

        return "offer/offer_filter";
    }

    private RequestValidationChain buildOfferValidationChain() {
        return new RequestValidationChain()
            .addValidator(new CostParameterValidator("cost", false))
            .addValidator(new EnumParameterValidator<>(OfferType.class, "offerType", false));
    }

    private void addOfferTypeValuesModel(Model model) {
        model.addAttribute("offerTypes", OfferType.values());
    }

    private void addCurrentUserPropertiesModel(Model model) {
        User loggedUser = authService.getLoggedUser();
        List<Property> userProperties = propertyService.getPropertiesOwnedByUser(loggedUser);
        model.addAttribute("userProperties", userProperties);
    }
}
