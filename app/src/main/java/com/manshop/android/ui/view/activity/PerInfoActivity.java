package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.hhl.library.FlowTagLayout;
import com.hhl.library.OnTagSelectListener;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.longsh.optionframelibrary.OptionBottomDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.adapter.TagAdapter;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.utils.BitmapUtil;
import com.manshop.android.utils.Constant;
import com.manshop.android.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Response;

import static cn.jpush.im.android.api.model.UserInfo.Field.nickname;


public class PerInfoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @Bind(R.id.icon_image)
    RoundedImageView imgHead;
    @Bind(R.id.et_nickname)
    EditText etName;
    @Bind(R.id.et_signature)
    EditText etSignature;
    //打开相机、相册
    private File headFile;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig config;
    private static final String TAG = "photo";
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private BitmapUtil bitmapUtil = new BitmapUtil();
    private UserInfo userInfo;

    private LabelsView labelsView;
    private List<String> isSelectedList = new ArrayList<>();
    private OkHttp okHttp = OkHttp.getOkhttpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_info);
        ButterKnife.bind(PerInfoActivity.this);
        showToolbar();
        initView();
    }

    @Override
    public void showToolbar() {
        super.showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                updateInfo();
                finish();
                break;
            default:
        }
        return true;
    }

    public void initView() {
        userInfo = JMessageClient.getMyInfo();
        //个人信息
        etName.setText(userInfo.getNickname());
        etSignature.setText(userInfo.getSignature());
        if (userInfo.getAvatarFile() != null) {
            Glide.with(PerInfoActivity.this).load(userInfo.getAvatarFile().getAbsolutePath()).into(imgHead);
        } else {
            imgHead.setImageResource(R.drawable.jmui_head_icon);
        }
        //上传拍照
        takePhoto = getTakePhoto();
//        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //标签
        labelsView = (LabelsView) findViewById(R.id.flow_tag);
        initTagData();
        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                if (isSelectedList.size() == 5)
                    ToastUtil.shortToast(PerInfoActivity.this, "最多选择5个标签");
                isSelectedList = labelsView.getSelectLabelDatas();
            }
        });
    }

    //标签数据
    public void initTagData() {
        ArrayList<String> dataSource = new ArrayList<>();
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
        labelsView.setLabels(dataSource);
    }

    public void updateInfo() {
        final Map<String, Object> param = new HashMap<>();
        Log.d("info", userInfo.getUserName() + "");
        param.put("phone", ""+userInfo.getUserName());
        param.put("username",etName.getText());
        okHttp.doPost(Constant.baseURL + "user/updateInfo", new CallBack(PerInfoActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                userInfo.setNickname(etName.getText().toString());
                JMessageClient.updateMyInfo(nickname, userInfo, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0)
                            Log.d("updateInfo","修改成功");
//                            ToastUtil.shortToast(PerInfoActivity.this,"修改昵称成功");
                    }
                });
                JMessageClient.updateUserAvatar(headFile, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        Log.d("info", i + s);
                    }
                });
            }
        }, param);
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
                    headFile = getFile();
                    Uri imageUri = Uri.fromFile(headFile);
                    takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);
                    optionBottomDialog.dismiss();
                } else if (position == 1) {
                    headFile = getFile();
                    Uri imageUri = Uri.fromFile(headFile);
                    takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
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

    public File getFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "/head/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    //压缩
    public void configCompress(TakePhoto takePhoto) {
        LubanOptions option = new LubanOptions.Builder().setMaxHeight(50).setMaxWidth(50).setMaxSize(102400).create();
        config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(true);
        takePhoto.onEnableCompress(config, true);
    }

    //界面
    public void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(false);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    @Override
    public void takeSuccess(TResult result) {
//        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
        Log.i(TAG, "takeSuccess：" + result.getImage().getOriginalPath());
        String iconPath = result.getImage().getOriginalPath();
        try {
            Bitmap bitmap = bitmapUtil.getBitmapFormUri(PerInfoActivity.this, Uri.parse("file://" + iconPath));
            imgHead.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        takePhoto.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        takePhoto.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
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