package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.manshop.android.R;
import com.manshop.android.adapter.PublishAdapter;
import com.manshop.android.model.Goods;
import com.manshop.android.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends BaseActivity {
    private List<Goods> mGood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        showToolbar();
        initData();
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recycle_publish);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setNestedScrollingEnabled(false);
        PublishAdapter adapter = new PublishAdapter(this, mGood);
        recyclerview.setAdapter(adapter);

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

    public void initData(){
        mGood = new ArrayList<>();
        List<String> mPic = new ArrayList<>();
        mPic.add("http://img1.imgtn.bdimg.com/it/u=3927833226,1156268831&fm=27&gp=0.jpg");
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mGood.add(new Goods("123","12",mPic));
        mGood.add(new Goods("123","12",mPic));
        mGood.add(new Goods("123","12",mPic));
        mGood.add(new Goods("123","12",mPic));
    }
}
