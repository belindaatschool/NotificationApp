package com.example.notification_bcr_timemanager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver implements MediaPlayer.OnCompletionListener {


    public static final String TAG = "NotificationReceiver";
    @Override
    //התקבל שידור
    public void onReceive(Context context, Intent intent)
    {

        MediaPlayer mp = MediaPlayer.create(context, R.raw.gudok);
        mp.start();//raw הוספת צליל להתראה. את קובץ הצליל מוסיפים לתיקיה
        mp.setOnCompletionListener(this);// נדאג לשחרור משאבים לאחר השמעת הצליל

        int seconds=intent.getIntExtra("seconds",0);
        // בניית התראה
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, Globals.CHANNEL_ID)//מזהה הערוץ!
                .setSmallIcon(R.drawable.lion)// תמונה
                .setContentTitle("התראה מתוזמנת")// כותרת ההתראה
                .setContentText("עברו "+ seconds +" שניות " )// ההודעה
                .setPriority(NotificationCompat.PRIORITY_HIGH);//עדיפות

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
        {

            Log.d(TAG, "Notification Permission not granted");
            return;
        }
        //שיגור ההתראה
        notificationManager.notify( seconds, builder.build());// מזהה ההתראה


    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
        mediaPlayer.release();
    }



}