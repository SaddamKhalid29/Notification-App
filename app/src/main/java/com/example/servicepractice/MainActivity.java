package com.example.servicepractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private static final String CHANNEL_ID ="My Notification" ;
    Button btnStart,btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);

        createNotificationChannel();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent resintent=new Intent(MainActivity.this,ASecService.class);
//                PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,resintent,0);
                NotificationCompat.Builder build=new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
                build.setSmallIcon(R.drawable.ic_launcher_background);
                build.setContentTitle("My Ring Notification");
                build.setContentText("This is notification from the notifying app...");
                build.setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("This is notification from the notifying app..."))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager=NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(123,build.build());
                Intent intent=new Intent(MainActivity.this,ASecService.class);
                startService(intent);
//                build.setContentIntent(pendingIntent);
//                build.addAction(R.drawable.ic_launcher_foreground,getString(R.string.snooze),pendingIntent);

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ASecService.class);
                stopService(intent);
            }
        });

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}