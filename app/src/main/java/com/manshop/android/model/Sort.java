package com.manshop.android.model;

import java.util.List;

public class Sort {
    private Integer id;
    private String sortName;
    private List<String> smallSort;

    public Sort() {
    }

    public Sort(Integer id, String sortName, List<String> smallSort) {
        this.id = id;
        this.sortName = sortName;
        this.smallSort = smallSort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public List<String> getSmallSort() {
        return smallSort;
    }

    public void setSmallSort(List<String> smallSort) {
        this.smallSort = smallSort;
    }
}
