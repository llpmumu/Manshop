package com.manshop.android.ui.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.manshop.android.R;
import com.manshop.android.adapter.AddressAdapter;
import com.manshop.android.model.Address;
import com.manshop.android.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends BaseActivity {
    private List<Address> mAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        showToolbar();
        init();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView_address);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        AddressAdapter adapter = new AddressAdapter(this, mAddress);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_toolbar_address, menu);
        return true;
    }

    private void init(){
        mAddress = new ArrayList<>();
        mAddress.add( new Address(1,"Jame","13459427684","杭州市拱墅区湖州街51号,浙江大学城市学院","123456",true));
        mAddress.add( new Address(1,"Jame","13459427684","杭州市拱墅区湖州街51号,浙江大学城市学院","123456",false));
        mAddress.add( new Address(1,"Jame","13459427684","杭州市拱墅区湖州街51号,浙江大学城市学院","123456",false));
        mAddress.add( new Address(1,"Jame","13459427684","杭州市拱墅区湖州街51号,浙江大学城市学院","123456",false));
        mAddress.add( new Address(1,"Jame","13459427684","杭州市拱墅区湖州街51号,浙江大学城市学院","123456",false));

    }
}
