package com.example.test4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        myTextView.setText("Code : "+"2x"+page_login.userID);
        TextView myTextView2 = (TextView) findViewById(R.id.myTextView2);
        myTextView2.setText("Code : "+"3x"+page_login.userID);


    }
}
