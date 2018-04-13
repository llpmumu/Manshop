package com.manshop.android.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manshop.android.utils.keyboard.utils.imageloader.ImageBase;
import com.manshop.android.utils.keyboard.utils.imageloader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageLoadUtils extends ImageLoader {

    public ImageLoadUtils(Context context) {
        super(context);
    }

    @Override
    protected void displayImageFromFile(String imageUri, ImageView imageView) throws IOException {
        String filePath = ImageBase.Scheme.FILE.crop(imageUri);
        Glide.with(imageView.getContext())
                .load(filePath)
                .asBitmap()
                .into(imageView);
    }

    @Override
    protected void displayImageFromAssets(String imageUri, ImageView imageView) throws IOException {
        String uri = Scheme.cropScheme(imageUri);
        ImageBase.Scheme.ofUri(imageUri).crop(imageUri);
        Glide.with(imageView.getContext())
                .load(Uri.parse("file:///android_asset/" + uri))
                .into(imageView);
    }

    public static List<String> displayGoodsImage(String imageName) {
        List<String> mString = new ArrayList<>();
        String[] txtpicture = imageName.split(";");
        String imageURL = "";
        for (int i = 0; i < txtpicture.length; i++) {
            imageURL = Constant.baseImageURL + txtpicture[i];
            mString.add(imageURL);
        }
        return mString;
    }
}
