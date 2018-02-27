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
import com.manshop.android.ui.view.activity.OrderDetailActivity;

import java.net.URL;
import java.util.List;

/**
 * Created by Lin on 2018/2/24.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    Context context;
    List<Order> mList;

    public OrderListAdapter(Context context, List<Order> mList) {
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
        Order order = mList.get(position);
        Goods good = order.getGood();
        Glide.with(context).load(good.getPics().get(0)).placeholder(R.drawable.img_loading).into(holder.orderPic);


        holder.orderName.setText(good.getTitle());
        holder.orderPrice.setText(good.getPrice() + "￥");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "nnnnn", Toast.LENGTH_SHORT).show();
                final Order order = mList.get(position);
                Log.d("recyle",mList.get(position).getGood().getPics().get(0));
                Intent intent = new Intent(context, OrderDetailActivity.class);
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

        public ViewHolder(View itemView) {
            super(itemView);
            orderPic = (ImageView) itemView.findViewById(R.id.item_order_pic);
            orderName = (TextView) itemView.findViewById(R.id.item_order_name);
            orderPrice = (TextView) itemView.findViewById(R.id.item_order_price);
        }
    }

}