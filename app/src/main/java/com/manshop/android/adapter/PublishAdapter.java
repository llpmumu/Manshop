package com.manshop.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.manshop.android.R;
import com.manshop.android.model.Goods;
import com.manshop.android.ui.view.activity.EditAddressActivity;
import com.manshop.android.ui.view.activity.NewPublishActivity;

import java.util.List;

/**
 * Created by Lin on 2018/1/12.
 */

public class PublishAdapter extends RecyclerView.Adapter<PublishAdapter.ViewHolder> {
    Context context;
    List<Goods> mList;

    public PublishAdapter(Context context, List<Goods> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_publish, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Goods good = mList.get(position);
        Glide.with(context).load(good.getPics().get(0)).into(holder.goodPic);
        holder.goodName.setText(good.getTitle());
        holder.goodPrice.setText(good.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "nnnnn", Toast.LENGTH_SHORT).show();
                final Goods good = mList.get(position);
                Intent intent = new Intent(context, NewPublishActivity.class);
                intent.putExtra("id", good.getId());
                intent.putExtra("title", good.getTitle());
                intent.putExtra("detail", good.getDetail());
                intent.putExtra("picture", good.getPicture());
                intent.putExtra("price",good.getPrice());
                intent.putExtra("rental",good.getRental());
                intent.putExtra("type", good.getType());
                intent.putExtra("state", good.getState());
                intent.putExtra("isEdite", true);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goodPic;
        TextView goodName;
        TextView goodPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            goodPic = (ImageView) itemView.findViewById(R.id.item_pic);
            goodName = (TextView) itemView.findViewById(R.id.item_good_name);
            goodPrice = (TextView) itemView.findViewById(R.id.item_good_price);
        }
    }

}