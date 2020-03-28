package com.epicknowledgesociety.cyanauraui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class InfectedPersonnel extends AppCompatActivity {
Button symptomdate,confirmdate,statusdate,recorddate;
String symptomdatestr,confirmdatestr,statusdatestr,recorddatestr;
int y=2020;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infected_personnel);
        symptomdate=(Button)findViewById(R.id.usymptomdate);
        confirmdate=(Button)findViewById(R.id.uconfirmdate);
        statusdate=(Button)findViewById(R.id.ustatusdate);
        recorddate=(Button)findViewById(R.id.urecordinsertdate);
symptomdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatePickerDialog d1=new DatePickerDialog(InfectedPersonnel.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                symptomdatestr=dayOfMonth+"/"+month+"/"+year;



            }
        },y,0,0);
        d1.show();

    }
});
confirmdate.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        DatePickerDialog d2=new DatePickerDialog(InfectedPersonnel.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                confirmdatestr=dayOfMonth+"/"+month+"/"+year;

            }
        },y,0,0);
        d2.show();

    }
});
statusdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatePickerDialog d3=new DatePickerDialog(InfectedPersonnel.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                statusdatestr=dayOfMonth+"/"+month+"/"+year;
            }
        },y,0,0);
        d3.show();

    }
});
recorddate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatePickerDialog d4=new DatePickerDialog(InfectedPersonnel.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                recorddatestr=dayOfMonth+"/"+month+"/"+year;
            }
        },y,0,0);
        d4.show();

    }
});

    }
}
