package com.manshop.android.model;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * Created by Lin on 2018/4/19.
 */

@SmartTable(name="角色配音表")
public class RoleAndAkira {
    @SmartColumn(id =1,name = "角色")
    private String name;
    @SmartColumn(id=2,name="声优")
    private String age;

    public RoleAndAkira(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
