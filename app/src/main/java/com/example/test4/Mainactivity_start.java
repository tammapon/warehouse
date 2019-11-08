package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Mainactivity_start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainstart);
        findViewById(R.id.btnstar).setOnClickListener(
                new View.OnClickListener(){
                    @Override public void onClick(View v)
                    {
                        Intent intent = new Intent(Mainactivity_start.this,page_login.class);

                        startActivity(intent);
                    }
                }
        );
    }
}
