package com.manshop.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.R;
import com.manshop.android.model.Goods;
import com.manshop.android.model.Order;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.view.activity.OrderDetailActivity;
import com.manshop.android.util.Constant;
import com.manshop.android.util.StringUtil;
import com.manshop.android.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by Lin on 2018/2/24.
 */

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    private Context context;
    private List<Order> mList;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    public ListOrderAdapter(Context context, List<Order> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_order, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        final Order order = mList.get(position);
        Goods good = order.getGood();
        holder.orderPic.setImageBitmap(good.getPics().get(0));
        Log.d("order", order.getState() + "       2222");
        holder.orderName.setText(good.getTitle());
        holder.orderPrice.setText(good.getPrice() + "￥");
        if (order.getState() == 0) {
            holder.orderState.setText("等待发货");
            holder.btnConfirm.setVisibility(View.GONE);
        } else if (order.getState() == 1) {
            holder.orderState.setText("等待收货");
            holder.btnConfirm.setVisibility(View.VISIBLE);
        } else if (order.getState() == 2) {
            holder.orderState.setText("交易完成");
            holder.btnConfirm.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Order order = mList.get(position);
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("oid", order.getId());
                intent.putExtra("type", "old");
                context.startActivity(intent);
            }
        });

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOrderState(position, order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView orderPic;
        TextView orderName;
        TextView orderPrice;
        TextView orderState;
        Button btnConfirm;

        public ViewHolder(View itemView) {
            super(itemView);
            orderPic = (ImageView) itemView.findViewById(R.id.item_order_pic);
            orderName = (TextView) itemView.findViewById(R.id.item_order_name);
            orderPrice = (TextView) itemView.findViewById(R.id.item_order_price);
            orderState = (TextView) itemView.findViewById(R.id.item_order_state);
            btnConfirm = (Button) itemView.findViewById(R.id.btn_confirm);
        }
    }

    public void changeOrderState(final int position, Order order) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", order.getId());
        params.put("state", 2);
        params.put("trackingnum", order.getTrackingnum());
        params.put("delivery", order.getDelivery());
        okHttp.doPost(Constant.baseURL + "order/updataOrder", new CallBack(context) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
//                Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Order result = json.getObject("data", Order.class);
                result.getGood().setPics(StringUtil.getInstance().spiltPic(result.getGood().getPicture()));
                ToastUtil.shortToast(context, "确认收货");
                mList.remove(position);
                mList.add(position, result);
                notifyDataSetChanged();
//                notifyItemChanged(position);
            }
        }, params);
    }
}