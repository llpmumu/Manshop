package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.adapter.GoodDetailPicAdapter;
import com.manshop.android.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoodDetailActivity extends BaseActivity {
    private RoundedImageView RivPhoto;
    private TextView tvUsername;
    private TextView tvPrice;
    private TextView tvDetail;
    private RecyclerView rePic;

    private Intent intent;
    private GoodDetailPicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        showToolbar();
        init();
        setData();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        RivPhoto = (RoundedImageView) findViewById(R.id.user_photo);
        tvUsername = (TextView) findViewById(R.id.user_name);
        tvPrice = (TextView) findViewById(R.id.good_price);
        tvDetail = (TextView) findViewById(R.id.good_detail);
        rePic = (RecyclerView) findViewById(R.id.recycle_pic);
        LinearLayoutManager manager = new LinearLayoutManager(GoodDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        rePic.setLayoutManager(manager);
    }

    public void setData() {
        intent = getIntent();
        Glide.with(GoodDetailActivity.this).load(intent.getStringExtra("photo")).into(RivPhoto);
        tvUsername.setText(intent.getStringExtra("username"));
        tvPrice.setText(intent.getStringExtra("price") + "￥");
        tvDetail.setText(intent.getStringExtra("detail"));
        TextPaint paint = tvDetail.getPaint();
        paint.setFakeBoldText(true);

        //物品图片
        List<String> mPic = new ArrayList<>();
        String picture = intent.getStringExtra("picture");
        String[] txtpicture = picture.split(";");
        Collections.addAll(mPic, txtpicture);
        adapter = new GoodDetailPicAdapter(GoodDetailActivity.this, mPic);
        rePic.setAdapter(adapter);
        rePic.setFocusableInTouchMode(false);
        rePic.requestFocus();
    }

    public void buy(View v){
        Intent intentToOrder = new Intent(GoodDetailActivity.this,MyNewOrderActivity.class);
        intentToOrder.putExtra("gid", intent.getIntExtra("gid",0));
        startActivity(intentToOrder);
    }
}
