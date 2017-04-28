package com.services;

import com.dao.PropertyDAO;
import com.dao.PropertyDAOImpl;
import com.model.Property;
import com.model.User;
import com.model.PropertyType;

import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParamId;

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
    public boolean addProperty(Map<String, String[]> params) {
        Property property = new Property();
        PropertyDAO propertyDAO = new PropertyDAOImpl();
        boolean isCorrectFields = true;

        property.setType( PropertyType.valueOf(params.get("type")[0]));
        property.setCity(params.get("city")[0]);
        property.setStreet(params.get("street")[0]);
        property.setHouseNumber(Integer.parseInt(params.get("houseNumber")[0]));
        property.setFlatNumber(Integer.parseInt(params.get("flatNumber")[0]));
        property.setRoomsCount(Integer.parseInt(params.get("flatNumber")[0]));
        property.setArea(Integer.parseInt(params.get("area")[0]));
        property.setDistanceToSubway(Integer.parseInt(params.get("subway")[0]));
        property.setDistanceToTransportStop(Integer.parseInt(params.get("bus")[0]));
        property.setHasFurniture(isChecked(params.get("furniture")));
        property.setHasInternet(isChecked(params.get("internet")));
        property.setHasTv(isChecked(params.get("tv")));
        property.setHasPhone(isChecked(params.get("phone")));
        property.setHasFridge(isChecked(params.get("fridge")));
        property.setHasStove(isChecked(params.get("stove")));
        property.setDescription(params.get("description")[0]);

        if (!checkStringName(property.getCity())){
            property.setCity(null);
            isCorrectFields = false;
        }

        if (!checkStringName(property.getStreet())){
            property.setStreet(null);
            isCorrectFields = false;
        }

        if (!checkNumber(property.getHouseNumber())){
            property.setHouseNumber(-1);
            isCorrectFields = false;
        }

        if (!checkNumber(property.getFlatNumber())){
            property.setFlatNumber(-1);
            isCorrectFields = false;
        }

        if (!checkNumber(property.getRoomsCount())){
            property.setRoomsCount(-1);
            isCorrectFields = false;
        }

        if (!checkNumber(property.getArea())){
            property.setArea(-1);
            isCorrectFields = false;
        }

        if (!checkDistance(property.getDistanceToSubway())){
            property.setDistanceToSubway(-1);
            isCorrectFields = false;
        }

        if (!checkDistance(property.getDistanceToTransportStop())){
            property.setDistanceToTransportStop(-1);
            isCorrectFields = false;
        }

        Map<String, Object> model = getSharedResources().getModel();
        model.put("propertyInfo", property);
        if (isCorrectFields){
            propertyDAO.addProperty(property);
        }
        else {
            PropertyType[] types = PropertyType.values();
            model.put("types", types);
        }
        return isCorrectFields;
    }

    @Override
    public boolean updateProperty(Map<String, String[]> params) {
        return false;
    }

    @Override
    public List<Property> filterProperties(Map<PropertyFilterParamId, FilterParameter> filterParameters) {
        PropertyDAO dao = new PropertyDAOImpl();

        return dao.filter(filterParameters);
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        PropertyDAO propertyDAO = new PropertyDAOImpl();

        return propertyDAO.getPropertiesOwnedByUser(user);
    }

    private boolean isChecked(String[] value){
        return value!=null;
    }

    private boolean checkStringName(String data){
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яёА-ЯЁ][a-zA-Zа-яёА-ЯЁ\\-'\\s]*$");
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }

    private boolean checkDistance(Integer data){
        return data>=0;
    }

    private boolean checkNumber(Integer data){
        return data>0;
    }
}
