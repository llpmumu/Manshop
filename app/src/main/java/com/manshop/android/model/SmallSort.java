package com.manshop.android.model;

public class SmallSort {
    private Integer id;
    private Integer sortid;
    private String sortName;

    public SmallSort() {
    }

    public SmallSort(Integer id, Integer sortid,String sortName) {
        this.id = id;
        this.sortid = sortid;
        this.sortName = sortName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
