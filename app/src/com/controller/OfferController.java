package com.controller;

import com.model.Offer;
import com.services.OfferService;
import com.services.shared.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

        return buildModelAndView("index");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addOffer")
    public ModelAndView addOfferAction(HttpServletResponse response) {
        initControllerResources(context, request, response);

        return buildModelAndView("index");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/editOffer")
    public ModelAndView showEditOfferView(HttpServletResponse response) {
        initControllerResources(context, request, response);

        return buildModelAndView("index");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editOffer")
    public ModelAndView editOfferAction(HttpServletResponse response) {
        initControllerResources(context, request, response);

        return buildModelAndView("index");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteOffer")
    public ModelAndView deleteOfferAction(HttpServletResponse response) {
        initControllerResources(context, request, response);

        return redirect("/profile");
    }
}
