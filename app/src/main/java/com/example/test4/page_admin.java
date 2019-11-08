package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class page_admin extends AppCompatActivity {
    Button btn_add_man,btn_remove_man,btn_add_em,btn_remove_em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_admin);

        btn_add_em=(Button)findViewById(R.id.btn_addEmployee);
        btn_remove_em=(Button)findViewById(R.id.btn_removeEmployee);
        btn_add_man=(Button)findViewById(R.id.btn_addManager);
        btn_remove_man=(Button)findViewById(R.id.btn_removeManager);

    }
}
