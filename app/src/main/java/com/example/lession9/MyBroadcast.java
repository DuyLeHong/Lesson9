package com.example.lession9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(MyBroadcast.class.getSimpleName(), "Air Plane mode change");
    }


}
