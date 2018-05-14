package com.manshop.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.model.Goods;
import com.manshop.android.ui.view.activity.GoodDetailActivity;
import com.manshop.android.utils.HandleResponseCode;
import com.manshop.android.utils.ImageLoadUtils;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by HP on 2017/12/27.
 */

public class HomeGoodsListAdapter extends RecyclerView.Adapter<HomeGoodsListAdapter.ViewHolder> {
    Context context;
    List<Goods> mList;

    public HomeGoodsListAdapter(Context context, List<Goods> mList) {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Goods good = mList.get(position);
//        Glide.with(context).load(good.getUser().getHead()).into(holder.photo);

        JMessageClient.getUserInfo(good.getUser().getPhone(), new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == 0) {
                    if (userInfo.getAvatarFile() != null) {
                        Glide.with(context).load(userInfo.getAvatarFile().getAbsolutePath()).into(holder.photo);
//                      holder.business_head.setImageBitmap(BitmapFactory.decodeFile(userInfo.getAvatarFile().getAbsolutePath()));
                    } else {
                        holder.photo.setImageResource(R.drawable.jmui_head_icon);
                    }
                } else {
                    HandleResponseCode.onHandle(context, i, false);
                }
            }
        });
        holder.username.setText(good.getUser().getUsername());
        holder.price.setText(good.getPrice() + "￥");
        holder.detail.setText(good.getDetail());
        Log.d("good", good.getDetail());
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(manager);

        HomeGoodsPicAdapter adapter = new HomeGoodsPicAdapter(context, ImageLoadUtils.displayGoodsImage(good.getPicture()));
        holder.recyclerView.setAdapter(adapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Goods good = mList.get(position);
                Intent intent = new Intent(context, GoodDetailActivity.class);
                intent.putExtra("gid", good.getId());
                intent.putExtra("targetId", good.getUser().getPhone());
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
            recyclerView = (RecyclerView) itemView.findViewById(R.id.image_recycler);
        }
    }

//    private Bitmap getBitmap(String url) throws IOException {
//        //创建一个Request
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//        //new call
//        final Bitmap[] bitmap = new Bitmap[1];
//        new Thread() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//                Response execute = null;
//                try {
//                    execute = client.newCall(request).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //获取流
//                InputStream in = execute.body().byteStream();
//                //转化为bitmap
//                bitmap[0] = BitmapFactory.decodeStream(in);
//            }
//        }.start();
//        return bitmap[0];
//    }
}
