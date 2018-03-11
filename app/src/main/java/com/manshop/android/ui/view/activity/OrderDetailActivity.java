package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.manshop.android.R;
import com.manshop.android.model.Goods;
import com.manshop.android.model.Order;
import com.manshop.android.model.User;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;
import com.manshop.android.util.StringUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class OrderDetailActivity extends BaseActivity {
    @Bind(R.id.tv_logistics_info)
    TextView tvLogisInfo;

    @Bind(R.id.adv_pic)
    SimpleDraweeView sdvPic;
    @Bind(R.id.dtl_title)
    TextView dtlTitle;

    @Bind(R.id.seller_name)
    TextView tvSellerName;
    @Bind(R.id.order_id)
    TextView tvOrderId;
    @Bind(R.id.order_time)
    TextView tvOrderTime;

    private Intent intent;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        intent = getIntent();
        showToolbar();
        getData();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String tip = intent.getStringExtra("type");
                if (tip.equals("buy"))
                    startActivity(new Intent(OrderDetailActivity.this, MainActivity.class));
                else if (tip.equals("old"))
                    finish();
                break;
            default:
        }
        return true;
    }

    public void getData() {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", intent.getStringExtra("oid"));
        Log.d("order", "oid:" + intent.getStringExtra("oid"));
        okHttp.doPost(Constant.baseURL + "order/getOneOrder", new CallBack(OrderDetailActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Order order = json.getObject("data", Order.class);

                //物流信息
                if (order.getDelivery() == 1) {
                    tvLogisInfo.setText("见面交易，无需快递");
                } else if (order.getDelivery() == 2) {
                    tvLogisInfo.setText(order.getTrackingnum());
                } else {
                    tvLogisInfo.setText("等待发货");
                }

                //物品信息
                Goods good = order.getGood();
                String picture = good.getPicture();
//                String[] txtpicture = picture.split(";");
//                Glide.with(OrderDetailActivity.this).load(txtpicture[0]).into(sdvPic);
                sdvPic.setImageBitmap(StringUtil.getInstance().spiltPic(picture).get(0));
                dtlTitle.setText(good.getTitle());

                //订单信息
                User suser = order.getSuser();
                tvSellerName.setText(suser.getUsername());
                tvOrderId.setText(order.getId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tvOrderTime.setText(sdf.format(order.getOrdertime()));

            }
        }, params);
    }


}
