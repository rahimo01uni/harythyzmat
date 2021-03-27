package com.eccomerce.com.model;

/**
 * Created by Admin on 5/23/2019.
 */

public class usluga_ici {
    String id;
    String img;
    String description;

    public usluga_ici(String id, String img, String description) {
        this.id = id;
        this.img = img;
        this.description = description;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
