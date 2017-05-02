package com.controller;

import com.model.Offer;
import com.model.RoleId;
import com.model.User;
import com.services.AuthService;
import com.services.AuthServiceImpl;
import com.services.OfferService;
import com.services.shared.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView navigateToIndex(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        OfferService offerService = ServiceManager.getInstance().getOfferService();
        List<Offer> offers = offerService.listAllOffers();
        model.put("offers", offers);
        System.out.println();
        return buildModelAndView("index");
    }
}
