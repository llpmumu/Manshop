package com.manshop.android.controller;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.manshop.android.MyApplication;
import com.manshop.android.adapter.ConversationAdapter;
import com.manshop.android.ui.view.activity.ChatActivity;
import com.manshop.android.ui.view.fragment.ConversationFragment;
import com.manshop.android.util.SortConvList;
import com.manshop.android.util.SortTopConvList;
import com.manshop.android.view.ConversationListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;

public class ConversationListController implements View.OnClickListener,
        AdapterView.OnItemClickListener{

    private ConversationListView mConvListView;
    private ConversationFragment mContext;
    private int mWidth;
    private ConversationAdapter mListAdapter;
    private List<Conversation> mDatas = new ArrayList<Conversation>();
    private Dialog mDialog;

    public ConversationListController(ConversationListView listView, ConversationFragment context,
                                      int width) {
        this.mConvListView = listView;
        this.mContext = context;
        this.mWidth = width;
        initConvListAdapter();
    }

    List<Conversation> topConv = new ArrayList<>();
    List<Conversation> forCurrent = new ArrayList<>();
    List<Conversation> delFeedBack = new ArrayList<>();

    private void initConvListAdapter() {
        forCurrent.clear();
        topConv.clear();
        delFeedBack.clear();
        int i = 0;
        mDatas = JMessageClient.getConversationList();
        if (mDatas != null && mDatas.size() > 0) {
            mConvListView.setNullConversation(true);
            SortConvList sortConvList = new SortConvList();
            Collections.sort(mDatas, sortConvList);
            for (Conversation con : mDatas) {
                if (con.getTargetId().equals("feedback_Android")) {
                    delFeedBack.add(con);
                }
                if (!TextUtils.isEmpty(con.getExtra())) {
                    forCurrent.add(con);
                }
            }
            topConv.addAll(forCurrent);
            mDatas.removeAll(forCurrent);
            mDatas.removeAll(delFeedBack);

        } else {
            mConvListView.setNullConversation(false);
        }
        if (topConv != null && topConv.size() > 0) {
            SortTopConvList top = new SortTopConvList();
            Collections.sort(topConv, top);
            for (Conversation conv : topConv) {
                mDatas.add(i, conv);
                i++;
            }
        }
        mListAdapter = new ConversationAdapter(mContext.getActivity(), mDatas, mConvListView);
        mConvListView.setConvListAdapter(mListAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.create_group_btn:
//                mContext.showPopWindow();
//                break;
//            case R.id.search_title:
//                Intent intent = new Intent();
//                intent.setClass(mContext.getActivity(), SearchContactsActivity.class);
//                mContext.startActivity(intent);
//                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击会话条目
        Intent intent = new Intent();
        if (position > 0) {
            //这里-3是减掉添加的三个headView
            Conversation conv = mDatas.get(position - 1);
            intent.putExtra(MyApplication.CONV_TITLE, conv.getTitle());
            //群聊
            if (conv.getType() == ConversationType.single) {
                //单聊
                String targetId = ((UserInfo) conv.getTargetInfo()).getUserName();
                intent.putExtra(MyApplication.TARGET_ID, targetId);
                intent.putExtra(MyApplication.TARGET_APP_KEY, conv.getTargetAppKey());
                intent.putExtra(MyApplication.DRAFT, getAdapter().getDraft(conv.getId()));
                Log.d("Conv",targetId+" "+conv.getTargetAppKey()+" "+getAdapter().getDraft(conv.getId())+" "+conv.getId());
            } else {
                if (mListAdapter.includeAtMsg(conv)) {
                    intent.putExtra("atMsgId", mListAdapter.getAtMsgId(conv));
                }

                if (mListAdapter.includeAtAllMsg(conv)) {
                    intent.putExtra("atAllMsgId", mListAdapter.getatAllMsgId(conv));
                }
                long groupId = ((GroupInfo) conv.getTargetInfo()).getGroupID();
                intent.putExtra(MyApplication.GROUP_ID, groupId);
                intent.putExtra(MyApplication.DRAFT, getAdapter().getDraft(conv.getId()));
                intent.setClass(mContext.getActivity(), ChatActivity.class);
                mContext.getActivity().startActivity(intent);
                return;
            }
            intent.setClass(mContext.getActivity(), ChatActivity.class);
            mContext.getContext().startActivity(intent);

        }
    }

    public ConversationAdapter getAdapter() {
        return mListAdapter;
    }


}
