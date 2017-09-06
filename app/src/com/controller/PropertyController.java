package com.controller;

import com.exception.EntityNotFoundException;
import com.exception.GenericException;
import com.helper.SystemMessages;
import com.model.Property;
import com.model.PropertyType;
import com.model.RoleId;
import com.security.annotations.RoleCheck;
import com.services.PropertyService;
import com.validators.PropertyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
public class PropertyController extends BaseController  {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyValidator propertyValidator;

    @GetMapping(value = "/property")
    public String showPropertyInfo(@RequestParam("id") int propertyId, Model model) {
        Property property = propertyService.get(propertyId);
        if (property == null) {
            throw new EntityNotFoundException("Такой собственности не существует!");
        }

        model.addAttribute("property", property);
        return "property_view/property";
    }

    @GetMapping(value = "/addProperty")
    @RoleCheck(RoleId.User)
    public String visitAddPropertyForm(Model model) {
        Property defaultProperty = new Property();
        defaultProperty.setType(PropertyType.Flat);

        model.addAttribute("property", defaultProperty);
        model.addAttribute("propertyTypes", PropertyType.values());

        return "property_view/addProperty";
    }

    @PostMapping(value = "/addProperty")
    @RoleCheck(RoleId.User)
    public String doAddProperty(@Valid @ModelAttribute("property") Property property, BindingResult result, Model model, HttpServletResponse response) {
        propertyValidator.validate(property, result);

        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            property.setOwner(authService.getLoggedUser());
            propertyService.add(property);

            return redirect("/profile");
        }

        model.addAttribute("propertyTypes", PropertyType.values());
        return "property_view/addProperty";
    }

    @GetMapping(value = "/deleteProperty")
    @RoleCheck(RoleId.User)
    public String deleteOfferAction(@RequestParam("id") int propertyId) {
        Property property = propertyService.get(propertyId);
        if (property != null) {
            boolean deletionSuccess = propertyService.delete(property);
            if (deletionSuccess) {
                return redirect("/profile");
            } else {
                throw new GenericException(SystemMessages.FailedToDeletePropertyMessage);
            }
        }

        throw new EntityNotFoundException(SystemMessages.NoSuchProperyMessage);
    }
}
