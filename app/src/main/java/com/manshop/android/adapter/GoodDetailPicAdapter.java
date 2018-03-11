package com.manshop.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.manshop.android.R;

import java.util.List;

/**
 * Created by Lin on 2018/2/22.
 */

public class GoodDetailPicAdapter extends RecyclerView.Adapter<GoodDetailPicAdapter.ViewHolder> {
    private Context context;
    private List<Bitmap> mList;

    public GoodDetailPicAdapter(Context context, List<Bitmap> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_detail_pic, null);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
//        Uri picuri = Uri.parse(mList.get(position));
//        String picuri = mList.get(position);
//        holder.picture.setImageURI(picuri);
//        Glide.with(context).load(picuri).into(holder.picture);
        Log.i("syso", position + ":" + mList.get(position).toString());
        holder.picture.setImageBitmap(mList.get(position));
//        Log.d("good", "111111111" + mList.get(position));
//        String ppp = mList.get(position);
//        holder.pic.setText(ppp);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        SimpleDraweeView picture;
        ImageView picture;
//        TextView pic;

        public ViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.detail_pic);
//            pic = (TextView) itemView.findViewById(R.id.detail_txt);
        }
    }
}

