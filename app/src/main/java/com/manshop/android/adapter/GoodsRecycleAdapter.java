package com.manshop.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.model.Goods;
import com.manshop.android.ui.view.activity.GoodDetailActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HP on 2017/12/27.
 */

public class GoodsRecycleAdapter extends RecyclerView.Adapter<GoodsRecycleAdapter.ViewHolder> {
    Context context;
    List<Goods> mList;

    public GoodsRecycleAdapter(Context context, List<Goods> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.item_goods, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Goods good = mList.get(position);
        Glide.with(context).load(good.getUser().getHead()).into(holder.photo);
        holder.username.setText(good.getUser().getUsername());
        holder.price.setText(good.getPrice());
        holder.detail.setText(good.getDetail());
        Log.d("good",good.getDetail());
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(manager);

        List<String> mPic = new ArrayList<>();
        String picture = good.getPicture();
        String[] txtpicture = picture.split(";");
        Collections.addAll(mPic, txtpicture);
        ImageAdapter adapter = new ImageAdapter(context,mPic);
        holder.recyclerView.setAdapter(adapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Goods good = mList.get(position);
                Intent intent = new Intent(context, GoodDetailActivity.class);
                intent.putExtra("gid", good.getId());
                intent.putExtra("uid",good.getUser().getId());
                intent.putExtra("photo", good.getUser().getHead());
                intent.putExtra("username", good.getUser().getUsername());
                intent.putExtra("title", good.getTitle());
                intent.putExtra("detail", good.getDetail());
                intent.putExtra("picture", good.getPicture());
                intent.putExtra("price",good.getPrice());
                intent.putExtra("rental",good.getRental());
                intent.putExtra("type", good.getType());
                intent.putExtra("state", good.getState());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
