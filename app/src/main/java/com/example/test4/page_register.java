package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class page_register extends AppCompatActivity {
    Boolean check_A = false,check_B  =false;
    CheckBox checkbox_A,checkbox_ME;
    EditText id_regis,pass_regis,name_regis,mail_regis,phone_regis;
    Button create_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_register);
        create_register=(Button)findViewById(R.id.button_create_regis);
        checkbox_A=(CheckBox)findViewById(R.id.checkBox_admin);
        checkbox_ME=(CheckBox)findViewById(R.id.checkBox_manager_employee);
        id_regis=(EditText)findViewById(R.id.id_regis);
        pass_regis=(EditText)findViewById(R.id.pass_regis);
        name_regis=(EditText)findViewById(R.id.name_regis);
        mail_regis=(EditText)findViewById(R.id.mail_regis);
        phone_regis=(EditText)findViewById(R.id.phone_regis);
        create_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send data to datbase
                if(check_A.equals(true)){
                    startActivity(new Intent(page_register.this,page_login.class));
                }
                else if (check_B.equals(true)){
                    startActivity(new Intent(page_register.this,page_levelSelect.class));
                }
                else {
                    Toast.makeText(getApplication(),"please select level!!!",Toast.LENGTH_LONG).show();
                }
            }
        });

        checkbox_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_A.isChecked()){
                    Toast.makeText(getApplication(),"check_Admin",Toast.LENGTH_LONG).show();
                    check_A = true;
                }
                else {
                    Toast.makeText(getApplication(),"check_Admin_cancle",Toast.LENGTH_LONG).show();
                    check_A = false;
                }
            }
        });

        checkbox_ME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_ME.isChecked()){
                    Toast.makeText(getApplication(),"check_manager",Toast.LENGTH_LONG).show();
                    check_B = true;
                }
                else {
                    Toast.makeText(getApplication(),"check_manager_cancle",Toast.LENGTH_LONG).show();
                    check_B=false;
                }
            }
        });
    }
}
