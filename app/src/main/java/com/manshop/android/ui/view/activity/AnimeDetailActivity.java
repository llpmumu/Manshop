package com.manshop.android.ui.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.table.TableData;
import com.manshop.android.R;
import com.manshop.android.model.RoleAndAkira;

import java.util.ArrayList;
import java.util.List;

public class AnimeDetailActivity extends AppCompatActivity {
    private List<RoleAndAkira> list = new ArrayList<>();
    private SmartTable table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);
        init();
    }

    public void init() {
        table = (SmartTable<RoleAndAkira>) findViewById(R.id.table_akira);
        list.add(new RoleAndAkira("111","1111"));
        list.add(new RoleAndAkira("111","1111"));
        list.add(new RoleAndAkira("111","1111"));
        list.add(new RoleAndAkira("111","1111"));
        table.setData(list);

        final Column<String> roleColumn = new Column<>("角色", "name");
        final Column<String> akireColumn = new Column<>("声优", "age");

        final TableData<RoleAndAkira> tableData= new TableData<>("测试",list,roleColumn,akireColumn);
        table.setTableData(tableData);
    }
}
