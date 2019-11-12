package com.example.test4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class page_register extends AppCompatActivity {
    //database
    public DatabaseReference myRef;

    Boolean check_A = false,check_B  =true;
    CheckBox checkbox_A,checkbox_ME;
    EditText id_regis,pass_regis,name_regis,mail_regis,phone_regis;
    Button create_register;
    String bufferName,bufferPass,bufferphone,buffermail;
    public static Double buffercode=0.0;
    public static String bufferID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_register);
        //database
        myRef = FirebaseDatabase.getInstance().getReference();

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
                //send data to datbase //
                bufferID = (String)id_regis.getText().toString();
                bufferName = (String)name_regis.getText().toString();
                bufferPass = (String)pass_regis.getText().toString();
                bufferphone = (String)phone_regis.getText().toString();
                buffermail = (String)mail_regis.getText().toString();

                //myRef.child("user").child(bufferID).child("name").setValue(bufferName);
                //myRef.child("user").child(bufferID).child("email").setValue(buffermail);
                //myRef.child("user").child(bufferID).child("password").setValue(bufferPass);
                //myRef.child("user").child(bufferID).child("phone").setValue(bufferphone);
                ///
                if(check_A.equals(true)){
                    //database

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String x = dataSnapshot.child("Databuffer").child("currentID").getValue().toString();
                            buffercode = Double.valueOf(x);
                            myRef.child("testcode").child("test").setValue(buffercode);
                            myRef.child(bufferID).child("data").child("ID").setValue(buffercode);
                            buffercode = buffercode+1.0;
                            myRef.child("Databuffer").child("currentID").setValue(buffercode);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    myRef.child(bufferID).child("data").child("name").setValue(bufferName);
                    myRef.child(bufferID).child("data").child("password").setValue(bufferPass);
                    myRef.child(bufferID).child("data").child("email").setValue(buffermail);
                    myRef.child(bufferID).child("data").child("phone").setValue(bufferphone);

                    //
                    startActivity(new Intent(page_register.this,page_login.class));
                    finish();
                }
                else if (check_B.equals(true)){
                    //database//
                    myRef.child("bufferUser").child(bufferID).child("name").setValue(bufferName);
                    myRef.child("bufferUser").child(bufferID).child("password").setValue(bufferPass);
                    myRef.child("bufferUser").child(bufferID).child("email").setValue(buffermail);
                    myRef.child("bufferUser").child(bufferID).child("phone").setValue(bufferphone);

                    //
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
