package com.manshop.android.utils.keyboard.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.manshop.android.utils.keyboard.data.PageEntity;


public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
