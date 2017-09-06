package com.model;

public class Property extends Entity {

    private PropertyType type;
    private User owner;
    private String city;
    private String street;
    private Integer houseNumber;
    private Integer blockNumber;
    private Integer flatNumber;
    private Integer roomsCount;
    private Integer area;
    private Integer distanceToSubway;
    private Integer distanceToTransportStop;
    private boolean hasFurniture;
    private boolean hasInternet;
    private boolean hasTv;
    private boolean hasPhone;
    private boolean hasFridge;
    private boolean hasStove;
    private String description;


    public void setType(PropertyType type) {
        this.type = type;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setBlockNumber(Integer blockNumber) { this.blockNumber = blockNumber; }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setRoomsCount(Integer roomsCount) {
        this.roomsCount = roomsCount;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public void setDistanceToSubway(Integer distanceToSubway) {
        this.distanceToSubway = distanceToSubway;
    }

    public void setDistanceToTransportStop(Integer distanceToTransportStop) {
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

    public PropertyType getType() {
        return this.type;
    }

    public User getOwner() {
        return this.owner;
    }

    public String getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }

    public Integer getHouseNumber() {
        return this.houseNumber;
    }

    public Integer getBlockNumber() { return this.blockNumber; }

    public Integer getFlatNumber() {
        return this.flatNumber;
    }

    public Integer getRoomsCount() {
        return this.roomsCount;
    }

    public Integer getArea() {
        return this.area;
    }

    public Integer getDistanceToSubway() {
        return this.distanceToSubway;
    }

    public Integer getDistanceToTransportStop() {
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

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Property obj2 = (Property)obj;
        return ((this.id == obj2.getId()));
    }
}
