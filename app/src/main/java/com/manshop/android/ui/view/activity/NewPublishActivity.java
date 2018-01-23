package com.manshop.android.ui.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manshop.android.R;
import com.manshop.android.adapter.GridViewAddImgesAdpter;
import com.manshop.android.ui.base.BaseActivity;

import net.bither.util.NativeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private GridView gw;
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    //     private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview/";
    /* 头像名称 */
    private final String PHOTO_FILE_NAME = "temp_photo.jpg";


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
//        整个线性布局
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


        gw = (GridView) findViewById(R.id.picture_gridview);
        datas = new ArrayList<>();
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        gw.setAdapter(gridViewAddImgesAdpter);
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                pop.showAtLocation(view, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
            }
        });
    }

    public void popWindows() {
//        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
//        pop = new PopupWindow(NewPublishActivity.this);
//        popup = (LinearLayout) view.findViewById(R.id.popup);
//        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        pop.setBackgroundDrawable(new BitmapDrawable());
//        pop.setFocusable(true);
//        pop.setOutsideTouchable(true);
//        pop.setContentView(view);
//
//        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
//        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
//        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
//        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
//        View localView = LayoutInflater.from(this).inflate(R.layout.item_popupwindows, null);
//        TextView tv_camera = (TextView) localView.findViewById(R.id.item_popupwindows_camera);
//        TextView tv_gallery = (TextView) localView.findViewById(R.id.item_popupwindows_Photo);
//        TextView tv_cancel = (TextView) localView.findViewById(R.id.item_popupwindows_cancel);
//        dialog = new Dialog(this);
//        dialog.setContentView(localView);
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//        // 设置全屏
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//        lp.width = display.getWidth(); // 设置宽度
//        dialog.getWindow().setAttributes(lp);
//        dialog.show();

        //整个线性布局
//        parent.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                pop.dismiss();
//                popup.clearAnimation();
//            }
//        });
//        //拍照
//        bt1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                take_photo();
//                pop.dismiss();
//                popup.clearAnimation();
//            }
//        });
//        //相册
//        bt2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                select_photo();
//                pop.dismiss();
//                popup.clearAnimation();
//            }
//        });
//        //取消
//        bt3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                pop.dismiss();
//                popup.clearAnimation();
//            }
//        });
    }

    /**
     * 拍照获取图片
     */
    public void take_photo() {
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {

            File dir = new File(IMAGE_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }
            tempFile = new File(dir, System.currentTimeMillis() + "_" + PHOTO_FILE_NAME);
            //从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
//            Intent intent = new Intent();// 启动系统相机
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
////            intent.addCategory(intent.CATEGORY_DEFAULT);
//            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
//            startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
            //启动相机程序
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
        } else {
            Toast.makeText(this, "未找到存储卡，无法拍照！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
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
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
//        Intent intent = new Intent();
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        if (Build.VERSION.SDK_INT <19) {
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        }else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        }
//        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == PHOTO_REQUEST_GALLERY) {
//                // 从相册返回的数据
//                if (data != null) {
//                    // 得到图片的全路径
//                    Uri uri = data.getData();
//                    String[] proj = {MediaStore.Images.Media.DATA};
//                    //好像是android多媒体数据库的封装接口，具体的看Android文档
//                    Cursor cursor = managedQuery(uri, proj, null, null, null);
//                    //按我个人理解 这个是获得用户选择的图片的索引值
//                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
//                    cursor.moveToFirst();
//                    //最后根据索引值获取图片路径
//                    String path = cursor.getString(column_index);
//
//                    uploadImage(path);
//                }
//
//            } else if (requestCode == PHOTO_REQUEST_CAREMA) {
//                if (resultCode != RESULT_CANCELED) {
//                    // 从相机返回的数据
//                    if (hasSdcard()) {
//                        if (tempFile != null) {
//                            uploadImage(tempFile.getPath());
//                        } else {
//                            Toast.makeText(this, "相机异常请稍后再试！", Toast.LENGTH_SHORT).show();
//                        }
//
//                        Log.i("images", "拿到照片path=" + tempFile.getPath());
//                    } else {
//                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//        }
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CANCELED:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // 得到图片的全路径
                        Uri uri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(uri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        String path = cursor.getString(column_index);

                        uploadImage(path);
                    }
                }
                break;
            case PHOTO_REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImgeOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }

        }
    };

    /**
     *4.4以下系统处理图片的方法
     * */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    /**
     * 4.4及以上系统处理图片的方法
     * */
    private void handleImgeOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的uri，则使用普通方式处理
                imagePath = getImagePath(uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
            //根据图片路径显示图片
            displayImage(imagePath);
        }
    }

    /**
     * 根据图片路径显示图片的方法
     * */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        }
    }

    /**
     * 通过uri和selection来获取真实的图片路径
     * */
    private String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 上传图片
     *
     * @param path
     */
    private void uploadImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                if (new File(path).exists()) {
                    Log.d("images", "源文件存在" + path);
                } else {
                    Log.d("images", "源文件不存在" + path);
                }

                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                final File file = new File(dir + "/temp_photo" + System.currentTimeMillis() + ".jpg");
//                NativeUtil.compressBitmap(path, file.getAbsolutePath(), 50);
                if (file.exists()) {
                    Log.d("images", "压缩后的文件存在" + file.getAbsolutePath());
                } else {
                    Log.d("images", "压缩后的不存在" + file.getAbsolutePath());
                }
                Message message = new Message();
                message.what = 0xAAAAAAAA;
                message.obj = file.getAbsolutePath();
                handler.sendMessage(message);

            }
        }.start();

    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged();
    }
}
