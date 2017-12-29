package me.echodev.resizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by dejandobnikar on 29/12/2017.
 */

class UriBitmapSource implements BitmapSource {

    private final Context context;
    private final Uri uri;

    UriBitmapSource(Context context, Uri uri) {
        this.context = context;
        this.uri = uri;
    }

    @Override public Bitmap loadBitmap(BitmapFactory.Options options) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream, null, options);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
