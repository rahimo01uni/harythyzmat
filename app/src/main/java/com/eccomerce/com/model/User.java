package com.eccomerce.com.model;

public class User {

    private String address;
    private String dob;
    private String email;
    private String full_name;
    private String phone;
    private String surname;
    private String userid;
    private String imageurl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public User(String address, String dob, String email, String full_name, String phone, String surname, String userid, String imageurl) {
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.full_name = full_name;
        this.phone = phone;
        this.surname = surname;
        this.userid = userid;
        this.imageurl = imageurl;
    }

    public User() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
