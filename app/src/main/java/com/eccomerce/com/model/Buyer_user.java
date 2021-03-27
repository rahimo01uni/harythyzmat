package com.eccomerce.com.model;

public class Buyer_user {
    public String full_name,surname,phone,email,userid, imageurl,address;

    public Buyer_user() {
    }

    public Buyer_user(String full_name, String phone, String email, String imageurl, String userid, String address) {
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.imageurl = imageurl;
        this.userid = userid;
        this.address=address;
    }

    public Buyer_user(String full_name, String surname, String phone, String email, String userid) {
        this.full_name = full_name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.userid = userid;
    }
    
}
