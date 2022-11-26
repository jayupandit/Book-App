package com.jaypandit.bookapp;

public class User {

    String email,mobile, password;
    int admin;


    public User() {
    }

    public User(String email, String mobile,String pass,int admin) {
        this.email = email;
        this.mobile = mobile;
        this.password = pass;
        this.admin = admin;

    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public int getAdmin() {
        return admin;
    }

    public User setAdmin(int admin) {
        this.admin = admin;
        return this;
    }
}
