package com.manshop.android.util;

import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lin on 2018/1/23.
 */

public class BitmapUtils {
    public static BitmapUtils getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final class InstanceHolder {
        private static final BitmapUtils INSTANCE = new BitmapUtils();
    }

    private BitmapUtils() {
    }

    /**
     * new File
     */
    public File getPicFile() {
        File file = new File(Environment.getExternalStorageDirectory() + "/pic/", System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 尺寸大小的缩放
     */
    public void getimage(String srcPath, String desPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options(); //将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存， // 返回值也不再是一个Bitmap对象，而是null。虽然Bitmap是null了， // 但是BitmapFactory.Options的outWidth、outHeight和outMimeType属性都会被赋值 newOpts.inJustDecodeBounds = true; Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//文件图片用该方法,此时Bitmap为null newOpts.inJustDecodeBounds = false; int w = newOpts.outWidth;//源图片的宽，高 int h = newOpts.outHeight; //最好动态指定设置宽高 float hh = 480;//这里设置高度为480f float ww = 320;//这里设置宽度为320f //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可 int be = 1;//be=1表示不缩放 if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放 be = (int) (newOpts.outWidth / ww); } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放 be = (int) (newOpts.outHeight / hh); } if (be <= 0) be = 1; newOpts.inSampleSize = be;//设置缩放比例 //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了 bitmap = BitmapFactory.decodeFile(srcPath, newOpts); compressImage(bitmap, desPath);//压缩好比例大小后再进行质量压缩 } /** * 存储大小的缩放 */ public void compressImage(Bitmap image, String file) { ByteArrayOutputStream baos = new ByteArrayOutputStream(); FileOutputStream fileOutputStream = null; try { fileOutputStream = new FileOutputStream(file); image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中 int options = 85; while ((baos.toByteArray().length / 1024 > 200) && (options > 1)) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩 baos.reset();// 重置baos即清空baos image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中 options -= 10;// 每次都减少10 } baos.writeTo(fileOutputStream); // 用完了记得回收 image.recycle(); } catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } finally { try { fileOutputStream.close(); baos.close(); } catch (IOException e) { e.printStackTrace(); } } } public String getPathByUri(final Context context, final Uri uri) { try { return getRealFilePath(context, uri); } catch (Exception e) { return uri.getPath(); } } /** * get url by uri */ private String getRealFilePath(final Context context, final Uri uri) { if (null == uri) return null; final String scheme = uri.getScheme(); String data = null; if (scheme == null) data = uri.getPath(); else if (ContentResolver.SCHEME_FILE.equals(scheme)) { data = uri.getPath(); } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) { Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null); if (null != cursor) { if (cursor.moveToFirst()) { int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); if (index > -1) { data = cursor.getString(index); } } cursor.close(); } } return data; }

    }
}
