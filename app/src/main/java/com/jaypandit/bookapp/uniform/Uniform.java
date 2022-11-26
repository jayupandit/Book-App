package com.jaypandit.bookapp.uniform;

import java.util.ArrayList;
import java.util.List;

public class Uniform {

   private String schoolName,pantColor,shitColor,prize;
   private List<String> shopNameList;

    public String getSchoolName() {
        return schoolName;
    }

    public Uniform setSchoolName(String schoolName) {
        this.schoolName = schoolName;
        return this;
    }

    public String getPantColor() {
        return pantColor;
    }

    public Uniform setPantColor(String pantColor) {
        this.pantColor = pantColor;
        return this;
    }

    public String getShitColor() {
        return shitColor;
    }

    public Uniform setShitColor(String shitColor) {
        this.shitColor = shitColor;
        return this;
    }

    public String getPrize() {
        return prize;
    }

    public Uniform setPrize(String prize) {
        this.prize = prize;
        return this;
    }

    public List<String> getShopNameList() {
        return shopNameList;
    }

    public Uniform setShopNameList(List<String> shopNameList) {
        this.shopNameList = shopNameList;
        return this;
    }
}
