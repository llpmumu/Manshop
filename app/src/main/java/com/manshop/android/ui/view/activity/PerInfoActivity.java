package com.manshop.android.ui.view.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.manshop.android.utils.BitmapUtil;
import com.sgb.library.FlowAdapter;
import com.sgb.library.FlowLayout;

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

    private FlowLayout mFlowLayout;
    private MyAdapter mAdapter;
    private List<String> mTags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_info);
        initData();
        initView();
    }

    public void initView() {
        takePhoto = getTakePhoto();
        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);
        mFlowLayout.setAdapter(mAdapter = new MyAdapter(mTags));
        mFlowLayout.setChoiceMode(FlowLayout.CHOICE_MODE_MULTIPLE);
        mFlowLayout.setMaxChecked(5);
    }

    public void initData(){
        mTags.add("海贼");
        mTags.add("ganggangde ");
        mTags.add("huoying");
        mTags.add("maomi");
        mTags.add("shouban");
        mTags.add("fuzhuang");
        mTags.add("xihuan");
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

    private class MyAdapter extends FlowAdapter<String> {
        public MyAdapter(List<String> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int position, FlowLayout parent) {
            TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);

            String obj = getItem(position);
            view.setText(obj);

            return view;
        }
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