package com.manshop.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manshop.android.R;
import com.manshop.android.model.Show;
import com.manshop.android.okHttp.OkHttp;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Lin on 2018/3/16.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    private Context context;
    private List<Show> mList;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    public ShowAdapter(Context context, List<Show> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_show, null);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         * 给itemView设置tag
         */
        holder.itemView.setTag(position);
        Show show = mList.get(position);
        Glide.with(context).load(show.getPicture()).into(holder.ivShow);
        holder.tvShowName.setText(show.getTitle());
        holder.tvShowAddress.setText(show.getAddress());
        holder.tvShowData.setText(show.getShowdata());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivShow;
        TextView tvShowName;
        TextView tvShowAddress;
        TextView tvShowData;

        public ViewHolder(View itemView) {
            super(itemView);
            ivShow = (ImageView) itemView.findViewById(R.id.iv_show);
            tvShowName = (TextView) itemView.findViewById(R.id.tv_show_name);
            tvShowAddress = (TextView) itemView.findViewById(R.id.tv_show_address);
            tvShowData = (TextView) itemView.findViewById(R.id.tv_show_data);
        }
    }


}