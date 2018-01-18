package com.manshop.android.ui.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.ui.view.activity.LoginActivity;
import com.manshop.android.ui.view.activity.PublishActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PersonalFragment extends Fragment {
    private ListView LvPer;
    private TextView tvLogin;
    private RoundedImageView head;
    private ArrayAdapter<String> adapter;
    private List<String> mPerList = new ArrayList<>();

    public static PersonalFragment newInstance(String info) {
        PersonalFragment fragment = new PersonalFragment();
        Bundle args = new Bundle();
        args.putString("ic_info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        init(view);
        addListener();
        return view;
    }

    public void init(View view){
        SharedPreferences user = getActivity().getSharedPreferences("User",MODE_PRIVATE);
        tvLogin = (TextView) view.findViewById(R.id.tvlogin);
        head = (RoundedImageView) view.findViewById(R.id.icon_image);
        showUser(user);

        //ListView列表
        initPer();
        LvPer = (ListView) view.findViewById(R.id.lv_per);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mPerList);
        LvPer.setAdapter(adapter);

    }

    public void showUser(SharedPreferences user){
        if (user != null){
            head.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.INVISIBLE);
            Glide.with(getActivity()).load(user.getString("head","")).into(head);
        }
        else {
            head.setVisibility(View.INVISIBLE);
            tvLogin.setVisibility(View.VISIBLE);
            Log.d("user","un");
        }
    }

    public void addListener(){
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });

        LvPer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPerList.get(position);
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getActivity(),PublishActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
            }
        });
    }

    public void initPer() {
        mPerList.add("我发布的");
        mPerList.add("我卖出的");
        mPerList.add("我出租的");
        mPerList.add("我买到的");
        mPerList.add("我收藏的");
    }
}