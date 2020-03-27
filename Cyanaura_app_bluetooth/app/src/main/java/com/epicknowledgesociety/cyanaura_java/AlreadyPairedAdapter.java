package com.epicknowledgesociety.cyanaura_java;

import android.content.Context;
import android.nfc.tech.TagTechnology;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class AlreadyPairedAdapter extends ArrayAdapter<BluetoothObject>
{
    private Context context;
    private ArrayList<BluetoothObject> arrayOfAlreadyPairedDevices;

    public AlreadyPairedAdapter(Context context, ArrayList<BluetoothObject> arrayOfAlreadyPairedDevices)
    {
        super(context, R.layout.row_bt_already_paired, arrayOfAlreadyPairedDevices);

        this.context = context;
        this.arrayOfAlreadyPairedDevices = arrayOfAlreadyPairedDevices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        BluetoothObject bluetoothObject = arrayOfAlreadyPairedDevices.get(position);

        // 1. Create Inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row_bt_already_paired, parent, false);

        // 3. Get the widgets from the rowView
        TextView bt_name = (TextView) rowView.findViewById(R.id.textview_bt_paired_name);
        TextView bt_address = (TextView) rowView.findViewById(R.id.textview_bt_paired_address);
        TextView bt_bondState = (TextView) rowView.findViewById(R.id.textview_bt_paired_bond_state);
        TextView bt_type = (TextView) rowView.findViewById(R.id.textview_bt_paired_type);

        // 4. Set the text for each widget
        bt_name.setText(bluetoothObject.getBluetooth_name());
        bt_address.setText("address: " + bluetoothObject.getBluetooth_address());
        bt_bondState.setText("state: " + bluetoothObject.getBluetooth_state());
        bt_type.setText("type: " + bluetoothObject.getBluetooth_type());

        // 5. return rowView
        return rowView;

    }//end getView()


}//end class AlreadyPairedAdapter































