package com.controller;

import com.model.*;
import com.services.OfferService;
import com.services.PropertyService;
import com.services.shared.ServiceManager;
import com.utils.request.OfferValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Controller
public class OfferController extends BaseController {

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
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        OfferValidator validator = new OfferValidator(request.getParameterMap());
        if (validator.verify()) {
            Offer offer = validator.getOffer();
            OfferService offerService = ServiceManager.getInstance().getOfferService();
            if (offerService.addOffer(offer)) {
                return redirect("/offer?id=" + String.valueOf(offer.getId()));
            }
        }

        model.put("msg", "Не удалось добавить предложение! Проверьте правильность параметров!");
        return buildModelAndView("../error_message");
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
        boolean updateSuccess = false;

        OfferValidator validator = new OfferValidator(request.getParameterMap());
        if (validator.verify()) {
            Offer offer = validator.getOffer();

            User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
            if (ServiceManager.getInstance().getPermissionService().canEditOffer(loggedUser, offer)) {
                OfferService offerService = ServiceManager.getInstance().getOfferService();
                updateSuccess = offerService.updateOffer(offer);

                if (updateSuccess) {
                    return redirect("/offer?id=" + String.valueOf(offer.getId()));
                }
            }
        }

        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", "Не удалось изменить предложение!");
        return buildModelAndView("../error_message");
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

    private ModelAndView showUndefinedOfferMessage() {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", "Такого предложения не существует!");
        return buildModelAndView("../error_message");
    }
}
