package com.manshop.android.ui.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.MessageAdapter;
import com.manshop.android.adapter.SellOrderAdapter;
import com.manshop.android.model.Address;
import com.manshop.android.model.Dialogue;
import com.manshop.android.model.Goods;
import com.manshop.android.model.Message;
import com.manshop.android.model.Order;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.view.activity.ListSellActivity;
import com.manshop.android.ui.view.activity.MyNewOrderActivity;
import com.manshop.android.util.Constant;
import com.manshop.android.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by Lin on 2017/10/31.
 */

public class MessageFragment extends Fragment {
//    @Bind(R.id.msg_recycler)
//    RecyclerView msgRecycler;
    private RecyclerView msgRecycler;
    private List<Dialogue> mMsg = new ArrayList<>();;
    private MessageAdapter adapter;
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    public static MessageFragment newInstance(String index) {
        MessageFragment f = new MessageFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        init(view);
//        initData();
        return view;
    }

    public void init(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        msgRecycler= (RecyclerView) view.findViewById(R.id.msg_recycler);
        msgRecycler.setLayoutManager(manager);
        addAddress();
    }

    //填写收货地址
    public void addAddress() {
        final Map<String, Object> params = new HashMap<>();
        params.put("sender", MyApplication.getInstance().getUserId());
        okHttp.doPost(Constant.baseURL + "message/getMsgList", new CallBack(getContext()) {
            @Override
            public void onError(Response response, Exception e) throws IOException {

            }
            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                List<Dialogue> listDialogue = JSON.parseArray(jsonArray + "", Dialogue.class);
                Log.d("msg11",""+listDialogue.size());
                for (Dialogue dialogue:listDialogue) {
                    mMsg.add(dialogue);
                }
                msgRecycler.setNestedScrollingEnabled(false);
                adapter = new MessageAdapter(getActivity(), mMsg);
                msgRecycler.setAdapter(adapter);
            }
        }, params);
    }

//    private void initData() {
//        mMsg = new ArrayList<>();
//        mMsg.add(new Message("http://img0.imgtn.bdimg.com/it/u=1345929477,2604128565&fm=27&gp=0.jpg", "lo", "123", "12:14:12"));
//        mMsg.add(new Message("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1049464934,487681744&fm=27&gp=0.jpg", "nh", "123", "12:14:12"));
//        mMsg.add(new Message("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3965109217,1887678806&fm=27&gp=0.jpg", "123", "123", "12:14:12"));
//        mMsg.add(new Message("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=836247249,3741295144&fm=27&gp=0.jpg", "wq", "123", "12:14:12"));
//        mMsg.add(new Message("http://img5.imgtn.bdimg.com/it/u=1130386333,2651990884&fm=27&gp=0.jpg", "123", "123", "12:14:12"));
//        mMsg.add(new Message("http://img0.imgtn.bdimg.com/it/u=3028691947,703373027&fm=27&gp=0.jpg", "123", "123", "12:14:12"));
//        mMsg.add(new Message("http://img3.imgtn.bdimg.com/it/u=2059737542,1874731615&fm=27&gp=0.jpg", "123", "123", "12:14:12"));
//    }
}
