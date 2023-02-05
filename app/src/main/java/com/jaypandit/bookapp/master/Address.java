package com.jaypandit.bookapp.master;

public class Address {

    String fullName, phoneNumber, houseNo, colony, pinCode, city, nearbyLocation;

    public String getFullName() {
        return fullName;
    }

    public Address setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public Address setHouseNo(String houseNo) {
        this.houseNo = houseNo;
        return this;
    }

    public String getColony() {
        return colony;
    }

    public Address setColony(String colony) {
        this.colony = colony;
        return this;
    }

    public String getPinCode() {
        return pinCode;
    }

    public Address setPinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getNearbyLocation() {
        return nearbyLocation;
    }

    public Address setNearbyLocation(String nearbyLocation) {
        this.nearbyLocation = nearbyLocation;
        return this;
    }
}
