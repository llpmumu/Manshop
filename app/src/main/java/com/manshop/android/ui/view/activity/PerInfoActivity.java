package com.manshop.android.ui.view.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hhl.library.FlowTagLayout;
import com.hhl.library.OnTagSelectListener;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.longsh.optionframelibrary.OptionBottomDialog;
import com.manshop.android.R;
import com.manshop.android.adapter.TagAdapter;
import com.manshop.android.utils.BitmapUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class PerInfoActivity extends AppCompatActivity implements TakePhoto.TakeResultListener, InvokeListener {
    //打开相机、相册
    private static final String TAG = "photo";
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private BitmapUtil bitmapUtil = new BitmapUtil();

    private FlowTagLayout mFlowLayout;
    private TagAdapter<String> mTagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_info);
        initData();
        initView();
    }

    public void initView() {
        takePhoto = getTakePhoto();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mFlowLayout = (FlowTagLayout) findViewById(R.id.flow_tag);
        //移动研发标签
        mTagAdapter = new TagAdapter<>(this);
        mFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        mFlowLayout.setAdapter(mTagAdapter);
        mFlowLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();

                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append(":");
                    }
                    Snackbar.make(parent, "移动研发:" + sb.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    public void initData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("android");
        dataSource.add("安卓");
        dataSource.add("SDK源码");
        dataSource.add("IOS");
        dataSource.add("iPhone");
        dataSource.add("游戏");
        dataSource.add("fragment");
        dataSource.add("viewcontroller");
        dataSource.add("cocoachina");
        dataSource.add("移动研发工程师");
        dataSource.add("移动互联网");
        dataSource.add("高薪+期权");
        mTagAdapter.onlyAddAll(dataSource);
    }


    //下面为拍照功能
    public void showDialog() {
        List<String> stringList = new ArrayList<>();
        stringList.add("拍照");
        stringList.add("从相册选择");

        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(PerInfoActivity.this, stringList);
        optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Uri uri = getImageCropUri();
                    takePhoto.onPickFromCapture(uri);
                    optionBottomDialog.dismiss();
                } else if (position == 1) {
                    takePhoto.onPickFromGallery();
                    optionBottomDialog.dismiss();
                }
            }
        });
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }


    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
        String iconPath = result.getImage().getOriginalPath();
        try {
            Bitmap bitmap = bitmapUtil.getBitmapFormUri(PerInfoActivity.this, Uri.parse("file://" + iconPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        photoPath(iconPath);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    //获得照片的输出保存Uri
    public Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/head/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }
}