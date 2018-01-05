package me.echodev.resizer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;

/**
 * Created by dejandobnikar on 29/12/2017.
 */

class FileBitmapSource implements BitmapSource {

    private final File image;

    FileBitmapSource(File image) {
        this.image = image;
    }

    @Override public Uri getUri() {
        return Uri.fromFile(image);
    }

    @Override public Bitmap loadBitmap(BitmapFactory.Options options) {
        return BitmapFactory.decodeFile(image.getAbsolutePath(), options);
    }
}
