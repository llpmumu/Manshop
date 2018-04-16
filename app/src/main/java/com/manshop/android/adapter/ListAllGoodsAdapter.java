package com.manshop.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manshop.android.R;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.view.activity.GoodDetailActivity;
import java.util.List;

/**
 * Created by Lin on 2018/4/16.
 */

public class ListAllGoodsAdapter extends RecyclerView.Adapter<ListAllGoodsAdapter.ViewHolder> {
    private Context context;
    private List<Goods> mList;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    public ListAllGoodsAdapter(Context context, List<Goods> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ListAllGoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_all_good_list, null);
        ListAllGoodsAdapter.ViewHolder holder = new ListAllGoodsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ListAllGoodsAdapter.ViewHolder holder, final int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        final Goods good = mList.get(position);
//        Goods good = goods.getGood();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height=dm.heightPixels;
        float density = dm.density;
        int densityDpi=dm.densityDpi;

        int screenWidth = (int)(width/density);
        int screenHeight=(int)(height/density);

        ViewGroup.LayoutParams params = holder.goodsPic.getLayoutParams();
        params.width =screenWidth-10;
        holder.goodsPic.setLayoutParams(params);
        Glide.with(context).load(good.getPics().get(0)).into(holder.goodsPic);

////        holder.orderPic.setImageBitmap(good.getPics().get(0));
//        Log.d("order", goods.getState() + "       2222");
        holder.goodsName.setText(good.getTitle());
        holder.goodsPrice.setText(good.getPrice() + "￥");
        holder.goodsSort.setText(good.getSmallSort().getSortName());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                final Order order = mList.get(position);
//                Intent intent = new Intent(context, OrderDetailActivity.class);
//                intent.putExtra("oid", order.getId());
//                intent.putExtra("type", "old");
//                context.startActivity(intent);
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Goods good = mList.get(position);
                Intent intent = new Intent(context, GoodDetailActivity.class);
                intent.putExtra("gid", good.getId());
                intent.putExtra("targetId",good.getUser().getPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsPic;
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsSort;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsPic = (ImageView) itemView.findViewById(R.id.iv_goods);
            goodsName = (TextView) itemView.findViewById(R.id.tv_title);
            goodsPrice = (TextView) itemView.findViewById(R.id.tv_price);
            goodsSort = (TextView) itemView.findViewById(R.id.tv_sort);
        }
    }

}
