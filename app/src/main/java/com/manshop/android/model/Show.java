package com.manshop.android.model;

/**
 * Created by Lin on 2018/3/16.
 */

public class Show {
    private Integer id;
    private String title;
    private String province;
    private String address;
    private String picture;
    private String showdata;


    public Show() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShowdata() {
        return showdata;
    }

    public void setShowdata(String showdata) {
        this.showdata = showdata;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", province='" + province + '\'' +
                ", address='" + address + '\'' +
                ", picture='" + picture + '\'' +
                ", showdata='" + showdata + '\'' +
                '}';
    }
}
