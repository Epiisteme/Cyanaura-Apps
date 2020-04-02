package com.epicknowledgesociety.cyanauraui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    Button Start;
    NotificationCompat.Builder notification;
    PendingIntent pIntent;
    NotificationManager manager;
    Intent resultIntent;
    TaskStackBuilder stackBuilder;
    public static final String NOTIFICATION_CHANNEL_ID ="Cyanaura";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Start = (Button)findViewById(R.id.start);
        Start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                startNotification();

            }

            protected void startNotification() {
                // TODO Auto-generated method stub
                //Creating Notification Builder

                notification = new NotificationCompat.Builder(Main2Activity.this,NOTIFICATION_CHANNEL_ID);
                notification.setSmallIcon(R.drawable.download);
                //Title for Notification
                notification.setContentTitle("Learn2Crack Updates");
                //Message in the Notification
                notification.setContentText("New Post on Android Notification.");
                //Alert shown when Notification is received
                notification.setTicker("New Message Alert!");
                //Icon to be set on Notification

                //Creating new Stack Builder
                stackBuilder = TaskStackBuilder.create(Main2Activity.this);
                stackBuilder.addParentStack(Result.class);
                //Intent which is opened when notification is clicked
                resultIntent = new Intent(Main2Activity.this, Result.class);
                stackBuilder.addNextIntent(resultIntent);
                pIntent =  stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pIntent);
                manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, notification.build());
      //          Toast.makeText(Main2Activity.this, "reached the end", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
