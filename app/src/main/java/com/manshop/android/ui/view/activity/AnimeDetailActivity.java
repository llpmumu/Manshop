package com.manshop.android.ui.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.manshop.android.R;

public class AnimeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);
        Log.d("anime",getIntent().getStringExtra("title"));
    }
}
