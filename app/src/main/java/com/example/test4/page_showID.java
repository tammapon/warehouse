package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

public class page_showID extends AppCompatActivity {
    Button btn_back;
    String str = "abc";
    String ssid = "11111111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_show_id);
        setContentView(R.layout.activity_page_show_id);
        TextView myTextView = (TextView) findViewById(R.id.myTextView);
        myTextView.setText("code:manager");
        TextView myTextView2 = (TextView) findViewById(R.id.myTextView2);
        myTextView2.setText("code:employee");


    }
}
