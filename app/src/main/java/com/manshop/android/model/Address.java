package com.manshop.android.model;

/**
 * Created by Lin on 2017/12/28.
 */

public class Address {
    private Integer id;
    private User user;
    private String consignee;
    private String address;
    private String addphone;
    private boolean isDefault;

    public Address(int id, String consignee, String phone, String addr, boolean isDefault) {
        this.id = id;
        this.consignee = consignee;
        this.addphone = phone;
        this.address = addr;
        this.isDefault = isDefault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressPhone() {
        return addphone;
    }

    public void setAddressPhone(String addphone) {
        this.addphone = addphone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
