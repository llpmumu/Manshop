package com.manshop.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.model.Address;
import com.manshop.android.model.Dialogue;
import com.manshop.android.model.User;
import com.manshop.android.ui.view.activity.DialogueActivity;

import java.util.List;

/**
 * Created by Lin on 2018/1/4.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private List<Dialogue> mMsgList;

    public MessageAdapter(Context context, List<Dialogue> mMsgList) {
        this.context = context;
        this.mMsgList = mMsgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_message_list, null);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final Dialogue dialogue = mMsgList.get(position);
                Intent intent = new Intent(context, DialogueActivity.class);
                if (dialogue.getSender() != MyApplication.getInstance().getUserId())
                    intent.putExtra("sid", dialogue.getSender());
                else if (dialogue.getReceiver() != MyApplication.getInstance().getUserId())
                    intent.putExtra("sid", dialogue.getReceiver());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Dialogue dialogue = mMsgList.get(position);
        User user = new User();
        if (dialogue.getSender() != MyApplication.getInstance().getUserId())
            user = dialogue.getSuser();
        else if (dialogue.getReceiver() != MyApplication.getInstance().getUserId())
            user = dialogue.getRuser();
        Glide.with(context).load(user.getHead()).into(holder.head);
        holder.username.setText(user.getUsername());

//        holder.lastMsg.setText(dialogue.getMsg());
//        holder.time.setText(dialogue.getMsgtime().toString());
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
//            lastMsg = (TextView) itemView.findViewById(R.id.msglist_msg);
//            time = (TextView) itemView.findViewById(R.id.msglist_time);
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}

