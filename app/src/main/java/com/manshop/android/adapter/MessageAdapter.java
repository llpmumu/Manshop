package com.manshop.android.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.model.Message;

import java.util.List;

/**
 * Created by Lin on 2018/1/4.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private List<Message> mMsgList;

    public MessageAdapter(Context context, List<Message> mMsgList) {
        this.context = context;
        this.mMsgList = mMsgList;
    }


    public MessageAdapter(List<Message> msgList) {
        mMsgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_message_list, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Message msg = mMsgList.get(position);
        Glide.with(context).load(msg.getHead()).into(holder.head);
        holder.username.setText(msg.getUsername());
        holder.lastMsg.setText(msg.getLastMsg());
        holder.time.setText(msg.getTime());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView head;
        TextView username;
        TextView lastMsg;
        TextView time;
        public ViewHolder(View view) {
            super(view);
            head = (RoundedImageView) itemView.findViewById(R.id.icon_image);
            username = (TextView) itemView.findViewById(R.id.msglist_name);
            lastMsg = (TextView) itemView.findViewById(R.id.msglist_msg);
            time = (TextView) itemView.findViewById(R.id.msglist_time);
        }
    }
    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}

