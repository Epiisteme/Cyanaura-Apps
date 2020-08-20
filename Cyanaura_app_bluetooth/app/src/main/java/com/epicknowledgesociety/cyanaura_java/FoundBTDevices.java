package com.epicknowledgesociety.cyanaura_java;

import android.Manifest;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static java.util.UUID.fromString;

public class FoundBTDevices extends ListActivity
{
    public static  BluetoothAdapter mBluetoothAdapter;
    public static UUID uuid;
    int count = 0;
    private ArrayList<BluetoothObject> arrayOfFoundBTDevices;
    public ArrayList<BluetoothDevice> devicesArray = new ArrayList<>();
    final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                // Get the bluetoothDevice object from the Intent
                count ++;
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicesArray.add(device);
                // Get the "RSSI" to get the signal strength as integer,
                // but should be displayed in "dBm" units
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);

                // Create the device object and add it to the arrayList of devices
                BluetoothObject bluetoothObject = new BluetoothObject();
                bluetoothObject.setBluetooth_name(device.getName());
                bluetoothObject.setBluetooth_address(device.getAddress());
                bluetoothObject.setBluetooth_state(device.getBondState());
                bluetoothObject.setBluetooth_type(device.getType());    // requires API 18 or higher
                bluetoothObject.setBluetooth_uuids(device.getUuids());
                bluetoothObject.setBluetooth_rssi(rssi);
                // send data to discovered device
                new AcceptThread(device.getUuids()[0].getUuid().toString()).start();
                new ConnectThread(device).start();


                arrayOfFoundBTDevices.add(bluetoothObject);

                // 1. Pass context and data to the custom adapter
                FoundBTDevicesAdapter adapter = new FoundBTDevicesAdapter(getApplicationContext(), arrayOfFoundBTDevices);

                // 2. setListAdapter
                setListAdapter(adapter);
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){

                for(BluetoothDevice d: devicesArray){
                    d.fetchUuidsWithSdp();
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
       displayListOfFoundDevices();
    }

    private void displayListOfFoundDevices()
    {
        arrayOfFoundBTDevices = new ArrayList<BluetoothObject>();

        // start looking for bluetooth devices
        mBluetoothAdapter.startDiscovery();

        // Discover new devices
        // Create a BroadcastReceiver for ACTION_FOUND

        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);

    }

    @Override
    protected void onPause()
    {

        mBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);
        super.onPause();

    }

}

class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private InputStream inputStream;
    public AcceptThread(String uuid) {
        BluetoothServerSocket tmp = null;

        try {
            tmp = FoundBTDevices.mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(FoundBTDevices.mBluetoothAdapter.getName(),UUID.fromString(uuid));
            Log.i("abcd","server listening at "+ uuid);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }
    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }
            if (socket != null) {
                Log.i("abcd","socket accepted");
                try {
                    inputStream = socket.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[1024];
                int numBytes;
                while(true){
                    try {
                        numBytes = inputStream.read(buffer);
                        Log.i("abcd",buffer.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            break;
            }
        }
    }

    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}

class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private  OutputStream outputStream;
    public ConnectThread(BluetoothDevice device) {
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {
            tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(mmDevice.getUuids()[0].getUuid());
            Log.i("abcd","Client socket created, uuid - "+ mmDevice.getUuids()[0].getUuid());
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }
    public void run() {
        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            Log.i("abcd","could not connect : "+ connectException);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }
        OutputStream out = null;
        try{
            out = mmSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputStream = out;
        String m = "abcde";
        byte[] msg = m.getBytes();
        try {
            outputStream.write(msg);
            Log.i("abcd","data sent");
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }
}