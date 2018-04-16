package com.manshop.android.ui.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.manshop.android.R;
import com.manshop.android.view.ExpandTabView;
import com.manshop.android.view.ViewLeft;
import com.manshop.android.view.ViewMiddle;
import com.manshop.android.view.ViewRight;

import java.util.ArrayList;

public class AllGoodsActivity extends AppCompatActivity {
    private ExpandTabView expandTabView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewLeft viewLeft;
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_goods);
        initView();
        initVaule();
        initListener();

    }

    private void initView() {

        expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
        viewLeft = new ViewLeft(this);
        viewMiddle = new ViewMiddle(this);
        viewRight = new ViewRight(this);

    }

    private void initVaule() {

//		mViewArray.add(viewLeft);
        mViewArray.add(viewMiddle);
		mViewArray.add(viewRight);
        ArrayList<String> mTextArray = new ArrayList<String>();
//		mTextArray.add("");
        mTextArray.add("全部");
		mTextArray.add("价格");
        expandTabView.setValue(mTextArray, mViewArray);
//		expandTabView.setTitle(viewLeft.getShowText(), 0);
		expandTabView.setTitle(viewMiddle.getShowText(), 0);
		expandTabView.setTitle(viewRight.getShowText(), 1);

    }

    private void initListener() {

        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewLeft, showText);
            }
        });

        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

            @Override
            public void getValue(String showText) {

                onRefresh(viewMiddle,showText);

            }
        });

        viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewRight, showText);
            }
        });

    }

    private void onRefresh(View view, String showText) {

        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
        }
        Toast.makeText(AllGoodsActivity.this, showText, Toast.LENGTH_SHORT).show();

    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {

        if (!expandTabView.onPressBack()) {
            finish();
        }

    }



}
