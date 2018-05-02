package com.manshop.android.ui.view.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.ui.view.activity.ListOrderActivity;
import com.manshop.android.ui.view.activity.ListSellActivity;
import com.manshop.android.ui.view.activity.LoginActivity;
import com.manshop.android.ui.view.activity.ListPublishActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PersonalFragment extends Fragment {
    private ListView LvPer;
    private TextView tvLogin;
    private RoundedImageView head;
    private Button btnToLogin;
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

    public void init(View view) {
        SharedPreferences user = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        tvLogin = (TextView) view.findViewById(R.id.tvlogin);
        head = (RoundedImageView) view.findViewById(R.id.icon_image);
        btnToLogin = (Button) view.findViewById(R.id.btn_to_login);
        showUser(user);

        //ListView列表
        initPer();
        LvPer = (ListView) view.findViewById(R.id.lv_per);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mPerList);
        LvPer.setAdapter(adapter);

    }

    public void showUser(SharedPreferences user) {
        if (user.getInt("id", -1) != -1) {
//            head.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(user.getString("head", "")).into(head);
            tvLogin.setText(user.getString("username", ""));
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), PerInfoActivity.class);
//                    startActivity(intent);
                }
            });
            btnToLogin.setVisibility(View.GONE);
        } else {
            head.setImageResource(R.drawable.ic_unlogin);
            tvLogin.setText("欢迎来到漫街");
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            btnToLogin.setVisibility(View.VISIBLE);
        }
    }

    public void addListener() {
        LvPer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPerList.get(position);
                switch (position) {
                    case 0:
                        //发布的
                        Intent intent = new Intent(getActivity(), ListPublishActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //买到的
                        startActivity(new Intent(getContext(), ListOrderActivity.class));
                        break;
                    case 2:
                        //卖出的
                        startActivity(new Intent(getContext(), ListSellActivity.class));
                        break;
                    default:
                }
            }
        });
    }

    public void initPer() {
        mPerList.add("我发布的");
        mPerList.add("我买到的");
        mPerList.add("我卖出的");
//        mPerList.add("我买到的");
//        mPerList.add("我收藏的");
    }
}
