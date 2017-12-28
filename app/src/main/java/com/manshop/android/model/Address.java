package com.manshop.android.model;

/**
 * Created by Lin on 2017/12/28.
 */

public class Address {
    private int id ;
    private String consignee ;
    private String phone ;
    private String addr ;
    private String postalCode ;
    private boolean isDefault ;

    public Address(int id, String consignee, String phone, String addr, String postalCode, boolean isDefault) {
        this.id = id;
        this.consignee = consignee;
        this.phone = phone;
        this.addr = addr;
        this.postalCode = postalCode;
        this.isDefault = isDefault;
    }

    public String getZipCode() {
        return postalCode;
    }

    public void setZipCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
