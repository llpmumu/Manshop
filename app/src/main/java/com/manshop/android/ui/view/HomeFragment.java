package com.manshop.android.ui.view;

/**
 * Created by Lin on 2017/10/31.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.manshop.android.R;
import com.manshop.android.adapter.GoodsRecycleAdapter;
import com.manshop.android.model.Goods;
import com.manshop.android.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    //商品列表
    private RecyclerView recyclerview;
    private List<Goods> mGood;
    //轮播图
    private List<Integer> images = new ArrayList<>();
    private String[] titles = {
            "title1",
            "title2",
            "title3",
            "title4",
            "title5",
    };

    private ScrollView mScrollView;
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

        initData();

        recyclerview = (RecyclerView) view.findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        GoodsRecycleAdapter adapter = new GoodsRecycleAdapter(getActivity(), mGood);
        recyclerview.setAdapter(adapter);

        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        return view;
    }

    private void initData() {
        mGood = new ArrayList<>();
        List<String> mPic = new ArrayList<>();
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mPic.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373321088,605628612&fm=27&gp=0.jpg");
        mGood.add(
                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
                        "123", "456", "\n\n789",mPic));
        mGood.add(
                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
                        "123", "456", "789",mPic));
        mGood.add(
                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
                        "123", "456", "789",mPic));
        mGood.add(
                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
                        "123", "456", "789",mPic));
        mGood.add(
                new Goods("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg",
                        "123", "456", "789",mPic));


    }
}
