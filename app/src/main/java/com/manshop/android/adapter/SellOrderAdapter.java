package com.manshop.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.manshop.android.R;
import com.manshop.android.model.Goods;
import com.manshop.android.model.Order;
import com.manshop.android.ui.view.activity.DeliveryActivity;
import com.manshop.android.ui.view.activity.OrderDetailActivity;

import java.net.URL;
import java.util.List;

/**
 * Created by Lin on 2018/2/24.
 */

public class SellOrderAdapter extends RecyclerView.Adapter<SellOrderAdapter.ViewHolder> {
    Context context;
    List<Order> mList;

    public SellOrderAdapter(Context context, List<Order> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_sell_order, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Order order = mList.get(position);
        Goods good = order.getGood();
        holder.orderPic.setImageBitmap(good.getPics().get(0));

        holder.orderName.setText(good.getTitle());
        holder.orderPrice.setText(good.getPrice() + "￥");
        if(order.getState() == 0)
            holder.orderState.setText("等待发货");
        else if(order.getState() == 1)
            holder.orderState.setText("等待收货");
        else
            holder.orderState.setText("交易完成");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Order order = mList.get(position);
                Intent intent = new Intent(context, DeliveryActivity.class);
                intent.putExtra("oid", order.getId());
                intent.putExtra("type","old");
                context.startActivity(intent);
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

        public ViewHolder(View itemView) {
            super(itemView);
            orderPic = (ImageView) itemView.findViewById(R.id.item_order_pic);
            orderName = (TextView) itemView.findViewById(R.id.item_order_name);
            orderPrice = (TextView) itemView.findViewById(R.id.item_order_price);
            orderState = (TextView) itemView.findViewById(R.id.item_order_state);
        }
    }

}