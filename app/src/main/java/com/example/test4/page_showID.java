package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.r0adkll.slidr.Slidr;

public class page_showID extends AppCompatActivity {
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_show_id);

        btn_back=(Button)findViewById(R.id.btn_back_showid);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(page_showID.this,page_admin.class);
                startActivity(test);
            }
        });
        Slidr.attach(this);
    }
}
