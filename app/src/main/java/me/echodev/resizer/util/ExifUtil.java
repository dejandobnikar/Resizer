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
            case 2:
                matrix.setScale(-1, 1);
                break;
            case 3:
                matrix.setRotate(180);
                break;
            case 4:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case 5:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case 6:
                matrix.setRotate(90);
                break;
            case 7:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case 8:
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
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return  90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return  180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return  270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            Log.w("ExifUtil", "cannot read exif", e);
            return 0;
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
