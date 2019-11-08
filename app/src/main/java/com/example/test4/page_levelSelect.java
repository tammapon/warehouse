package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class page_levelSelect extends AppCompatActivity {
    EditText level;
    Button btn_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_level_select);

        level =(EditText)findViewById(R.id.inputlevel);
        btn_level=(Button)findViewById(R.id.btn_level);

        btn_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.getText().toString().equals("addManager")){
                    //send data level to database
                    startActivity(new Intent(page_levelSelect.this,page_login.class));
                }
                else if(level.getText().toString().equals("addEmployee")){
                    //send data level to database
                    startActivity(new Intent(page_levelSelect.this,page_login.class));
                }
                else {
                    Toast.makeText(getApplication(),"errorCode!!!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
