package com.manshop.android.ui.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.manshop.android.R;
import com.manshop.android.ui.base.BaseActivity;

public class AddressDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);
        showToolbar();
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
}
