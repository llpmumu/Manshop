package com.manshop.android.ui.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manshop.android.R;
import com.manshop.android.ui.base.BaseActivity;

import java.io.File;
import java.io.IOException;

public class NewPublishActivity extends BaseActivity {
    private RadioGroup pubRg;
    private RadioButton saleRb;
    private RadioButton rentRb;

    private TextView tip1;
    private EditText et1;
    private TextView tip2;
    private EditText et2;
    private TextView tip3;

    private PopupWindow pop = null;
    private LinearLayout popup;
    private ImageView imageView;


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
        et1 = (EditText) findViewById(R.id.et_sale_price);
        tip2 = (TextView) findViewById(R.id.tv_tip2);
        et2 = (EditText) findViewById(R.id.et_rent_price);
        tip3 = (TextView) findViewById(R.id.tv_tip3);

        imageView = (ImageView) findViewById(R.id.im_add_pic);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows();
            }
        });

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
    }

    public void popWindows(){
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        pop = new PopupWindow(NewPublishActivity.this);
        popup = (LinearLayout) view.findViewById(R.id.popup);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);

        //整个线性布局
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop.dismiss();
                popup.clearAnimation();
            }
        });
        //拍照
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                take_photo();
                pop.dismiss();
                popup.clearAnimation();
            }
        });
        //相册
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                select_photo();
                pop.dismiss();
                popup.clearAnimation();
            }
        });
        //取消
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                popup.clearAnimation();
            }
        });
    }

    public static final int TAKE_PHOTO = 1;
    public static final int SELECT_PHOTO = 2;
    private Uri imageUri;

    /**
     * 拍照获取图片
     */

    public void take_photo() {
        //创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this, "com.llk.study.activity.PhotoActivity", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 从相册中获取图片
     */
    public void select_photo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    /**
     * 打开相册的方法
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

}
