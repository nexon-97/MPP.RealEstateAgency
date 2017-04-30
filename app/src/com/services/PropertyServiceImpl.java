package com.services;

import com.dao.PropertyDAO;
import com.dao.PropertyDAOImpl;
import com.model.Property;
import com.model.User;
import com.model.PropertyType;

import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceManager;
import com.services.shared.ServiceSharedResources;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParamId;
import com.utils.request.validator.RequestValidationChain;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.security.Provider;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyServiceImpl extends BaseService implements PropertyService {
    public PropertyServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.PropertyService, sharedResources);
    }

    @Override
    public Property getPropertyById(int id) {
        PropertyDAO dao = new PropertyDAOImpl();

        return dao.getPropertyById(id);
    }

    @Override
    public boolean addProperty(RequestValidationChain requestValidationChain) {
        Property property = new Property();
        PropertyDAO propertyDAO = new PropertyDAOImpl();
        boolean isCorrectFields = true;

        property.setType((PropertyType)requestValidationChain.getValue("type"));
        property.setCity((String)requestValidationChain.getValue("city"));
        property.setStreet((String)requestValidationChain.getValue("street"));
        property.setHouseNumber((Integer) requestValidationChain.getValue("houseNumber"));
        property.setBlockNumber((Integer) requestValidationChain.getValue("blockNumber"));
        property.setFlatNumber((Integer) requestValidationChain.getValue("flatNumber"));
        property.setRoomsCount((Integer) requestValidationChain.getValue("roomsCount"));
        property.setArea((Integer) requestValidationChain.getValue("area"));
        property.setDistanceToSubway((Integer) requestValidationChain.getValue("subway"));
        property.setDistanceToTransportStop((Integer) requestValidationChain.getValue("bus"));
        property.setHasFurniture((Boolean) requestValidationChain.getValue("furniture"));
        property.setHasInternet((Boolean) requestValidationChain.getValue("internet"));
        property.setHasTv((Boolean) requestValidationChain.getValue("tv"));
        property.setHasPhone((Boolean) requestValidationChain.getValue("phone"));
        property.setHasFridge((Boolean) requestValidationChain.getValue("fridge"));
        property.setHasStove((Boolean) requestValidationChain.getValue("stove"));
        property.setDescription((String)requestValidationChain.getValue("description"));

        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        property.setOwner(loggedUser);

        Map<String, Object> model = getSharedResources().getModel();
        return propertyDAO.addProperty(property);
    }

    @Override
    public boolean updateProperty(Map<String, String[]> params) {
        return false;
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        PropertyDAO propertyDAO = new PropertyDAOImpl();

        return propertyDAO.getPropertiesOwnedByUser(user);
    }


    private boolean checkNotNullNumber(Integer data) {
        return data>0;
    }
}
