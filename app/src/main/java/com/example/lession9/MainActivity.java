package com.example.lession9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.example.lession9.DownloadService.URL_ARG;

public class MainActivity extends AppCompatActivity {

    static final String DOWNLOAD_COMPLETE_ACTION = "com.vinid.myfirstproject.download_complete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupView();

        registerBroadCastReceiver();
    }

    private void registerBroadCastReceiver() {
        IntentFilter filter = new IntentFilter(DOWNLOAD_COMPLETE_ACTION);
        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new DownloadCompleteReceiver();

    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String filePath = intent.getStringExtra(DownloadService.FILE_PATH_KEY);
            ImageView imageView = findViewById(R.id.ivImage);
            Glide.with(MainActivity.this)
                    .load(filePath)
                    .into(imageView);
        }
    }

    private void setupView(){
        findViewById(R.id.btnStartDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForStartDownloadService();
            }
        });
    }

    public static final int REQUEST_WRITE_STORAGE = 1;

    private void checkForStartDownloadService() {
        boolean hasPermission = hasStoragePermission();
        if (hasPermission) {
            startDownloadService();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    private boolean hasStoragePermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private static final String IMAGE_URL = "https://codefresher.vn/wp-content/uploads/2021/06/Banner-05-KH-IT-Foundation.png";

    private void startDownloadService() {
        Toast.makeText(this, "Download started!!!", Toast.LENGTH_LONG).show();


        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(URL_ARG, IMAGE_URL);
        startService(intent);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }


    }
}