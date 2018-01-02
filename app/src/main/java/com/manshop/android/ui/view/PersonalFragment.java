package com.manshop.android.ui.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.manshop.android.R;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends Fragment {
    private ListView LvPer;
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
        final TextView imlogin = (TextView) view.findViewById(R.id.imlogin);
        imlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imlogin.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        LvPer = (ListView) view.findViewById(R.id.lv_per);
        initPer();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mPerList);
        LvPer.setAdapter(adapter);
        LvPer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPerList.get(position);
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getActivity(),SaleActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
            }
        });
        return view;
    }

    public void initPer() {
        mPerList.add("我要发布");
        mPerList.add("我发布的");
        mPerList.add("我卖出的");
        mPerList.add("我出租的");
        mPerList.add("我买到的");
        mPerList.add("我收藏的");
    }
}
