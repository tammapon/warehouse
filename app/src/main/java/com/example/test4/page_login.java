package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class page_login extends AppCompatActivity {
    EditText id_login,pass_login;
    Button access_login,register_login;
    String level = "A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_login);

        id_login=(EditText)findViewById(R.id.id_login);
        pass_login=(EditText)findViewById(R.id.pass_login);
        access_login=(Button)findViewById(R.id.access_login);
        register_login=(Button)findViewById(R.id.register_login);

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(page_login.this,page_register.class));
            }
        });

        access_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_login.getText().toString().equals("napat")&&pass_login.getText().toString().equals("napat")){
                    Toast.makeText(getApplication(),"success",Toast.LENGTH_LONG).show();
                    //send data to databse and return level to check
                    if(level.equals("A")){
                        startActivity(new Intent(page_login.this,page_admin.class));
                    }
                    else if(level.equals("B")){
                        startActivity(new Intent(page_login.this,page_manager.class));
                    }
                    else if(level.equals("C")){
                        startActivity(new Intent(page_login.this,page_employee.class));
                    }
                }
                else{
                    Toast.makeText(getApplication(),"fail",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
//mknnkmkm
