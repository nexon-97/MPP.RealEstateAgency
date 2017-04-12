package com.model;

public class Property {
    private int id;
    private PropertyType type;
    private String city;
    private String street;
    private int houseNumber;
    private int flatNumber;
    private int roomsCount;
    private int area;
    private int distanceToSubway;
    private int distanceToTransportStop;
    private boolean hasFurniture;
    private boolean hasInternet;
    private boolean hasTv;
    private boolean hasPhone;
    private boolean hasFridge;
    private boolean hasStove;
    private String description;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setDistanceToSubway(int distanceToSubway) {
        this.distanceToSubway = distanceToSubway;
    }

    public void setDistanceToTransportStop(int distanceToTransportStop) {
        this.distanceToTransportStop = distanceToTransportStop;
    }

    public void setHasFurniture(boolean hasFurniture) {
        this.hasFurniture = hasFurniture;
    }

    public void setHasInternet(boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    public void setHasTv(boolean hasTv) {
        this.hasTv = hasTv;
    }

    public void setHasPhone(boolean hasPhone) {
        this.hasPhone = hasPhone;
    }

    public void setHasFridge(boolean hasFridge) {
        this.hasFridge = hasFridge;
    }

    public void setHasStove(boolean hasStove) {
        this.hasStove = hasStove;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public PropertyType getType() {
        return this.type;
    }

    public String getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }

    public int getHouseNumber() {
        return this.houseNumber;
    }

    public int getFlatNumber() {
        return this.flatNumber;
    }

    public int getRoomsCount() {
        return this.roomsCount;
    }

    public int getArea() {
        return this.area;
    }

    public int getDistanceToSubway() {
        return this.distanceToSubway;
    }

    public int getDistanceToTransportStop() {
        return this.distanceToTransportStop;
    }

    public boolean getHasFurniture() {
        return this.hasFurniture;
    }

    public boolean getHasInternet() {
        return this.hasInternet;
    }

    public boolean getHasTv() {
        return this.hasTv;
    }

    public boolean getHasPhone() {
        return this.hasPhone;
    }

    public boolean getHasFridge() {
        return this.hasFridge;
    }

    public boolean getHasStove() {
        return this.hasStove;
    }

    public String getDescription() {
        return this.description;
    }
}
