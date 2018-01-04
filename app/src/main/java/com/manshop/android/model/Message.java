package com.manshop.android.model;

/**
 * Created by Lin on 2018/1/4.
 */

public class Message {
    private String head;
    private String username;
    private String lastMsg;
    private String time;

    public Message(String head, String username, String lastMsg, String time) {
        this.head = head;
        this.username = username;
        this.lastMsg = lastMsg;
        this.time = time;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "head='" + head + '\'' +
                ", username='" + username + '\'' +
                ", lastMsg='" + lastMsg + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
