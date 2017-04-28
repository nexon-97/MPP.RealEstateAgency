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

        property.setType(getPropertyType(params, "type"));
        property.setCity(getStringParam(params, "city"));
        property.setStreet(getStringParam(params, "street"));
        property.setHouseNumber(getNotNullIntParam(params, "houseNumber"));
        property.setBlockNumber(getIntParam(params, "blockNumber"));
        property.setFlatNumber(getIntParam(params, "flatNumber"));
        property.setRoomsCount(getIntParam(params, "roomsCount"));
        property.setArea(getNotNullIntParam(params, "area"));
        property.setDistanceToSubway(getIntParam(params, "subway"));
        property.setDistanceToTransportStop(getIntParam(params, "bus"));
        property.setHasFurniture(isChecked(params.get("furniture")));
        property.setHasInternet(isChecked(params.get("internet")));
        property.setHasTv(isChecked(params.get("tv")));
        property.setHasPhone(isChecked(params.get("phone")));
        property.setHasFridge(isChecked(params.get("fridge")));
        property.setHasStove(isChecked(params.get("stove")));
        property.setDescription(getDescription(params, "description"));

        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        property.setOwner(loggedUser);

        if (!checkTypeParam(property.getType())) {
            isCorrectFields = false;
        }

        if (!checkStringName(property.getCity())){
            property.setCity(null);
            isCorrectFields = false;
        }

        if (!checkStringName(property.getStreet())){
            property.setStreet(null);
            isCorrectFields = false;
        }

        if (!checkNotNullNumber(property.getHouseNumber())){
            property.setHouseNumber(-1);
            isCorrectFields = false;
        }

        if (!checkNumber(property.getBlockNumber())){
            property.setBlockNumber(-1);
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

        if (!checkNotNullNumber(property.getArea())){
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
    public List<Property> getPropertiesOwnedByUser(User user) {
        PropertyDAO propertyDAO = new PropertyDAOImpl();

        return propertyDAO.getPropertiesOwnedByUser(user);
    }

    private PropertyType getPropertyType(Map<String, String[]> params, String paramKey){
        try {
            return PropertyType.valueOf(params.get("type")[0]);
        }
        catch(NullPointerException | IllegalArgumentException e){
            return null;
        }
    }

    private String getDescription(Map<String, String[]> params, String paramKey){
        String[] paramValues = params.get(paramKey);
        if (paramValues == null || paramValues[0]== null) return "";
        else return paramValues[0];
    }

    private String getStringParam(Map<String, String[]> params, String paramKey){
        String[] paramValues = params.get(paramKey);
        if (paramValues == null || paramValues[0]== null || paramValues[0] == "") return null;
        else return paramValues[0].trim();
    }

    private int getNotNullIntParam(Map<String, String[]> params, String paramKey){
        try {
            String[] paramValues = params.get(paramKey);
            if (paramValues == null || paramValues[0] == null || paramValues[0] == "") return -1;
            else return Integer.parseInt(paramValues[0]);
        }
        catch (NullPointerException | NumberFormatException e){
            return -1;
        }
    }

    private Integer getIntParam(Map<String, String[]> params, String paramKey){
        try {
            String[] paramValues = params.get(paramKey);
            if (paramValues == null || paramValues[0] == null || paramValues[0] == "") return null;
            else return Integer.parseInt(paramValues[0]);
        }
        catch (NullPointerException | NumberFormatException e){
            return -1;
        }
    }

    private boolean isChecked(String[] value){
        return value!=null;
    }

    private boolean checkTypeParam(PropertyType propertyType){
        if (propertyType == null) return false;
        else return true;
    }

    private boolean checkStringName(String data){
        if (data == null) return false;
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яёА-ЯЁ][a-zA-Zа-яёА-ЯЁ\\-'\\s]*$");
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }

    private boolean checkDistance(Integer data){
        if (data == null) return true;
        else return data>=0;
    }

    private boolean checkNumber(Integer data){
        if (data==null) return true;
        else return checkNotNullNumber(data);
    }

    private boolean checkNotNullNumber(Integer data) {
        return data>0;
    }
}
