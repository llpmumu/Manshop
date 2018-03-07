package com.manshop.android.model;

import java.sql.Timestamp;

/**
 * Created by Lin on 2017/10/19.
 */

public class Dialogue {
//    public static final int TYPE_RECEIVED=0;
//    public static final int TYPE_SENT=1;
//    private String content;
//    private int type;
//    public Dialogue(String content, int type){
//        this.content=content;
//        this.type=type;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public int getType() {
//        return type;
//    }

    private Integer id;
    private String msg;
    private Integer sender;
    private User suser;
    private Integer receiver;
    private User ruser;
    private Integer type;
    private Timestamp msgtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public User getSuser() {
        return suser;
    }

    public void setSuser(User suser) {
        this.suser = suser;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public User getRuser() {
        return ruser;
    }

    public void setRuser(User ruser) {
        this.ruser = ruser;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(Timestamp msgtime) {
        this.msgtime = msgtime;
    }

    @Override
    public String toString() {
        return "Dialogue{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", sender=" + sender +
                ", suser=" + suser +
                ", receiver=" + receiver +
                ", ruser=" + ruser +
                ", type=" + type +
                ", msgtime=" + msgtime +
                '}';
    }
}
