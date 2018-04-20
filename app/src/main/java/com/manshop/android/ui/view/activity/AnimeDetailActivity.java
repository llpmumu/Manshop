package com.manshop.android.ui.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.table.TableData;
import com.manshop.android.R;
import com.manshop.android.model.RoleAndAkira;

import java.util.ArrayList;
import java.util.List;

public class AnimeDetailActivity extends AppCompatActivity {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    //    private SmartTable table;
    private TableLayout table;

    private List<RoleAndAkira> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);
        init();
    }

    public void init() {
//        table = (SmartTable<RoleAndAkira>) findViewById(R.id.table_akira);
//        list.add(new RoleAndAkira("111","1111"));
//        list.add(new RoleAndAkira("111","1111"));
//        list.add(new RoleAndAkira("111","1111"));
//        list.add(new RoleAndAkira("111","1111"));
//        table.setData(list);
//
//        final Column<String> roleColumn = new Column<>("角色", "name");
//        final Column<String> akireColumn = new Column<>("声优", "age");
//
//        final TableData<RoleAndAkira> tableData= new TableData<>("测试",list,roleColumn,akireColumn);
//        table.setTableData(tableData);
        table = (TableLayout) findViewById(R.id.table_akira);
        //清除表格所有行
        table.removeAllViews();
        //全部列自动填充空白处
        table.setStretchAllColumns(true);
        for (int i = 1; i <= 5; i++) {
            TableRow tableRow = new TableRow(AnimeDetailActivity.this);
            for (int j = 1; j <= 2; j++) {
                //tv用于显示
                TextView tv = new TextView(AnimeDetailActivity.this);
                tv.setText("(" + i + "," + j + ")");

                tableRow.addView(tv);
            }
            //新建的TableRow添加到TableLayout
            table.addView(tableRow, new TableLayout.LayoutParams(MP, WC, 1));

        }
    }
}
