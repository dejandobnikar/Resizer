package me.echodev.resizer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.media.ExifInterface;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import me.echodev.resizer.BitmapSource;

class ExifUtil {

    static Bitmap rotateBitmap(Context context, BitmapSource src, Bitmap bitmap) {
        try {

            int orientation = getExifOrientation(context, src);
            
            if (orientation == 1) {
                return bitmap;
            }
            
            Matrix matrix = new Matrix();
            switch (orientation) {
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
            }
            
            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                Log.w("ExifUtil", "cannot perform rotation", e);
                return bitmap;
            }
        } catch (IOException e) {
            Log.w("ExifUtil", "error", e);
        } 
        
        return bitmap;
    }
    
    private static int getExifOrientation(Context context, BitmapSource src) throws IOException {

        final Uri uri = src.getUri();

        InputStream in = null;
        try {
            in = context.getContentResolver().openInputStream(uri);
            if (in == null) {
                return 0;
            }

            ExifInterface exifInterface = new ExifInterface(in);
            return exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        } catch (IOException e) {
            Log.w("ExifUtil", "cannot read exif", e);
            return 1;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
