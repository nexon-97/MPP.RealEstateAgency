package com.controller;

import com.exception.EntityNotFoundException;
import com.exception.GenericException;
import com.helper.SystemMessages;
import com.model.Property;
import com.model.PropertyType;
import com.model.RoleId;
import com.security.annotations.RoleCheck;
import com.services.PropertyService;
import com.utils.request.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PropertyController extends BaseController  {

    @Autowired
    PropertyService propertyService;

    @GetMapping(value = "/property")
    public String showPropertyInfo(@RequestParam("id") int propertyId, Model model) {
        Property property = propertyService.getPropertyById(propertyId);
        if (property == null) {
            throw new EntityNotFoundException("Такой собственности не существует!");
        }

        model.addAttribute("property", property);
        return "property_view/property";
    }

    @GetMapping(value = "/addProperty")
    @RoleCheck(RoleId.User)
    public String visitAddPropertyForm(Model model) {
        PropertyType[] types = PropertyType.values();
        model.addAttribute("types", types);

        return "property_view/addProperty";
    }

    @PostMapping(value = "/addProperty")
    @RoleCheck(RoleId.User)
    public String doAddProperty(Model model) {
        RequestValidationChain requestValidator = buildPropertyValidationChain();
        if (requestValidator.validate(request)) {
            boolean isAdded = propertyService.addProperty(requestValidator);
            if (isAdded) {
                return redirect("/profile");
            } else {
                return getViewWithErrors(model, "addError", "Ошибка при добавлении собственности", requestValidator.getValidatedValues());
            }
        } else {
            return getViewWithErrors(model, "errors", requestValidator.getErrorMessageMap(), requestValidator.getValidatedValues());
        }
    }

    @GetMapping(value = "/deleteProperty")
    @RoleCheck(RoleId.User)
    public String deleteOfferAction(@RequestParam("id") int propertyId) {
        Property property = propertyService.getPropertyById(propertyId);
        if (property != null) {
            boolean deletionSuccess = propertyService.deleteProperty(property);
            if (deletionSuccess) {
                return redirectToReferer();
            } else {
                throw new GenericException(SystemMessages.FailedToDeletePropertyMessage);
            }
        }

        throw new EntityNotFoundException(SystemMessages.NoSuchProperyMessage);
    }

    private RequestValidationChain buildPropertyValidationChain() {
        return new RequestValidationChain()
                .addValidator(new EnumParameterValidator<>(PropertyType.class, "type", false))
                .addValidator(new PropertyStringParameterValidator("city",  false))
                .addValidator(new PropertyStringParameterValidator("street", false))
                .addValidator(new IntegerParameterValidator("houseNumber", false))
                .addValidator(new IntegerParameterValidator("blockNumber", true))
                .addValidator(new IntegerParameterValidator("flatNumber", true))
                .addValidator(new IntegerParameterValidator("roomsCount", true))
                .addValidator(new IntegerParameterValidator("area", false))
                .addValidator(new IntegerParameterValidator("subway", true))
                .addValidator(new IntegerParameterValidator("bus",true))
                .addValidator(new BooleanParameterValidator("furniture"))
                .addValidator(new BooleanParameterValidator("internet"))
                .addValidator(new BooleanParameterValidator("tv"))
                .addValidator(new BooleanParameterValidator("phone"))
                .addValidator(new BooleanParameterValidator("fridge"))
                .addValidator(new BooleanParameterValidator("stove"))
                .addValidator(new StringParameterValidator("description", false));
    }

    private String getViewWithErrors(Model model, String key, Object error, Object values) {
        PropertyType[] types = PropertyType.values();
        model.addAttribute(key, error);
        model.addAttribute("values", values);
        model.addAttribute("types", types);

        return "addProperty";
    }
}
