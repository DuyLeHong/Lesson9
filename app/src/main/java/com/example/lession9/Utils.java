package com.example.lession9;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hungnm24 on 4/27/20
 * Copyright (c) {2020} VinID. All rights reserved.
 */

public final class Utils {

    public static Bitmap downloadImage(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String downloadImageToFile(String src) {
        Bitmap bitmap = downloadImage(src);
        return saveBitmapToFile(bitmap);
    }

    public static String saveBitmapToFile(Bitmap bitmap) {
        // Assume block needs to be inside a Try/Catch block.
        String filePath = null;
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File file = new File(path, generateFileName());
            OutputStream fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream

            // notify gallery when new image is inserted
            MediaStore.Images.Media.insertImage(
                    App.getAppContext().getContentResolver(),
                    file.getAbsolutePath(),
                    file.getName(),
                    file.getName()
            );
            filePath = file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }


    private static String generateFileName() {
        return System.currentTimeMillis() + ".jpg";
    }

}
