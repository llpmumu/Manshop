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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.ui.view.activity.ListOrderActivity;
import com.manshop.android.ui.view.activity.ListSellActivity;
import com.manshop.android.ui.view.activity.LoginActivity;
import com.manshop.android.ui.view.activity.ListPublishActivity;
import com.manshop.android.ui.view.activity.MainActivity;
import com.manshop.android.ui.view.activity.PerInfoActivity;
import com.manshop.android.utils.HandleResponseCode;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

import static android.content.Context.MODE_PRIVATE;

public class PersonalFragment extends Fragment {
    //    private ListView LvPer;
    private TextView tvLogin;
    private RoundedImageView head;
    private Button btnToLogin;
    private LinearLayout linearLayoutShare;
    private LinearLayout linearLayoutBuy;
    private LinearLayout linearLayoutSell;

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

        linearLayoutShare = (LinearLayout) view.findViewById(R.id.share);
        linearLayoutBuy = (LinearLayout) view.findViewById(R.id.buy);
        linearLayoutSell = (LinearLayout) view.findViewById(R.id.sell);
    }

    public void showUser(SharedPreferences user) {
        if (user.getInt("id", -1) != -1) {
//            head.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(user.getString("head", "")).into(head);
            JMessageClient.getUserInfo(user.getString("phone", ""), new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == 0) {
                        if (userInfo.getAvatarFile() != null) {
                            tvLogin.setText(userInfo.getNickname());
                            Glide.with(getContext()).load(userInfo.getAvatarFile().getAbsolutePath()).into(head);
//                      holder.business_head.setImageBitmap(BitmapFactory.decodeFile(userInfo.getAvatarFile().getAbsolutePath()));
                        } else {
                            head.setImageResource(R.drawable.jmui_head_icon);
                        }
                    } else {
                        HandleResponseCode.onHandle(getContext(), i, false);
                    }
                }
            });
//            tvLogin.setText(user.getString("username", ""));
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PerInfoActivity.class);
                    startActivity(intent);
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
        //发布的
        linearLayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListPublishActivity.class);
                startActivity(intent);
            }
        });
        //买到的
        linearLayoutBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ListOrderActivity.class));
            }
        });
        //卖出的
        linearLayoutSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ListSellActivity.class));
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences user = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        showUser(user);
    }
}
