package com.manshop.android.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.manshop.android.R;
import com.manshop.android.ui.view.activity.DialogueActivity;

/**
 * Created by Lin on 2018/4/4.
 */

public class PollingService extends Service {
    public static final String ACTION = "com.manshop.service.PollingService";

    private Notification mNotification;
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initNotifiManager();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }

    //初始化通知栏配置
    private void initNotifiManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;
//        mNotification = new Notification();
//        mNotification.icon = icon;
//        mNotification.tickerText = "New Message";
//        mNotification.defaults |= Notification.DEFAULT_SOUND;
//        mNotification.flags = Notification.FLAG_AUTO_CANCEL;

    }

    //弹出Notification
    private void showNotification() {
        mNotification.when = System.currentTimeMillis();
        //Navigator to the new activity when click the notification title
        Intent i = new Intent(this, DialogueActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
//        mNotification.setLatestEventInfo(this, getResources().getString(R.string.app_name), "You have new message!", pendingIntent);
        mNotification = new Notification.Builder(this)
                .setContentTitle("New Message")
//                .setContentText(subject)
                .setSmallIcon(R.drawable.ic_launcher)
//                .setLargeIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        mManager.notify(0, mNotification);
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     */
    int count = 0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            count ++;
            //当计数能被5整除时弹出通知
            if (count % 5 == 0) {
                showNotification();
                System.out.println("New message!");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }
}
