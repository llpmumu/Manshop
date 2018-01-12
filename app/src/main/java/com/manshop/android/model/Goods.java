package com.manshop.android.model;

import java.util.List;

/**
 * Created by HP on 2017/12/27.
 */

public class Goods {
    private String photo;
    private String username;
    private String price;
    private String details;
    private String title;
    private List<String> pics;

    public Goods(String title, String price, List<String> pics) {
        this.price = price;
        this.title = title;
        this.pics = pics;
    }

    public Goods(String photo, String username, String price, String details, List<String> pics) {
        this.photo = photo;
        this.username = username;
        this.price = price;
        this.details = details;
        this.pics = pics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "photo='" + photo + '\'' +
                ", username='" + username + '\'' +
                ", price='" + price + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
