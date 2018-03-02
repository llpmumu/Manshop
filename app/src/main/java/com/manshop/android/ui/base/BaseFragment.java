package com.manshop.android.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

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

}
