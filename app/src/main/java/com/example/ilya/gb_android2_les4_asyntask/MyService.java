package com.example.ilya.gb_android2_les4_asyntask;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ilya on 12.03.2017.
 */

public class MyService extends Service {
    private NotificationManager mNotificationManager;
    private static final String TAGG = "TAGG";


    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.d(TAGG, "Service-onCreate: true");
    }


    // тут качаем файл
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAGG, "Service-onStartCommand: true");
        downLoadImage();
        sendNotif();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotif() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setSmallIcon(R.drawable.ic_image_black_24dp);
        mBuilder.setContentTitle("Загрузка картинки");
        mBuilder.setContentText("Качаем большую красивую картинку с яндекса и видом на море");
        Intent resultintent = new Intent(this, MainActivity.class);
        resultintent.putExtra(MainActivity.FILE_NAME, "somefilename");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultintent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }


    @Override
    public void onDestroy() {
        Log.d(TAGG, "Service-onDestroy: true");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAGG, "Service-onBind: true");
        return null;
    }

    void downLoadImage() {
        Log.d(TAGG, "Service-downLoadImage: true");



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.zwalls.ru/pic/201502/2560x1600/zwalls.ru-48974.jpg");
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    int lenghtOfFile = conexion.getContentLength();
                    InputStream is = url.openStream();
                    File testDirectory = new File(Environment.getExternalStorageDirectory() + "/Download");
                    if (!testDirectory.exists()) {
                        testDirectory.mkdir();
                    }
                    FileOutputStream fos = new FileOutputStream(testDirectory + "/pic.jpg");
                    byte data[] = new byte[1024];
                    long total = 0;
                    int count = 0;
                    while ((count = is.read(data)) != -1) {
                        total += count;
                        int progress_temp = (int) total * 100 / lenghtOfFile;
        /*publishProgress("" + progress_temp); //only for asynctask
        if (progress_temp % 10 == 0 && progress != progress_temp) {
            progress = progress_temp;
        }*/
                        fos.write(data, 0, count);
                    }
                    is.close();
                    fos.close();
                } catch (Exception e) {
                    Log.e("ERROR DOWNLOADING", "Unable to download" + e.getMessage());
                }
                stopSelf();
            }
        }).start();


    }
    }
