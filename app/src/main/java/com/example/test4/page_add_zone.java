package com.example.test4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class page_add_zone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_add_zone);

    }

    public void back_2_managerzone(View v) {
        Intent next = new Intent(this, manager_zone.class);
        startActivity(next);
    }

    public void back_ok(View v) {
        Intent next = new Intent(this, manager_zone.class);
        startActivity(next);
    }

}
