package com.manshop.android.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.manshop.android.R;
import com.manshop.android.ui.base.BaseActivity;

public class PublishActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        showToolbar();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_add, menu);
        return true;
    }

    //标题栏按钮功能实现
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_add:
                Intent intent = new Intent(PublishActivity.this,NewPublishActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
}
