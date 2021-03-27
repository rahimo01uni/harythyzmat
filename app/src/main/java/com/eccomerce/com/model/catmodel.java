package com.eccomerce.com.model;

/**
 * Created by Admin on 5/25/2019.
 */

public class catmodel {
    String img,name,id;

    public catmodel( String id,String name,String img) {
        this.img = img;
        this.name = name;
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
