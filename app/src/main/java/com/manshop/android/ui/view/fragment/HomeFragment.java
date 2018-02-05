package com.manshop.android.ui.view.fragment;

/**
 * Created by Lin on 2017/10/31.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.GoodsRecycleAdapter;
import com.manshop.android.model.Goods;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.view.activity.ComicActivity;
import com.manshop.android.util.Constant;
import com.manshop.android.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class HomeFragment extends Fragment {
    //商品列表
    private RecyclerView recyclerview;
    private GoodsRecycleAdapter adapter;
    private List<Goods> mGood = new ArrayList<>();
    //轮播图
    private List<Integer> images = new ArrayList<>();
    private String[] titles = {
            "title1",
            "title2",
            "title3",
            "title4",
            "title5",
    };

    //    private ScrollView mScrollView;
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
        //        轮播图
        Banner banner = (Banner) view.findViewById(R.id.banner);
        images.add(R.drawable.img_lunbo1);
        images.add(R.drawable.img_lunbo2);
        images.add(R.drawable.img_lunbo3);
        images.add(R.drawable.img_lunbo4);
        images.add(R.drawable.img_lunbo5);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(Arrays.asList(titles));
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
//        recyclerview.setNestedScrollingEnabled(false);
//        adapter = new GoodsRecycleAdapter(getActivity(), mGood);
//        recyclerview.setAdapter(adapter);

        TextView tvShow = (TextView) view.findViewById(R.id.tv_show);
        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ComicActivity.class));
            }
        });
    }

    private OkHttp okhttp = OkHttp.getOkhttpHelper();

    private void initData() {
        final List<String> mPic = new ArrayList<>();
//        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
//        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
//        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
//        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
//        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
//        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
//        mGood.add(
//                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
//                        "123", "456", "\n\n789", mPic));
//        mGood.add(
//                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
//                        "123", "456", "789", mPic));
//        mGood.add(
//                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
//                        "123", "456", "789", mPic));
//        mGood.add(
//                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
//                        "123", "456", "789", mPic));
//        mGood.add(
//                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
//                        "123", "456", "789", mPic));

        final Map<String, Object> param = new HashMap<>();
        param.put("uid", MyApplication.getInstance().getUserId());
        okhttp.doPost(Constant.baseURL + "goods/getGood", new CallBack(getActivity()) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                Log.d("good", "success");
                JSONObject json = JSON.parseObject((String) o);
                Object jsonArray = json.get("data");
                System.out.println(jsonArray);
                List<Goods> listGood = JSON.parseArray(jsonArray + "", Goods.class);
                Log.d("good", " " + mGood.size());
                for (Goods good : listGood) {
                    Log.d("good", " " + good.toString());
                    String picture = good.getPicture();
                    String[] txtpicture = picture.split(";");
                    Collections.addAll(mPic, txtpicture);
                    good.setPics(mPic);
                    mGood.add(good);
                    Log.d("address", " 2222221    " + mGood.size());
                }
                recyclerview.setNestedScrollingEnabled(false);
                adapter = new GoodsRecycleAdapter(getActivity(), mGood);
                recyclerview.setAdapter(adapter);
            }
        },param);
    }
}
