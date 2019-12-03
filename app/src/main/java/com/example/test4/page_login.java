package com.example.test4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class page_login extends AppCompatActivity {

    //database
    public DatabaseReference myRef;

    EditText id_login,pass_login;
    Button access_login,register_login;
    String level = "A";
    String ID,pass,ID_db,pass_b;
    public static String username=" ";
    public static String userID=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //database
        myRef = FirebaseDatabase.getInstance().getReference();
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
                ID = id_login.getText().toString();
                pass = pass_login.getText().toString();
                Log.e("xxx",ID);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Map map = (Map) dataSnapshot.getValue();
                        //username = map.get(ID).toString();
                        //if (map.get(ID)){
                        //    ...
                        //}
                        //Log.e("xxx",username);
                        //Log.e("xxx",dataSnapshot.getChildren()[]);
                        Integer i=0,ck=0;
                        aa:
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            Log.e("xxx",i.toString());
                            Log.e("xxx",childSnapshot.getKey());

                            i+=1;
                            if(childSnapshot.getKey().equals(ID)){
                                Log.e("xxx","scscscsc");
                                Log.e("xxx",dataSnapshot.child(ID).child("data").child("password").getValue().toString());
                                if(dataSnapshot.child(ID).child("data").child("password").getValue().toString().equals(pass)){
                                    Log.e("xxx","scscscsc1111");
                                    username=childSnapshot.getKey();
                                    userID=dataSnapshot.child(childSnapshot.getKey()).child("ID").getValue().toString().split("B")[1];
                                    startActivity(new Intent(page_login.this,page_admin.class));
                                    ck+=1;
                                    break aa;
                                }
                            }
                            else if (childSnapshot.getKey().equals("Databuffer")){
                            }
                            else if (childSnapshot.getKey().equals("testcode")){

                            }
                            else {
                                Log.e("xxx",dataSnapshot.child(childSnapshot.getKey()).child("user").getKey());
                                bb:
                                for (DataSnapshot childSnapshot2: dataSnapshot.child(childSnapshot.getKey()).child("user").getChildren()){
                                    Log.e("xxx",childSnapshot2.getKey());
                                    if(childSnapshot2.getKey().equals(ID)){
                                        if(dataSnapshot.child(childSnapshot.getKey()).child("user").child(ID).child("password").getValue().toString().equals(pass)){
                                            Log.e("xxx",dataSnapshot.child(childSnapshot.getKey()).child("user").child(ID).child("password").getValue().toString());
                                            String[] IDcode=dataSnapshot.child(childSnapshot.getKey()).child("user").child(ID).child("ID").getValue().toString().split("A");
                                            if (IDcode[0].equals("20")){
                                                Log.e("xxx","scscscsc2222");
                                                username=childSnapshot.getKey();
                                                userID=dataSnapshot.child(childSnapshot.getKey()).child("ID").getValue().toString().split("B")[1];
                                                startActivity(new Intent(page_login.this,manager_product.class));
                                                ck+=1;
                                                break aa;
                                            }
                                            else if(IDcode[0].equals("30")){
                                                Log.e("xxx","scscscsc3333");
                                                username=childSnapshot.getKey();
                                                userID=dataSnapshot.child(childSnapshot.getKey()).child("ID").getValue().toString().split("B")[1];
                                                startActivity(new Intent(page_login.this,page_employee.class));
                                                ck+=1;
                                                break aa;
                                            }
                                        }
                                    }
                                    else {
                                        //Toast.makeText(getApplication(),"fail",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                        if(ck.equals(0)){
                            Toast.makeText(getApplication(),"fail",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (id_login.getText().toString().equals("napat")&&pass_login.getText().toString().equals("napat")){
                    Toast.makeText(getApplication(),"success",Toast.LENGTH_LONG).show();
                    //send data to databse and return level to check
                    if(level.equals("A")){
                        startActivity(new Intent(page_login.this,page_admin.class));
                    }
                    else if(level.equals("B")){
                        startActivity(new Intent(page_login.this,manager_product.class));
                    }
                    else if(level.equals("C")){
                        startActivity(new Intent(page_login.this,page_employee.class));
                    }
                }
                else{
                    //Toast.makeText(getApplication(),"fail",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
//pun susu
