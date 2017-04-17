package com.yangcao.simpleresume.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Rainie on 4/10/17.
 */

public class ImageUtils {
    public static void loadImage(@NonNull Context context,
                                 @NonNull Uri uri,
                                 @NonNull ImageView imageView) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            //MediaStore.Images.Media.getBitmap(ContentResolver cr,Uri url)方法可以从一个已知的图片Uri中获得图片的bitmap对象，
            // 其中ContentResolver通常可以通过在Activity中调用的getContentResolver()方法中获取
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
