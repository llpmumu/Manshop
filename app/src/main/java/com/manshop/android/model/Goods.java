package com.manshop.android.model;

import java.util.List;

/**
 * Created by Lin on 2017/12/27.
 */

public class Goods {
    private Integer id;
    private Integer uid;
    private String username;
    private String photo;
    private String title;
    private String details;
    private String price;
    private String rental;
    private Integer type;
    private Integer state;
    private String picture;
    private List<String> pics;

    public Goods() {
    }

    public Goods(String photo, String username, String price, String details, List<String> pics) {
        this.photo = photo;
        this.username = username;
        this.price = price;
        this.details = details;
        this.pics = pics;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
