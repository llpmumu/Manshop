package com.manshop.android.model;


import java.util.List;

public class User {
    private Integer id;
    private String phone;
    private String username;
    private String password;
    private String head;
    private Address defauteConsigen;

    public Address getDefauteConsigen() {
        return defauteConsigen;
    }

    public void setDefauteConsigen(Address defauteConsigen) {
        this.defauteConsigen = defauteConsigen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

}
