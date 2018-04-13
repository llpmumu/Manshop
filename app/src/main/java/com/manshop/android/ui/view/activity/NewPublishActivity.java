package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
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
import com.manshop.android.utils.BitmapUtil;
import com.manshop.android.utils.Constant;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class NewPublishActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    //标题 具体内容
    private EditText etTitle;
    private EditText etDetail;

    //选择出售、或者出租
//    private RadioGroup pubRg;
//    private RadioButton saleRb;
//    private RadioButton rentRb;

    //价格 租金
    private TextView tip1;
    private EditText etPrice;
//    private TextView tip2;
//    private EditText etRent;
//    private TextView tip3;

    private Boolean isEdit;
    private Intent intent;

    //图片上传网格布局
    private GridView gw;
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    public List<Bitmap> listBitmap = new ArrayList<>();

    //打开相机、相册
    private static final String TAG = "photo";
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    CompressConfig compressConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_publish);
        init();
        edit();
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
            default:
        }
        return true;
    }

    public void init() {
        showToolbar();
        etTitle = (EditText) findViewById(R.id.et_sale_name);
        etDetail = (EditText) findViewById(R.id.etContent);

//        pubRg = (RadioGroup) findViewById(R.id.publish_RadioGroup);
//        saleRb = (RadioButton) findViewById(R.id.rb_sale);
//        rentRb = (RadioButton) findViewById(R.id.rb_rent);

        tip1 = (TextView) findViewById(R.id.tv_tip1);
        etPrice = (EditText) findViewById(R.id.et_sale_price);
//        tip2 = (TextView) findViewById(R.id.tv_tip2);
//        tip3 = (TextView) findViewById(R.id.tv_tip3);
//        etRent = (EditText) findViewById(R.id.et_rent_price);

//        pubRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                if (checkedId == saleRb.getId()) {
//                    tip1.setText("价格");
//                    tip2.setVisibility(View.GONE);
//                    etPrice.setVisibility(View.GONE);
//                    tip3.setVisibility(View.GONE);
//                } else if (checkedId == rentRb.getId()) {
//                    tip1.setText("租金");
//                    tip2.setVisibility(View.VISIBLE);
//                    etRent.setVisibility(View.VISIBLE);
//                    tip3.setVisibility(View.VISIBLE);
//                }
//            }
//        });

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

    //编辑商品跳转数据传入
    public void edit() {
        intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdite", false);
        etTitle.setText(intent.getStringExtra("title"));
        etDetail.setText(intent.getStringExtra("detail"));
        etPrice.setText(intent.getStringExtra("price"));
//        etRent.setText(intent.getStringExtra("rental"));
    }

    private OkHttp okhttp = OkHttp.getOkhttpHelper();

    public void publish(View view) {
        Map<String, Object> param = new HashMap<>();
        if (isEdit) {
            param.put("id", intent.getIntExtra("id", 0));
            requestGoodData(Constant.baseURL + "goods/updateGood", param);
        } else
            requestGoodData(Constant.baseURL + "goods/newGood", param);
    }

    //提交数据
    public void requestGoodData(String uri, Map<String, Object> params) {
        String title = etTitle.getText().toString();
        String detail = etDetail.getText().toString();
        String price = etPrice.getText().toString();
//        String rent = etRent.getText().toString();
        params.put("uid", MyApplication.getInstance().getUserId());
        params.put("title", title);
        params.put("detail", detail);
        params.put("price", price);
//        params.put("rental", rent);
        String string = "";
        ByteArrayOutputStream bStream;
        for (int i = 0; i < listBitmap.size(); i++) {
            Bitmap bitmap = listBitmap.get(i);
            bStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bStream);
            byte[] bytes = bStream.toByteArray();
            string = Base64.encodeToString(bytes, Base64.DEFAULT) + ";" + string;
        }
        params.put("picture", string);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        params.put("goodtime", date);
        okhttp.doPost(uri, new CallBack(NewPublishActivity.this) {
            @Override
            public void onError(Response response, Exception e) throws IOException {
                Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callBackSuccess(Response response, Object o) throws IOException {
                Log.d("good", "new success");
                Intent intent = new Intent(NewPublishActivity.this, ListPublishActivity.class);
                startActivity(intent);
                finish();

            }
        }, params);
    }

    //下面为拍照功能
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

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    BitmapUtil bitmapUtil = new BitmapUtil();

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
        String iconPath = result.getImage().getOriginalPath();
        try {
            Bitmap bitmap = bitmapUtil.getBitmapFormUri(NewPublishActivity.this, Uri.parse("file://" + iconPath));
//            Log.i("syso", "bbbb" + " " + bitmap.toString());
            listBitmap.add(bitmap);
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
        File file = new File(Environment.getExternalStorageDirectory(), "/goods/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    private Bitmap convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged(datas);
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

}
