package com.manshop.android.ui.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.ListOrderAdapter;
import com.manshop.android.model.Goods;
import com.manshop.android.model.Order;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.utils.Constant;
import com.manshop.android.utils.ImageLoadUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ListOrderActivity extends BaseActivity {
    private List<Order> mOrder = new ArrayList<>();
    private ListOrderAdapter adapter;
    @Bind(R.id.recycle_order)
    RecyclerView rvOrder;
    private OkHttp okhttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);
        ButterKnife.bind(this);
        showToolbar();
        initData();
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

    public void initData() {
        mOrder.clear();
        final List<String> mPic = new ArrayList<>();
        final Map<String, Object> param = new HashMap<>();
        param.put("buid", MyApplication.getInstance().getUserId());
        okhttp.doPost(Constant.baseURL + "order/getMyOrder", new CallBack(ListOrderActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                List<Order> listOrder = JSON.parseArray(jsonArray + "", Order.class);
                for (Order order : listOrder) {
                    Goods good = order.getGood();
                    good.setPics(ImageLoadUtils.displayGoodsImage(good.getPicture()));
                    mOrder.add(order);
                }
                adapter = new ListOrderAdapter(ListOrderActivity.this, mOrder);
                LinearLayoutManager manager = new LinearLayoutManager(ListOrderActivity.this, LinearLayoutManager.VERTICAL, false);
                rvOrder.setLayoutManager(manager);
                rvOrder.setAdapter(adapter);
            }
        }, param);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("----", "onRestart: 刷新");
        initData();
    }
}
