package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.ui.base.BaseActivity;

public class GoodDetailActivity extends BaseActivity {
    private RoundedImageView RivPhoto;
    private TextView tvUsername;
    private TextView tvPrice;
    private TextView tvDetail;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        init();
        setData();
    }
    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_toolbar_edit_address, menu);
        return true;
    }
    public void init() {
        RivPhoto = (RoundedImageView) findViewById(R.id.user_photo);
        tvUsername = (TextView) findViewById(R.id.user_name);
        tvPrice = (TextView) findViewById(R.id.good_price);
        tvDetail = (TextView) findViewById(R.id.good_detail);
    }

    public void setData() {
        intent = getIntent();
        Glide.with(GoodDetailActivity.this).load(intent.getStringExtra("photo")).into(RivPhoto);
        tvUsername.setText(intent.getStringExtra("username"));
        tvPrice.setText(intent.getStringExtra("price") + "￥");
        tvDetail.setText(intent.getStringExtra("detail"));
        TextPaint paint = tvDetail.getPaint();
        paint.setFakeBoldText(true);
    }
}
