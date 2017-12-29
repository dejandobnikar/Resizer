package me.echodev.resizer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by dejandobnikar on 29/12/2017.
 */

public interface BitmapSource {

    Bitmap loadBitmap(BitmapFactory.Options options);
}
