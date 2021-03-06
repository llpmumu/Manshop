package com.manshop.android.ui.view.fragment;

/**
 * Created by Lin on 2017/10/31.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.HomeGoodsListAdapter;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.view.activity.AllGoodsActivity;
import com.manshop.android.ui.view.activity.AnimeDataActivity;
import com.manshop.android.ui.view.activity.ShowActivity;
import com.manshop.android.utils.Constant;
import com.manshop.android.utils.GlideImageLoader;
import com.manshop.android.utils.ImageLoadUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class HomeFragment extends Fragment {
    //商品列表
    private RecyclerView recyclerview;
    private HomeGoodsListAdapter adapter;
    private List<Goods> mGood = new ArrayList<>();
    private MaterialRefreshLayout materialRefreshLayout;
    //轮播图
    private List<Integer> images = new ArrayList<>();
    private OkHttp okhttp = OkHttp.getOkhttpHelper();

    public static HomeFragment newInstance(String index) {
        HomeFragment f = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.matfresh_refresh);
        // 轮播图
        Banner banner = (Banner) view.findViewById(R.id.banner);
        images.add(R.drawable.img_lunbo1);
        images.add(R.drawable.img_lunbo2);
        images.add(R.drawable.img_lunbo3);
        images.add(R.drawable.img_lunbo4);
        images.add(R.drawable.img_lunbo5);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

//        商品列表
        initData();
        recyclerview = (RecyclerView) view.findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);

        TextView tvBuy = (TextView) view.findViewById(R.id.tv_buy);
        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllGoodsActivity.class));
            }
        });
        TextView tvShow = (TextView) view.findViewById(R.id.tv_show);
        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShowActivity.class));
            }
        });
        TextView tvData = (TextView) view.findViewById(R.id.tv_data);
        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AnimeDataActivity.class));
            }
        });

        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                initData();
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
            }
        });
    }


    private void initData() {
        mGood.clear();
        final List<String> mPic = new ArrayList<>();
        final Map<String, Object> param = new HashMap<>();
        param.put("uid", MyApplication.getInstance().getUserId());
        okhttp.doPost(Constant.baseURL + "goods/getGood", new CallBack(getActivity()) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                System.out.println(jsonArray);
                List<Goods> listGood = JSON.parseArray(jsonArray + "", Goods.class);
                for (Goods good : listGood) {
                    good.setPics(ImageLoadUtils.displayGoodsImage(good.getPicture()));
                    mGood.add(good);
                }
                recyclerview.setNestedScrollingEnabled(false);
                adapter = new HomeGoodsListAdapter(getActivity(), mGood);
                recyclerview.setAdapter(adapter);
            }
        }, param);
    }

}
