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
import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.manshop.android.R;
import com.manshop.android.model.Address;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.view.activity.NewPublishActivity;
import com.manshop.android.utils.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by Lin on 2018/1/12.
 */

public class ListPublishAdapter extends RecyclerView.Adapter<ListPublishAdapter.ViewHolder> {
    Context context;
    List<Goods> mList;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    public ListPublishAdapter(Context context, List<Goods> mList) {
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
//        holder.goodPic.setImageBitmap(good.getPics().get(0));
        holder.goodName.setText(good.getTitle());
        holder.goodPrice.setText(good.getPrice() + "￥");

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "nnnnn", Toast.LENGTH_SHORT).show();
//                final Goods good = mList.get(position);
//                Intent intent = new Intent(context, NewPublishActivity.class);
//                intent.putExtra("id", good.getId());
//                intent.putExtra("title", good.getTitle());
//                intent.putExtra("detail", good.getDetail());
//                intent.putExtra("picture", good.getPicture());
//                intent.putExtra("price", good.getPrice());
//                intent.putExtra("rental", good.getRental());
//                intent.putExtra("type", good.getType());
//                intent.putExtra("state", good.getState());
//                intent.putExtra("isEdite", true);
//                context.startActivity(intent);
//            }
//        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = holder.getAdapterPosition();
                final Goods goods = mList.get(position);
                final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(context);
                mMaterialDialog.setMessage("确定删除该商品？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteGood(goods.getId(), position);
                                mMaterialDialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .show();
                return false;
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

    public void deleteGood(int id, final int position){
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        okHttp.doPost(Constant.baseURL + "goods/deleteGood", new CallBack(context) {
            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                mList.remove(position);
                notifyDataSetChanged();
            }
        }, param);
    }

}