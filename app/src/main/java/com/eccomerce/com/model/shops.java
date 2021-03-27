package com.eccomerce.com.model;

/**
 * Created by User on 22.11.2018.
 */

public class shops {
    String id,kategoriya,name,nomer,content,skidka,Image,rate,locate,uid;

    public shops(String id,String uid, String kategoriya, String name, String nomer, String content, String skidka, String image) {
        this.kategoriya = kategoriya;
        this.name = name;
        this.nomer = nomer;
        this.content = content;
        this.skidka = skidka;
        this.Image=image;
        this.id=id;
        this.uid=uid;
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

    public String getImage() {
        return Image;
    }


    public String getKategoriya() {
        return kategoriya;
    }

    public String getName() {
        return name;
    }

    public String getNomer() {
        return nomer;
    }

    public String getContent() {
        return content;
    }

    public String getSkidka() {
        return skidka;
    }


    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }
}
