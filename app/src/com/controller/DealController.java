package com.controller;

import com.dao.DealDAO;
import com.exception.EntityNotFoundException;
import com.exception.GenericException;
import com.helper.SystemMessages;
import com.model.*;
import com.security.annotations.RoleCheck;
import com.services.DealRequestService;
import com.services.DealService;
import com.services.OfferService;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class DealController extends BaseController {

    @Autowired
    DealDAO dealDAO;

    @Autowired
    OfferService offerService;

    @Autowired
    UserService userService;

    @Autowired
    DealRequestService dealRequestService;

    @Autowired
    DealService dealService;

    @GetMapping(value = "/deal")
    @RoleCheck(RoleId.Admin)
    public String showDeal(@RequestParam("id") int dealId, Model model) {
        Deal deal = dealDAO.get(dealId);
        if (deal != null) {
            model.addAttribute("deal", deal);
        } else {
            throw new GenericException("Такой стелки не существует!");
        }

        return "deal/deal_view";
    }

    @GetMapping(value = "/addDealRequest")
    @RoleCheck(RoleId.User)
    public String addOfferRequest(@RequestParam("id") int offerId,
                                  @RequestParam("buyer") int buyerId,
                                  @RequestParam(value = "realtor", required = false) Integer realtorId,
                                  Model model) {
            Offer offer = offerService.getOfferById(offerId);
            if (offer == null) {
                throw new GenericException(SystemMessages.NoSuchOfferMessage);
            }

            User buyer = userService.getUserByID(buyerId);
            if (buyer == null) {
                throw new GenericException(SystemMessages.InvalidDealRequestBuyerAssigned);
            } else if (!authService.getLoggedUser().equals(buyer)) {
                throw new GenericException("Вы не можете откликаться на предложение от имени другого пользователя!");
            }

            DealRequest dealRequest = new DealRequest();
            dealRequest.setOffer(offer);
            dealRequest.setBuyer(buyer);

            if (dealRequestService.isAlreadyRegistered(dealRequest)) {
                throw new GenericException(SystemMessages.SuchDealRequestAlreadyRegistered);
            }

            // Add optional realtor
            if (realtorId == null) {
                // Add model param to construct realtors selector
                List<User> realtorsList = userService.getUsersByRole(RoleId.Realtor);

                model.addAttribute("realtorsList", realtorsList);
                model.addAttribute("offer", offer);

                return "deal/add_deal_request_view";
            } else if (realtorId != 0) {
                boolean realtorAdded = addDealRequestRealtor(dealRequest, realtorId);
                if (!realtorAdded) {
                    throw new GenericException(SystemMessages.FailedToAddRealtorToDealRequest);
                }
            }

            boolean requestAdded = dealRequestService.add(dealRequest);
            if (!requestAdded) {
                throw new GenericException(SystemMessages.FailedToAddDealRequest);
            }

            model.addAttribute("msg", SystemMessages.DealRequestHasBeenRegistered);
            return "success_message";
    }

    @GetMapping(value = "/confirmDealRealtor")
    @RoleCheck(RoleId.Realtor)
    public String confirmDealRequestRealtor(@RequestParam("id") int requestId, @RequestParam("realtor") int realtorId) {
        DealRequest request = dealRequestService.get(requestId);
        if (request != null) {
            if (!request.getRealtor().equals(authService.getLoggedUser())) {
                throw new GenericException("Вы не были помечены как риэлтор в этом отклике!");
            }

            request.setRealtorValidation(true);
            dealRequestService.update(request);

            return redirect("/profile");
        } else {
            throw new EntityNotFoundException("Такого отклика не существует!");
        }
    }

    @GetMapping(value = "/confirmDealSeller")
    @RoleCheck(RoleId.User)
    public String confirmDealRequestSeller(@RequestParam("id") int requestId, @RequestParam("seller") int sellerId) {
        DealRequest request = dealRequestService.get(requestId);
        if (request != null) {
            if (!request.getOffer().getProperty().getOwner().equals(authService.getLoggedUser())) {
                throw new GenericException("Вы не владелец данной собственности!");
            }

            request.setSellerValidation(true);
            dealRequestService.update(request);

            return redirect("/profile");
        } else {
            throw new GenericException("Такого отклика не существует!");
        }
    }

    @PostMapping(value = "/confirmDealBroker")
    @RoleCheck(RoleId.Broker)
    public String confirmDealBroker(@RequestParam("id") int id) {
        Deal deal = dealService.get(id);
        if (deal != null) {
            if (dealService.signDeal(deal, authService.getLoggedUser())) {
                return redirectToReferer();
            } else {
                throw new GenericException("Не удалось закрепить брокера за сделкой!");
            }
        } else {
            throw new GenericException(SystemMessages.NoSuchDealMessage);
        }
    }

    private boolean addDealRequestRealtor(DealRequest request, Integer realtorId) {
        if (realtorId != null) {
            User realtor = userService.getUserByID(realtorId);
            if (realtor != null) {
                request.setRealtor(realtor);
                request.setRealtorValidation(false);

                return true;
            }
        }

        return false;
    }
}
