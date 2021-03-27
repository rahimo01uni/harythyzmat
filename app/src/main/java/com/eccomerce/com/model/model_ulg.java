package com.eccomerce.com.model;

/**
 * Created by Admin on 5/19/2019.
 */

public class model_ulg {
    String id,uid,title,rate,img;
    String img2,img3,img4,img5,mg6,content;
    String number;
    public model_ulg(String id,String uid, String title, String rate, String img,String img2,String img3,String img4,String content,String number) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.rate = rate;
        this.img = img;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.content = content;
        this.number = number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImg5() {
        return img5;
    }
    public void setImg5(String img5) {
        this.img5 = img5;
    }
    public String getMg6() {
        return mg6;
    }
    public void setMg6(String mg6) {
        this.mg6 = mg6;
    }
}
