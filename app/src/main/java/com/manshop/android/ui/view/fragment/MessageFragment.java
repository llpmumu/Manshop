package com.manshop.android.ui.view.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.MessageAdapter;
import com.manshop.android.controller.ConversationListController;
import com.manshop.android.entity.Event;
import com.manshop.android.model.Dialogue;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseFragment;
import com.manshop.android.view.ConversationListView;


import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.eventbus.EventBus;
import okhttp3.Response;

/**
 * Created by Lin on 2017/10/31.
 */

public class MessageFragment extends BaseFragment {
    //    private RecyclerView msgRecycler;
//    private List<Dialogue> mMsg = new ArrayList<>();
//    ;
//    private MessageAdapter adapter;
//    private OkHttp okHttp = OkHttp.getOkhttpHelper();
//
    public static MessageFragment newInstance(String index) {
        MessageFragment f = new MessageFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        f.setArguments(args);
        return f;
    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_message, container, false);
//        init(view);
//        return view;
//    }
//
//    public void init(View view) {
//        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
//        msgRecycler = (RecyclerView) view.findViewById(R.id.msg_recycler);
//        msgRecycler.setLayoutManager(manager);
//        getMsgList();
//    }
//
//    //填写收货地址
//    public void getMsgList() {
//        final Map<String, Object> params = new HashMap<>();
//        params.put("sender", MyApplication.getInstance().getUserId());
//        okHttp.doPost(Constant.baseURL + "message/getMsgList", new CallBack(getContext()) {
//            @Override
//            public void onError(Response response, Exception e) throws IOException {
//
//            }
//
//            @Override
//            public void callBackSuccess(Response response, Object o) throws IOException {
//                JSONObject json = JSON.parseObject((String) o);
//                Object jsonArray = json.get("data");
//                List<Dialogue> listDialogue = JSON.parseArray(jsonArray + "", Dialogue.class);
//                Log.d("msg11", "" + listDialogue.size());
//                for (Dialogue dialogue : listDialogue) {
//                    mMsg.add(dialogue);
//                }
//                msgRecycler.setNestedScrollingEnabled(false);
//                adapter = new MessageAdapter(getActivity(), mMsg);
//                msgRecycler.setAdapter(adapter);
//            }
//        }, params);
//    }


    private Activity mContext;
    private View mRootView;
    private ConversationListView mConvListView;
    private ConversationListController mConvListController;
    private HandlerThread mThread;
    private static final int REFRESH_CONVERSATION_LIST = 0x3000;
    private static final int DISMISS_REFRESH_HEADER = 0x3001;
    private static final int ROAM_COMPLETED = 0x3002;
    private BackgroundHandler mBackgroundHandler;
    private NetworkReceiver mReceiver;
    protected boolean isCreate = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate = true;
        mContext = this.getActivity();

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.fragment_message, (ViewGroup) getActivity().findViewById(R.id.vp_min), false);
        mConvListView = new ConversationListView(mRootView, this.getActivity(), this);
        mConvListView.initModule();
        mThread = new HandlerThread("MainActivity");
        mThread.start();
        mBackgroundHandler = new BackgroundHandler(mThread.getLooper());
        mConvListController = new ConversationListController(mConvListView, this, mWidth);
        mConvListView.setItemListeners(mConvListController);

        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (null == activeInfo) {
            mConvListView.showHeaderView();
        } else {
            mConvListView.dismissHeaderView();
            mBackgroundHandler.sendEmptyMessageDelayed(DISMISS_REFRESH_HEADER, 1000);
        }
        initReceiver();

    }

    private void initReceiver() {
        mReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(mReceiver, filter);
    }

    //监听网络状态的广播
    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                if (null == activeInfo) {
                    mConvListView.showHeaderView();
                } else {
                    mConvListView.dismissHeaderView();
                }
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

//    public void showPopWindow() {
//        mMenuPopWindow.setTouchable(true);
//        mMenuPopWindow.setOutsideTouchable(true);
//        mMenuPopWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//        if (mMenuPopWindow.isShowing()) {
//            mMenuPopWindow.dismiss();
//        } else {
//            mMenuPopWindow.showAsDropDown(mRootView.findViewById(R.id.create_group_btn), -10, -5);
//        }
//    }

    /**
     * 收到消息
     */
    public void onEvent(MessageEvent event) {
        mConvListView.setUnReadMsg(JMessageClient.getAllUnReadMsgCount());
        Message msg = event.getMessage();
        if (msg.getTargetType() == ConversationType.group) {
            long groupId = ((GroupInfo) msg.getTargetInfo()).getGroupID();
            Conversation conv = JMessageClient.getGroupConversation(groupId);
            if (conv != null && mConvListController != null) {
                if (msg.isAtMe()) {
                    MyApplication.isAtMe.put(groupId, true);
                    mConvListController.getAdapter().putAtConv(conv, msg.getId());
                }
                if (msg.isAtAll()) {
                    MyApplication.isAtall.put(groupId, true);
                    mConvListController.getAdapter().putAtAllConv(conv, msg.getId());
                }
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST,
                        conv));
            }
        } else {
            final UserInfo userInfo = (UserInfo) msg.getTargetInfo();
            String targetId = userInfo.getUserName();
            Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
            if (conv != null && mConvListController != null) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(userInfo.getAvatar())) {
                            userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                                @Override
                                public void gotResult(int responseCode, String responseMessage, Bitmap avatarBitmap) {
                                    if (responseCode == 0) {
                                        mConvListController.getAdapter().notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                });
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            }
        }
    }

    /**
     * 接收离线消息
     *
     * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        if (!conv.getTargetId().equals("feedback_Android")) {
            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
        }
    }

    /**
     * 消息撤回
     */
    public void onEvent(MessageRetractEvent event) {
        Conversation conversation = event.getConversation();
        mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conversation));
    }

    /**
     * 消息已读事件
     */
    public void onEventMainThread(MessageReceiptStatusChangeEvent event) {
        mConvListController.getAdapter().notifyDataSetChanged();
    }

    /**
     * 消息漫游完成事件
     *
     * @param event 漫游完成后， 刷新会话事件
     */
    public void onEvent(ConversationRefreshEvent event) {
        Conversation conv = event.getConversation();
        if (!conv.getTargetId().equals("feedback_Android")) {
            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            //多端在线未读数改变时刷新
            if (event.getReason().equals(ConversationRefreshEvent.Reason.UNREAD_CNT_UPDATED)) {
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            }
        }
    }

    private class BackgroundHandler extends Handler {
        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            final Conversation conv;
            switch (msg.what) {
                case REFRESH_CONVERSATION_LIST:
                    conv = (Conversation) msg.obj;
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mConvListView.setNullConversation(true);
                            mConvListController.getAdapter().setToTop(conv);
                        }
                    });
                    break;
                case DISMISS_REFRESH_HEADER:
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mConvListView.dismissLoadingHeader();
                        }
                    });
                    break;
                case ROAM_COMPLETED:
                    conv = (Conversation) msg.obj;
                    mConvListController.getAdapter().addAndSort(conv);
                    break;
            }
        }
    }

    public void onEventMainThread(Event event) {
        switch (event.getType()) {
            case createConversation:
                Conversation conv = event.getConversation();
                if (conv != null) {
                    mConvListController.getAdapter().addNewConversation(conv);
                }
                break;
            case deleteConversation:
                conv = event.getConversation();
                if (null != conv) {
                    mConvListController.getAdapter().deleteConversation(conv);
                }
                break;
            //收到保存为草稿事件
            case draft:
                conv = event.getConversation();
                String draft = event.getDraft();
                //如果草稿内容不为空，保存，并且置顶该会话
                if (!TextUtils.isEmpty(draft)) {
                    mConvListController.getAdapter().putDraftToMap(conv, draft);
                    mConvListController.getAdapter().setToTop(conv);
                    //否则删除
                } else {
                    mConvListController.getAdapter().delDraftFromMap(conv);
                }
                break;
            case addFriend:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mConvListController.getAdapter().notifyDataSetChanged();
    }

//    public void dismissPopWindow() {
//        if (mMenuPopWindow.isShowing()) {
//            mMenuPopWindow.dismiss();
//        }
//    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mContext.unregisterReceiver(mReceiver);
        mBackgroundHandler.removeCallbacksAndMessages(null);
        mThread.getLooper().quit();
        super.onDestroy();
    }

    public void sortConvList() {
        if (mConvListController != null) {
            mConvListController.getAdapter().sortConvList();
        }
    }
}
