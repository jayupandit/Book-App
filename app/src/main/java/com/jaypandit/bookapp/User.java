package com.jaypandit.bookapp;

public class User {

    String email,mobile,pass;

    public User() {
    }

    public User(String email, String mobile,String pass) {
        this.email = email;
        this.mobile = mobile;
        this.pass = pass;

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
}
