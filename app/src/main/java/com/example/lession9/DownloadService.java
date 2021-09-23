package com.example.lession9;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.lession9.MainActivity.DOWNLOAD_COMPLETE_ACTION;


/**
 * Created by hungnm24 on 5/3/20
 * Copyright (c) {2020} VinID. All rights reserved.
 */

public class DownloadService extends Service {

    static final String URL_ARG = "url";
    static final String FILE_PATH_KEY = "file_path";

    public static final String CHANNEL_ID = "ForegroundServiceChannel";


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Lesson 10")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String url = intent.getStringExtra(URL_ARG);
            startDownload(url);
        }

        //return super.onStartCommand(intent, flags, startId);

        return START_NOT_STICKY;
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
