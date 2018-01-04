package com.manshop.android.model.city;

import java.util.List;

/**
 * Created by Lin on 2018/1/4.
 */

public class Privince {
    private String name ;
    private List<City> cityModels ;

    public List<City> getCityModels() {
        return cityModels;
    }

    public void setCityModels(List<City> cityModels) {
        this.cityModels = cityModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
