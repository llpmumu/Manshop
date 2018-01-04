package com.manshop.android.model.city;

import java.util.List;

/**
 * Created by Lin on 2018/1/4.
 */

public class City {
    private String name ;
    private List<District> districtModels ;

    public List<District> getDistrictModels() {
        return districtModels;
    }

    public void setDistrictModels(List<District> districtModels) {
        this.districtModels = districtModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
