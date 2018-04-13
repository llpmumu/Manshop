package com.manshop.android.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

import com.manshop.android.utils.FileHelper;
import com.manshop.android.utils.SharePreferenceUtil;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Lin on 2017/10/31.
 */

public class BaseFragment extends Fragment {
    public static BaseFragment newInstance(String info) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString("ic_info", info);
        fragment.setArguments(args);
        return fragment;
    }

    private UserInfo myInfo;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mWidth;
    protected int mHeight;
    protected float mRatio;
    protected int mAvatarSize;
    private Context mContext;
    public Activity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        //订阅接收消息,子类只要重写onEvent就能收到消息
        JMessageClient.registerEventReceiver(this);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);

    }

    public void onEventMainThread(LoginStateChangeEvent event) {
        final LoginStateChangeEvent.Reason reason = event.getReason();
        UserInfo myInfo = event.getMyInfo();
        if (myInfo != null) {
            String path;
            File avatar = myInfo.getAvatarFile();
            if (avatar != null && avatar.exists()) {
                path = avatar.getAbsolutePath();
            } else {
                path = FileHelper.getUserAvatarPath(myInfo.getUserName());
            }
            SharePreferenceUtil.setCachedUsername(myInfo.getUserName());
            SharePreferenceUtil.setCachedAvatarPath(path);
            JMessageClient.logout();
        }
    }

    @Override
    public void onDestroy() {
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = context;
    }

}
