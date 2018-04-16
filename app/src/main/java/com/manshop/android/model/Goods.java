package com.manshop.android.model;

import android.graphics.Bitmap;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Lin on 2017/12/27.
 */

public class Goods {
    private Integer id;
    private Integer uid;
    private User user;
    private Integer sid;
    private SmallSort smallSort;
    private String title;
    private String detail;
    private String price;
    private String rental;
    private Integer type;
    private Integer state;
    private String picture;
    private List<String> pics;
    private Timestamp goodtime;

    public Goods() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public SmallSort getSmallSort() {
        return smallSort;
    }

    public void setSmallSort(SmallSort smallSort) {
        this.smallSort = smallSort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public Timestamp getGoodtime() {
        return goodtime;
    }

    public void setGoodtime(Timestamp goodtime) {
        this.goodtime = goodtime;
    }
}
