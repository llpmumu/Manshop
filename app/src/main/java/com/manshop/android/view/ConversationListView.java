package com.manshop.android.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.manshop.android.R;
import com.manshop.android.ui.view.fragment.MessageFragment;
import com.manshop.android.util.ThreadUtil;

/**
 * Created by ${chenyn} on 2017/3/13.
 */

public class ConversationListView {
    private View mConvListFragment;
    private ListView mConvListView = null;
    private LinearLayout mHeader;
    private Context mContext;
    private TextView mNull_conversation;
    private MessageFragment mFragment;

    public ConversationListView(View view, Context context, MessageFragment fragment) {
        this.mConvListFragment = view;
        this.mContext = context;
        this.mFragment = fragment;
    }

    public void initModule() {
        mConvListView = (ListView) mConvListFragment.findViewById(R.id.conv_list_view);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = (LinearLayout) inflater.inflate(R.layout.conv_list_head_view, mConvListView, false);
        mNull_conversation = (TextView) mConvListFragment.findViewById(R.id.null_conversation);
        mConvListView.addHeaderView(mHeader);
    }

    public void setConvListAdapter(ListAdapter adapter) {
        mConvListView.setAdapter(adapter);
    }


    public void setItemListeners(AdapterView.OnItemClickListener onClickListener) {
        mConvListView.setOnItemClickListener(onClickListener);
    }

    public void setLongClickListener(AdapterView.OnItemLongClickListener listener) {
        mConvListView.setOnItemLongClickListener(listener);
    }


    public void showHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.VISIBLE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.VISIBLE);
    }

    public void dismissHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.GONE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.GONE);
    }

    public void setNullConversation(boolean isHaveConv) {
        if (isHaveConv) {
            mNull_conversation.setVisibility(View.GONE);
        } else {
            mNull_conversation.setVisibility(View.VISIBLE);
        }
    }


    public void setUnReadMsg(final int count) {
//        ThreadUtil.runInUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mAllUnReadMsg != null) {
//                    if (count > 0) {
//                        mAllUnReadMsg.setVisibility(View.VISIBLE);
//                        if (count < 100) {
//                            mAllUnReadMsg.setText(count + "");
//                        } else {
//                            mAllUnReadMsg.setText("99+");
//                        }
//                    } else {
//                        mAllUnReadMsg.setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
    }


}
