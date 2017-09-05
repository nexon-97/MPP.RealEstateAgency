package com.controller;

import com.model.Offer;
import com.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    OfferService offerService;

    @GetMapping(value = "/")
    public String navigateToIndex(Model model) {
        List<Offer> offers = offerService.listAllOffers();
        model.addAttribute("offers", offers);

        return "index";
    }
}
