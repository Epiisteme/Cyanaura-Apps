package com.epicknowledgesociety.cyanaura_java;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;



public class AlreadyPairedList extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 0. Get the arrayList from the Intent. This was sent from MainActivity.java
        Intent intent = getIntent();
        ArrayList<BluetoothObject> arrayOfPairedDevices = intent.getParcelableArrayListExtra("arrayOfPairedDevices");

        // 1. Pass context and data to the custom adapter
        AlreadyPairedAdapter myAdapter = new AlreadyPairedAdapter(getApplicationContext(), arrayOfPairedDevices);

        // 2. setListAdapter
        setListAdapter(myAdapter);
    }
}//end class AlreadyPairedList