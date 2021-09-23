package com.example.lession9;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by hungnm24 on 7/31/20
 * Copyright (c) {2020} VinID. All rights reserved.
 */

class DownloadBitmapAsyncTask extends AsyncTask<String, Void, String> {
    Callback callback;

    DownloadBitmapAsyncTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        Bitmap bitmap =  Utils.downloadImage(url);
        String filePath = Utils.saveBitmapToFile(bitmap);
        return filePath;
    }

    @Override
    protected void onPostExecute(String filePath) {
        super.onPostExecute(filePath);
        callback.onDownloadFinish(filePath);
    }
}