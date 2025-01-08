package com.example.notification_bcr_timemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn2sec;
    private Button btn5sec;
    private Button btn30sec;

    private int code = 0;

    private static  final int REQUEST_CODE_POST_NOTIFICATIONS = 1;
    // Define constants for the delays
    private static final int TWO_SECONDS = 2;
    private static final int FIVE_SECONDS = 5 ;
    private static final int THIRTY_SECONDS = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Globals.createNotificationChannel(this);

        this.btn2sec=findViewById(R.id.two_seconds);
        this.btn2sec.setOnClickListener(this);
        this.btn5sec=findViewById(R.id.five_seconds);
        this.btn5sec.setOnClickListener(this);
        this.btn30sec =findViewById(R.id.thirty_seconds);
        this.btn30sec.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {


        if (v == btn2sec) {
            checkPermissionAndScheduleNotification(TWO_SECONDS);

        } else if (v == btn5sec) {
            checkPermissionAndScheduleNotification(FIVE_SECONDS);
        } else if (v == btn30sec) {
            checkPermissionAndScheduleNotification(THIRTY_SECONDS);
        }


    }




    // Function to check permission and schedule notification
    private void checkPermissionAndScheduleNotification(int delay) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                scheduleNotification(delay);
            } else {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, delay);
            }
        } else {
            // For older versions, assume permission is granted
            scheduleNotification(delay);
        }
    }

    // Override onRequestPermissionsResult to handle the permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == TWO_SECONDS || requestCode == FIVE_SECONDS || requestCode ==  THIRTY_SECONDS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scheduleNotification(requestCode);
            } else {
                // Handle permission denied case
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }



    private void scheduleNotification(int code) {
        Calendar calendar = Calendar.getInstance(); //נוכחי מגדירים תאריך
        Intent intent = new Intent(MainActivity.this, NotificationReceiver.class);
        intent.putExtra("seconds", code);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(  //   יצירת אינטנט דחוי לשידור
                MainActivity.this,
                code, //קוד השידור
                intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService
                (Context.ALARM_SERVICE);
        // השהיית השידור לזמן הנתון!
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis() + code * 1000, pendingIntent);
    }


}

