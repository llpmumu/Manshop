package com.manshop.android.model;

public class SmallSort {
    private Integer id;
    private Integer sortid;
    private Sort sort;
    private String sortName;

    public SmallSort() {
    }

    public SmallSort(Integer id, Integer sortid, Sort sort, String sortName) {
        this.id = id;
        this.sortid = sortid;
        this.sort = sort;
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

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
