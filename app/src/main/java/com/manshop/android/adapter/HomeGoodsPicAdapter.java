package com.manshop.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.manshop.android.R;

import java.util.List;

/**
 * Created by Lin on 2017/12/27.
 */

public class HomeGoodsPicAdapter extends RecyclerView.Adapter<HomeGoodsPicAdapter.ViewHolder> {
    Context context;
    List<String> mList;

    public HomeGoodsPicAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_goods_pic, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);

//        Uri uri = Uri.parse(mList.get(position));
        Glide.with(context).load(mList.get(position)).into(holder.draweeView);
//        holder.draweeView.setImageBitmap(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView draweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);
        }
    }
}
