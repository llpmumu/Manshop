package com.manshop.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.model.Goods;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HP on 2017/12/27.
 */

public class GoodsRecycleAdapter extends RecyclerView.Adapter<GoodsRecycleAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    Context context;
    List<Goods> mList;

    public GoodsRecycleAdapter(Context context, List<Goods> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.item_goods, null);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Goods good = mList.get(position);
        Glide.with(context).load(good.getPhoto()).into(holder.photo);
        holder.username.setText(good.getUsername());
        holder.price.setText(good.getPrice());
        holder.detail.setText(good.getDetails());
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(manager);
        ImageAdapter adapter = new ImageAdapter(context,good.getPics());
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * item点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }


    @Override
    public boolean onLongClick(View v) {
        return true;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView photo;
        TextView username;
        TextView price;
        TextView detail;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = (RoundedImageView) itemView.findViewById(R.id.user_photo);
            username = (TextView) itemView.findViewById(R.id.user_name);
            price = (TextView) itemView.findViewById(R.id.goods_price);
            detail = (TextView) itemView.findViewById(R.id.goods_description);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.image_recycler);
        }
    }

    private Bitmap getBitmap(String url) throws IOException {
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        final Bitmap[] bitmap = new Bitmap[1];
        new Thread() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Response execute = null;
                try {
                    execute = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取流
                InputStream in = execute.body().byteStream();
                //转化为bitmap
                bitmap[0] = BitmapFactory.decodeStream(in);
            }
        }.start();
        return bitmap[0];
    }
}
