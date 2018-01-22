package com.manshop.android.model;

/**
 * Created by Lin on 2017/12/28.
 */

public class Address {
    private Integer id;
    private Integer uid;
    private String consignee;
    private String address;
    private String addphone;
    private boolean isDefault;

    public Address() {
        super();
    }
    public Address(Integer id, Integer uid, String consignee, String address, String addphone, boolean isDefault) {
        this.id = id;
        this.uid = uid;
        this.consignee = consignee;
        this.address = address;
        this.addphone = addphone;
        this.isDefault = isDefault;
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

    public String getAddphone() {
        return addphone;
    }

    public void setAddphone(String addphone) {
        this.addphone = addphone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", uid=" + uid +
                ", consignee='" + consignee + '\'' +
                ", address='" + address + '\'' +
                ", addphone='" + addphone + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
