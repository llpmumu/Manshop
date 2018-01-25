package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import com.manshop.android.adapter.GridViewAddImgesAdpter;
import com.manshop.android.ui.base.BaseActivity;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPublishActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    private RadioGroup pubRg;
    private RadioButton saleRb;
    private RadioButton rentRb;

    private TextView tip1;
    private EditText et1;
    private TextView tip2;
    private EditText et2;
    private TextView tip3;

    private GridView gw;
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;

    private static final String TAG = "photo";
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_publish);
        init();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    //标题栏按钮功能实现
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    public void init() {
        showToolbar();

        pubRg = (RadioGroup) findViewById(R.id.publish_RadioGroup);
        saleRb = (RadioButton) findViewById(R.id.rb_sale);
        rentRb = (RadioButton) findViewById(R.id.rb_rent);

        tip1 = (TextView) findViewById(R.id.tv_tip1);

        tip2 = (TextView) findViewById(R.id.tv_tip2);
        tip3 = (TextView) findViewById(R.id.tv_tip3);
        et2 = (EditText) findViewById(R.id.et_rent_price);

        pubRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == saleRb.getId()) {
                    tip1.setText("价格");
                    tip2.setVisibility(View.GONE);
                    et2.setVisibility(View.GONE);
                    tip3.setVisibility(View.GONE);
                } else if (checkedId == rentRb.getId()) {
                    tip1.setText("租金");
                    tip2.setVisibility(View.VISIBLE);
                    et2.setVisibility(View.VISIBLE);
                    tip3.setVisibility(View.VISIBLE);
                }
            }
        });

        takePhoto = getTakePhoto();
        gw = (GridView) findViewById(R.id.picture_gridview);
        datas = new ArrayList<>();
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        gw.setAdapter(gridViewAddImgesAdpter);
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showDialog();
            }
        });
    }

    public void showDialog() {
        List<String> stringList = new ArrayList<>();
        stringList.add("拍照");
        stringList.add("从相册选择");

        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(NewPublishActivity.this, stringList);
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

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
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

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
        String iconPath = result.getImage().getOriginalPath();
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
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/goods/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged(datas);
    }
}
