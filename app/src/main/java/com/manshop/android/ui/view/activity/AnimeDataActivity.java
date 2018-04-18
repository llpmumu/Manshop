package com.manshop.android.ui.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.manshop.android.R;
import com.manshop.android.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnimeDataActivity extends BaseActivity {
    @Bind(R.id.lv_comic_name)
    ListView listvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_data);
        ButterKnife.bind(this);
        showToolbar();
    }
    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

}
