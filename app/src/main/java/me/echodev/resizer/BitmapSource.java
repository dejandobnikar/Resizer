package me.echodev.resizer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * Created by dejandobnikar on 29/12/2017.
 */

public interface BitmapSource {

    Uri getUri();

    Bitmap loadBitmap(BitmapFactory.Options options);
}
