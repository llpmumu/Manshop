package com.manshop.android.ui.view;

/**
 * Created by Lin on 2017/10/31.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manshop.android.R;
import com.manshop.android.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<Integer> images = new ArrayList<>();
    private String[] titles = {
            "title1",
            "title2",
            "title3",
            "title4",
            "title5",
    };
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
        return view;
    }
}
