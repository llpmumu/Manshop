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
import com.manshop.android.model.Dialogue;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.util.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by Lin on 2017/10/31.
 */

public class MessageFragment extends Fragment {
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
        return view;
    }

    public void init(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        msgRecycler= (RecyclerView) view.findViewById(R.id.msg_recycler);
        msgRecycler.setLayoutManager(manager);
        getMsgList();
    }

    //填写收货地址
    public void getMsgList() {
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
}
