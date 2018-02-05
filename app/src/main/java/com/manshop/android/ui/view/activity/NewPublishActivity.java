package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.longsh.optionframelibrary.OptionBottomDialog;
import com.manshop.android.MyApplication;
import com.manshop.android.R;
import com.manshop.android.adapter.GridViewAddImgesAdpter;
import com.manshop.android.okHttp.CallBack;
import com.manshop.android.okHttp.OkHttp;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.util.Constant;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

import static android.R.attr.password;

public class NewPublishActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    //标题 具体内容
    private EditText etTitle;
    private EditText etDetail;

    //选择出售、或者出租
    private RadioGroup pubRg;
    private RadioButton saleRb;
    private RadioButton rentRb;

    //价格 租金
    private TextView tip1;
    private EditText etPrice;
    private TextView tip2;
    private EditText etRent;
    private TextView tip3;

    //图片上传网格布局
    private GridView gw;
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;

    //打开相机、相册
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
        etTitle = (EditText) findViewById(R.id.et_sale_name);
        etDetail= (EditText) findViewById(R.id.etContent);

        pubRg = (RadioGroup) findViewById(R.id.publish_RadioGroup);
        saleRb = (RadioButton) findViewById(R.id.rb_sale);
        rentRb = (RadioButton) findViewById(R.id.rb_rent);

        tip1 = (TextView) findViewById(R.id.tv_tip1);
        etPrice = (EditText) findViewById(R.id.et_sale_price);
        tip2 = (TextView) findViewById(R.id.tv_tip2);
        tip3 = (TextView) findViewById(R.id.tv_tip3);
        etRent = (EditText) findViewById(R.id.et_rent_price);

        pubRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == saleRb.getId()) {
                    tip1.setText("价格");
                    tip2.setVisibility(View.GONE);
                    etPrice.setVisibility(View.GONE);
                    tip3.setVisibility(View.GONE);
                } else if (checkedId == rentRb.getId()) {
                    tip1.setText("租金");
                    tip2.setVisibility(View.VISIBLE);
                    etRent.setVisibility(View.VISIBLE);
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
    public Uri getImageCropUri() {
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

    private OkHttp okhttp = OkHttp.getOkhttpHelper();
    public void publish(View view){
        String title = etTitle.getText().toString();
        String detail = etDetail.getText().toString();
        String price = etPrice.getText().toString();
        String rent = etRent.getText().toString();
        final Map<String, Object> param = new HashMap<>();
        param.put("uid", MyApplication.getInstance().getUserId());
        param.put("title",title);
        param.put("detail",detail);
        param.put("price",price);
        param.put("rental",rent);
        param.put("picture","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1121475478,2545730346&fm=27&gp=0.jpg;https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3813653354,3201329653&fm=27&gp=0.jpg");
        okhttp.doPost(Constant.baseURL + "goods/newGood", new CallBack(NewPublishActivity.this) {

            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "添加商品失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_LONG).show();
                startActivity(new Intent(NewPublishActivity.this, PublishActivity.class));
            }
        }, param);
    }
}
