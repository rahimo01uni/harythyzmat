package com.eccomerce.com.model;

public class modelvip {
    String id,uid,name,price,priceold,number,shopid,shop_uid,image,image1,image2,image3,skidka,content,rate,vip,yeri,eamil;

    public modelvip(String id,String uid, String name, String price, String priceold, String number, String shopid,String shop_uid, String image, String image1,
                    String image2, String image3, String skidka,String content) {
        this.content=content;
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.price = price;
        this.priceold = priceold;
        this.number = number;
        this.shopid = shopid;
        this.shop_uid = shop_uid;
        this.image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.skidka = skidka;

    }

    public String getShop_uid() {
        return shop_uid;
    }

    public void setShop_uid(String shop_uid) {
        this.shop_uid = shop_uid;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public modelvip() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceold() {
        return priceold;
    }

    public String getNumber() {
        return number;
    }

    public String getShopid() {
        return shopid;
    }

    public String getImage() {
        return image;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getSkidka() {
        return skidka;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getYeri() {
        return yeri;
    }

    public void setYeri(String yeri) {
        this.yeri = yeri;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }
}
