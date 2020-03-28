package com.epicknowledgesociety.cyanauraui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CheckBox isInfected;
    Spinner spinner1;
    Button symptomdate,confirmdate,statusdate,recorddate,submit1;
    String symptomdatestr,confirmdatestr,statusdatestr,recorddatestr,infsourcestr,inforiginstr;

    EditText infsource,inforigin;
    androidx.constraintlayout.widget.ConstraintLayout l1;
    int y=2020;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isInfected=(CheckBox)findViewById(R.id.isinfected);

        infsource=(EditText)findViewById(R.id.uinfectionsource);
        inforigin=(EditText)findViewById(R.id.uinfectionorigin);

        symptomdate=(Button)findViewById(R.id.usymptomdate);
        confirmdate=(Button)findViewById(R.id.uconfirmdate);
        statusdate=(Button)findViewById(R.id.ustatusdate);
        recorddate=(Button)findViewById(R.id.urecordinsertdate);
        submit1=(Button)findViewById(R.id.submit1);
        l1=( androidx.constraintlayout.widget.ConstraintLayout)findViewById(R.id.cl1);

        spinner1=(Spinner)findViewById(R.id.ugender);


        isInfected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(l1.getVisibility()==View.GONE) {
                    l1.setVisibility(View.VISIBLE);
                }
                else{
                   l1.setVisibility(View.GONE);
                }

   // infsourcestr=infsource.getText().toString();
    //inforiginstr=inforigin.getText().toString();


            }


        });

        symptomdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog d1=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog d2=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog d3=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog d4=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        recorddatestr=dayOfMonth+"/"+month+"/"+year;
                    }
                },y,0,0);
                d4.show();

            }
        });



        List<String> categories = new ArrayList<String>();
        categories.add("Male");
        categories.add("Female");
        categories.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);

    }

}
