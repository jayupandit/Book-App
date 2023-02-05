package com.jaypandit.bookapp.vendor;

import java.util.List;

public class BookVendor {

    String vendorFullName,shopName,shopAddress,cityName,phoneNumber,personalNumber;
    List<String> schoolNameList;

    public String getVendorFullName() {
        return vendorFullName;
    }

    public BookVendor setVendorFullName(String vendorFullName) {
        this.vendorFullName = vendorFullName;
        return this;
    }

    public String getShopName() {
        return shopName;
    }

    public BookVendor setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public BookVendor setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public BookVendor setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BookVendor setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public BookVendor setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
        return this;
    }

    public List<String> getSchoolNameList() {
        return schoolNameList;
    }

    public BookVendor setSchoolNameList(List<String> schoolNameList) {
        this.schoolNameList = schoolNameList;
        return this;
    }

}
