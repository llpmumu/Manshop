package com.manshop.android.model;

import java.sql.Time;

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
    private Integer delivery;
    private String trackingnum;
    private Integer state;
    private String leavetime;
    private Integer type;
    private Time ordertime;

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

    public Time getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Time ordertime) {
        this.ordertime = ordertime;
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
