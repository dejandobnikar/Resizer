package me.echodev.resizer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;

import me.echodev.resizer.BitmapSource;

/**
 * Created by K.K. Ho on 3/9/2017.
 */

public class ImageUtils {
    public static File getScaledImage(Context context, int targetLength, int quality, Bitmap.CompressFormat compressFormat,
            String outputDirPath, String outputFilename, BitmapSource bitmapSource) throws IOException {
        File directory = new File(outputDirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Prepare the new file name and path
        String outputFilePath = FileUtils.getOutputFilePath(compressFormat, outputDirPath, outputFilename);

        // Write the resized image to the new file
        Bitmap scaledBitmap = getScaledBitmap(targetLength, bitmapSource);
        Bitmap rotatedBitmap = ExifUtil.rotateBitmap(context, bitmapSource, scaledBitmap);
        FileUtils.writeBitmapToFile(rotatedBitmap, compressFormat, quality, outputFilePath);

        return new File(outputFilePath);
    }

    public static Bitmap getScaledBitmap(int targetLength, BitmapSource bitmapSource) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;

        Bitmap bitmap = bitmapSource.loadBitmap(options);

        // Get the dimensions of the original bitmap
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        float aspectRatio = (float) originalWidth / originalHeight;

        // Calculate the target dimensions
        int targetWidth, targetHeight;

        if (originalWidth > originalHeight) {
            targetWidth = targetLength;
            targetHeight = Math.round(targetWidth / aspectRatio);
        } else {
            aspectRatio = 1 / aspectRatio;
            targetHeight = targetLength;
            targetWidth = Math.round(targetHeight / aspectRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
    }
}
