package com.manshop.android.ui.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manshop.android.R;
import com.manshop.android.adapter.MessageAdapter;
import com.manshop.android.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 2017/10/31.
 */

public class MessageFragment extends Fragment {
    public static MessageFragment newInstance(String index) {
        MessageFragment f = new MessageFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        f.setArguments(args);
        return f;
    }

    private List<Message> mMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initData();

        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.msg_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setNestedScrollingEnabled(false);
        MessageAdapter adapter = new MessageAdapter(getActivity(), mMsg);
        recyclerview.setAdapter(adapter);
        return view;
    }

    private void initData() {
        mMsg = new ArrayList<>();
        mMsg.add(new Message("http://img0.imgtn.bdimg.com/it/u=1345929477,2604128565&fm=27&gp=0.jpg", "lo", "123", "12:14:12"));
        mMsg.add(new Message("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1049464934,487681744&fm=27&gp=0.jpg", "nh", "123", "12:14:12"));
        mMsg.add(new Message("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3965109217,1887678806&fm=27&gp=0.jpg", "123", "123", "12:14:12"));
        mMsg.add(new Message("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=836247249,3741295144&fm=27&gp=0.jpg", "wq", "123", "12:14:12"));
        mMsg.add(new Message("http://img5.imgtn.bdimg.com/it/u=1130386333,2651990884&fm=27&gp=0.jpg", "123", "123", "12:14:12"));
        mMsg.add(new Message("http://img0.imgtn.bdimg.com/it/u=3028691947,703373027&fm=27&gp=0.jpg", "123", "123", "12:14:12"));
        mMsg.add(new Message("http://img3.imgtn.bdimg.com/it/u=2059737542,1874731615&fm=27&gp=0.jpg", "123", "123", "12:14:12"));
    }
}
