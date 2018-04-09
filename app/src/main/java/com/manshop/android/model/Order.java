package com.manshop.android.model;


import java.sql.Timestamp;


/**
 * Created by Lin on 2018/2/23.
 */

public class Order {
    private String id;
    private Integer gid;
    private Goods good;
    private Integer suid;
    private User suser;
    private Integer buid;
    private User buser;
    private Integer aid;
    private Address address;
    private Integer delivery;
    private String trackingnum;
    private Integer state;
    private String leavetime;
    private Integer type;
    private Timestamp ordertime;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Goods getGood() {
        return good;
    }

    public void setGood(Goods good) {
        this.good = good;
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public User getSuser() {
        return suser;
    }

    public void setSuser(User suser) {
        this.suser = suser;
    }

    public Integer getBuid() {
        return buid;
    }

    public void setBuid(Integer buid) {
        this.buid = buid;
    }

    public User getBuser() {
        return buser;
    }

    public void setBuser(User buser) {
        this.buser = buser;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public String getTrackingnum() {
        return trackingnum;
    }

    public void setTrackingnum(String trackingnum) {
        this.trackingnum = trackingnum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(String leavetime) {
        this.leavetime = leavetime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Timestamp ordertime) {
        this.ordertime = ordertime;
    }


    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", gid=" + gid +
                ", good=" + good +
                ", suid=" + suid +
                ", suser=" + suser +
                ", buid=" + buid +
                ", buser=" + buser +
                ", delivery=" + delivery +
                ", trackingnum='" + trackingnum + '\'' +
                ", state=" + state +
                ", leavetime='" + leavetime + '\'' +
                ", type=" + type +
                ", ordertime=" + ordertime +
                '}';
    }
}
