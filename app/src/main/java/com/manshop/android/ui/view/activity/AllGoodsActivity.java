package com.manshop.android.ui.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.R;
import com.manshop.android.adapter.ListAllGoodsAdapter;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.utils.Constant;
import com.manshop.android.utils.ImageLoadUtils;
import com.manshop.android.view.ExpandTabView;
import com.manshop.android.view.ViewMiddle;
import com.manshop.android.view.ViewRight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class AllGoodsActivity extends AppCompatActivity {
    private ExpandTabView expandTabView;
    private RecyclerView reGoods;
    private ListAllGoodsAdapter adapter;
    private List<Goods> mGoods = new ArrayList<>();
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_goods);
        initView();
        initVaule();
        initListener();
        refreshView("全部");
    }

    private void initView() {
        expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
        reGoods = (RecyclerView) findViewById(R.id.re_goods);
        GridLayoutManager manager = new GridLayoutManager(AllGoodsActivity.this, 2);
        reGoods.setLayoutManager(manager);
        viewMiddle = new ViewMiddle(this);
        viewRight = new ViewRight(this);
    }

    private void initVaule() {
        mViewArray.add(viewMiddle);
        mViewArray.add(viewRight);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("全部");
        mTextArray.add("价格");
        expandTabView.setValue(mTextArray, mViewArray);
        expandTabView.setTitle(viewMiddle.getShowText(), 0);
        expandTabView.setTitle(viewRight.getShowText(), 1);
    }

    private void initListener() {
        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

            @Override
            public void getValue(String showText) {
                onRefresh(viewMiddle, showText);
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
//        Toast.makeText(AllGoodsActivity.this, showText, Toast.LENGTH_SHORT).show();
        if (position == 0)
            refreshView(showText);
        else
            refreshView(position, showText);
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

    public void refreshView(String showText) {
        mGoods.clear();
        Log.d("sort", showText);
        Map<String, Object> params = new HashMap<>();
        params.put("sortName", showText);
        okHttp.doPost(Constant.baseURL + "sort/getSortGood", new CallBack(AllGoodsActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
//                Toast.makeText(getApplicationContext(), "手机号或密码错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                System.out.println(jsonArray);
                List<Goods> listGood = JSON.parseArray(jsonArray + "", Goods.class);
                for (Goods good : listGood) {
                    good.setPics(ImageLoadUtils.displayGoodsImage(good.getPicture()));
                    mGoods.add(good);
                }
                reGoods.setNestedScrollingEnabled(false);
                adapter = new ListAllGoodsAdapter(AllGoodsActivity.this, mGoods);
                reGoods.setAdapter(adapter);
            }
        }, params);
    }

    public void refreshView(int position, final String showText) {
        Collections.sort(mGoods, new Comparator<Goods>() {
            @Override
            public int compare(Goods o1, Goods o2) {
                Goods good1 = (Goods) o1;
                Goods good2 = (Goods) o2;

                Integer price1 = Integer.parseInt(good1.getPrice());
                Integer price2 = Integer.parseInt(good2.getPrice());
                int flag = 0;
                if (showText.equals("从高到低")) {
                    flag = price2.compareTo(price1);
                } else {
                    flag = price1.compareTo(price2);
                }
                return flag;
            }
        });
        adapter.notifyDataSetChanged();
    }

}
