package com.example.notification_bcr_timemanager;


import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class Globals {

    public static final String CHANNEL_ID = "myBCRChannelID";
    public static final String CHANNEL_NAME = "myBCRNotificationChannel";
    public static void createNotificationChannel(Context context)
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        //Oreo -26 יצירת ערוץ רק אם מעל גירסה
        {
            android.app.NotificationChannel channel =
                    new android.app.NotificationChannel
                            (CHANNEL_ID, // זהו המזהה של הערוץ
                            CHANNEL_NAME,//  שם הערוץ
                            NotificationManager.IMPORTANCE_HIGH);//עדיפות


            //-לא ראיתי שעובד- הגדרות נוספות לערוץ: צבע, רטט,אור וכו'
//            channel.setDescription("This is a Channel for visit activity");
//            channel.enableVibration(true); // רטט
//            channel.setVibrationPattern(new
//                               long[]{100,200,300,400,500,400,300,200,400});
//            channel.setLightColor(Color.CYAN);
//            channel.enableLights(true);

            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }




}
