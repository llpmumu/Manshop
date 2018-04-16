package com.manshop.android.model;

import java.util.List;

public class Sort {
    private Integer id;
    private String sortName;
    private List<SmallSort> smallSortList;

    public Sort() {
    }

    public Sort(Integer id, String sortName, List<SmallSort> smallSortList) {
        this.id = id;
        this.sortName = sortName;
        this.smallSortList = smallSortList;
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

    public List<SmallSort> getSmallSortList() {
        return smallSortList;
    }

    public void setSmallSortList(List<SmallSort> smallSortList) {
        this.smallSortList = smallSortList;
    }
}
