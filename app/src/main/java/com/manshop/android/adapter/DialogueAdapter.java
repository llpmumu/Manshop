package com.manshop.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.model.Dialogue;

import java.util.List;

/**
 * Created by Lin on 2017/10/19.
 */

public class DialogueAdapter extends RecyclerView.Adapter<DialogueAdapter.ViewHolder> {
    private List<Dialogue> mDiaList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftDia;
        TextView rightDia;

        public ViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftDia = (TextView) view.findViewById(R.id.left_msg);
            rightDia = (TextView) view.findViewById(R.id.right_msg);
        }
    }

    public DialogueAdapter(List<Dialogue> msgList) {
        mDiaList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialogue, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dialogue dialogue = mDiaList.get(position);
        if (dialogue.getSender() == MyApplication.getInstance().getUserId()) {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightDia.setText(dialogue.getMsg());
        } else {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftDia.setText(dialogue.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return mDiaList.size();
    }
}
