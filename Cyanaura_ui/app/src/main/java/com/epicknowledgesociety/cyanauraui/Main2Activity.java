package com.epicknowledgesociety.cyanauraui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    Button Start;
    NotificationCompat.Builder notification;
    PendingIntent pIntent;
    NotificationManager manager;
    Intent resultIntent;
    TaskStackBuilder stackBuilder;
    private NotificationManager notifManager;
    Button bt1;
    ArrayList<String> detval=new ArrayList<String>();

    ListView dataListView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Start = (Button)findViewById(R.id.start);
        bt1=(Button)findViewById(R.id.bt1);
        dataListView = (ListView) findViewById(R.id.listview);
       final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, detval){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){

                        // Get the current item from ListView
                        View view = super.getView(position,convertView,parent);
                        if(position%8>=4)
                        {
                            // Set a background color for ListView regular row/item
                            view.setBackgroundColor(Color.parseColor("#FFB6B546"));

                        }
                        else
                        {
                            // Set the background color for alternate row/item
                            view.setBackgroundColor(Color.parseColor("#00FFFF"));

                        }
                        return view;
                    }
                };

        Start.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

               createNotification("New alert",getApplicationContext());

            }

            @RequiresApi(api = Build.VERSION_CODES.O)



                public void createNotification(String aMessage, Context context) {
                    final int NOTIFY_ID = 0; // ID of notification
                    String id = "channel1"; // default_channel_id
                    String title ="cyanaura"; // Default Channel
                    Intent intent;
                    PendingIntent pendingIntent;
                    NotificationCompat.Builder builder;
                    if (notifManager == null) {
                        notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                        if (mChannel == null) {
                            mChannel = new NotificationChannel(id, title, importance);
                            mChannel.enableVibration(true);
                            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                            notifManager.createNotificationChannel(mChannel);
                        }
                        builder = new NotificationCompat.Builder(context, id);
                        intent = new Intent(context, Result.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                        builder.setContentTitle(aMessage)                            // required
                                .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                                .setContentText(context.getString(R.string.app_name)) // required
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .setTicker(aMessage)
                                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    }
                    else {
                        builder = new NotificationCompat.Builder(context, id);
                        intent = new Intent(context, Result.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                        builder.setContentTitle(aMessage)                            // required
                                .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                                .setContentText(context.getString(R.string.app_name)) // required
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .setTicker(aMessage)
                                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                                .setPriority(Notification.PRIORITY_HIGH);
                    }
                    Notification notification = builder.build();
                    notifManager.notify(NOTIFY_ID, notification);
                }

      //          Toast.makeText(Main2Activity.this, "reached the end", Toast.LENGTH_SHORT).show();


        });


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothSPP bt = new BluetoothSPP(getApplicationContext());
                if(!bt.isBluetoothAvailable()) {
                    // any command for bluetooth is not available
                    Toast.makeText(Main2Activity.this, "Bluetooth Not available for this device", Toast.LENGTH_SHORT).show();
                }
                if(!bt.isBluetoothEnabled()) {
                    // Do somthing if bluetooth is disable
                    Toast.makeText(Main2Activity.this, "Bluetooth Not enabled for this device", Toast.LENGTH_SHORT).show();

                } else {
                    // Do something if bluetooth is already enable
                    Toast.makeText(Main2Activity.this, "Bluetooth  on for this device", Toast.LENGTH_SHORT).show();
                    bt.startService(BluetoothState.DEVICE_ANDROID);
                    bt.autoConnect("");
                    bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
                        public void onNewConnection(String name, String address) {
                            detval.add("Name:"+name);
                            detval.add("Address:"+address);

                            dataListView.setAdapter(itemsAdapter);
                            // Do something when searching for new connection device


                        }

                        public void onAutoConnectionStarted() {
                            // Do something when auto connection has started
                            Toast.makeText(Main2Activity.this, "Autoconnection started", Toast.LENGTH_LONG).show();
                        }
                    });


                }

            }
        });
    }

}
