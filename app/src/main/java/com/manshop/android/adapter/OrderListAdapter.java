package com.manshop.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.manshop.android.R;
import com.manshop.android.model.Order;
import com.manshop.android.ui.view.activity.OrderDetailActivity;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Order good = mList.get(position);
//        Glide.with(context).load(good.getPics().get(0)).into(holder.goodPic);


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "nnnnn", Toast.LENGTH_SHORT).show();
//                final Order good = mList.get(position);
//                Intent intent = new Intent(context, OrderDetailActivity.class);
//
//            }
//        });
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
            orderName = (TextView) itemView.findViewById(R.id.item_good_name);
            orderPrice = (TextView) itemView.findViewById(R.id.item_good_price);
        }
    }

}