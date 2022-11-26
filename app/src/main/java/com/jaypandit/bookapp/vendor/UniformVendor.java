package com.jaypandit.bookapp.vendor;

import java.util.List;

public class UniformVendor {

    String fullName,shopName,shopAddress,shopCity,shopPhoneNo,shopPermanentNo;
    List<String> schoolNameList,shirtColorList,pantColorList;

    public String getFullName() {
        return fullName;
    }

    public UniformVendor setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getShopName() {
        return shopName;
    }

    public UniformVendor setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public UniformVendor setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
        return this;
    }

    public String getShopCity() {
        return shopCity;
    }

    public UniformVendor setShopCity(String shopCity) {
        this.shopCity = shopCity;
        return this;
    }

    public String getShopPhoneNo() {
        return shopPhoneNo;
    }

    public UniformVendor setShopPhoneNo(String shopPhoneNo) {
        this.shopPhoneNo = shopPhoneNo;
        return this;
    }

    public String getShopPermanentNo() {
        return shopPermanentNo;
    }

    public UniformVendor setShopPermanentNo(String shopPermanentNo) {
        this.shopPermanentNo = shopPermanentNo;
        return this;
    }

    public List<String> getSchoolNameList() {
        return schoolNameList;
    }

    public UniformVendor setSchoolNameList(List<String> schoolNameList) {
        this.schoolNameList = schoolNameList;
        return this;
    }

    public List<String> getShirtColorList() {
        return shirtColorList;
    }

    public UniformVendor setShirtColorList(List<String> shirtColorList) {
        this.shirtColorList = shirtColorList;
        return this;
    }

    public List<String> getPantColorList() {
        return pantColorList;
    }

    public UniformVendor setPantColorList(List<String> pantColorList) {
        this.pantColorList = pantColorList;
        return this;
    }
}
