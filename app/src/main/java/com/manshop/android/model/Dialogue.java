package com.manshop.android.model;

/**
 * Created by Lin on 2017/10/19.
 */

public class Dialogue {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;
    private String content;
    private int type;
    public Dialogue(String content, int type){
        this.content=content;
        this.type=type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
