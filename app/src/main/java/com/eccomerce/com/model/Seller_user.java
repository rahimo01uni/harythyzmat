package com.eccomerce.com.model;

public class Seller_user {

    public String full_name,surname,dob,address,phone,email,userid;

    public Seller_user(){

    }
    public Seller_user(String full_name, String surname, String dob, String address, String phone, String email, String userid){
        this.full_name = full_name;
        this.userid=userid;
        this.surname = surname;
        this.dob=dob;
        this.address=address;
        this.phone=phone;
        this.email=email;

    }
}
