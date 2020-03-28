package com.epicknowledgesociety.cyanauraui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    CheckBox isInfected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isInfected=(CheckBox)findViewById(R.id.isinfected);
        isInfected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,InfectedPersonnel.class);
                isInfected.setChecked(false);
                startActivity(intent);
            }
        });

    }
}
