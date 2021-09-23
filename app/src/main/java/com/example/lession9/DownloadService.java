package com.example.lession9;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import static com.example.lession9.MainActivity.DOWNLOAD_COMPLETE_ACTION;


/**
 * Created by hungnm24 on 5/3/20
 * Copyright (c) {2020} VinID. All rights reserved.
 */

public class DownloadService extends Service {

    static final String URL_ARG = "url";
    static final String FILE_PATH_KEY = "file_path";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String url = intent.getStringExtra(URL_ARG);
            startDownload(url);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload(String url) {
        DownloadBitmapAsyncTask downloadAndSaveTask = new DownloadBitmapAsyncTask(
                new Callback() {
                    @Override
                    public void onDownloadFinish(String filePath) {
                        sendDownloadCompleteBroadCast(filePath);
                    }
                }
        );
        downloadAndSaveTask.execute(url);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendDownloadCompleteBroadCast(String filePath) {
        Intent intent = new Intent(DOWNLOAD_COMPLETE_ACTION);
        intent.putExtra(FILE_PATH_KEY, filePath);
        sendBroadcast(intent);
    }
}
