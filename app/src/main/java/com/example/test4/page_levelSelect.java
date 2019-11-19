package com.example.test4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class page_levelSelect extends AppCompatActivity {
    EditText level;
    Button btn_level;
    String codelevel;
    String[] codelevellist;
    String adminkey;
    String adminID;
    public DatabaseReference myRef;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_level_select);

        //database
        myRef = FirebaseDatabase.getInstance().getReference();

        level =(EditText)findViewById(R.id.inputlevel);
        btn_level=(Button)findViewById(R.id.btn_level);

        btn_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codelevel=(String)level.getText().toString();
                codelevellist = codelevel.split("x");

                query = myRef.orderByChild("ID").equalTo("10B"+codelevellist[1]);

                if(codelevellist[0].equals("2")){
                    //send data level to database
                    Log.e("xxx",query.toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                adminID=childSnapshot.getKey();
                                //Log.e("xxx",childSnapshot.getKey());
                            }
                            //adminkey = dataSnapshot.getChildren().toString();
                            ///Log.e("xxx",dataSnapshot.getKey());
                            //Log.e("xxx",adminkey);
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                    String x = dataSnapshot2.child(adminID).child("Databuffer").child("currentmanegerID").getValue().toString();
                                    Integer buffercode = Integer.valueOf(x);
                                    buffercode = buffercode+1;
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("ID").setValue("20A"+codelevellist[1]+"B"+buffercode.toString());
                                    myRef.child(adminID).child("Databuffer").child("currentmanegerID").setValue(buffercode);
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("name").setValue(page_register.bufferName);
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("password").setValue(page_register.bufferPass);
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("email").setValue(page_register.buffermail);
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("phone").setValue(page_register.bufferphone);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    startActivity(new Intent(page_levelSelect.this,page_login.class));
                }
                else if(codelevellist[0].equals("3")){
                    //send data level to database
                    Log.e("xxx",query.toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                adminID=childSnapshot.getKey();
                                //Log.e("xxx",childSnapshot.getKey());
                            }
                            //adminkey = dataSnapshot.getChildren().toString();
                            ///Log.e("xxx",dataSnapshot.getKey());
                            //Log.e("xxx",adminkey);
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                    String x = dataSnapshot2.child(adminID).child("Databuffer").child("currentemID").getValue().toString();
                                    Integer buffercode = Integer.valueOf(x);
                                    buffercode = buffercode+1;
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("ID").setValue("30A"+codelevellist[1]+"B"+buffercode.toString());
                                    myRef.child(adminID).child("Databuffer").child("currentemID").setValue(buffercode);
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("name").setValue(page_register.bufferName);
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("password").setValue(page_register.bufferPass);
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("email").setValue(page_register.buffermail);
                                    myRef.child(adminID).child("user").child(page_register.bufferID).child("phone").setValue(page_register.bufferphone);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    startActivity(new Intent(page_levelSelect.this,page_login.class));
                }
                else {
                    Toast.makeText(getApplication(),"errorCode!!!",Toast.LENGTH_LONG).show();
                }
                /*
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
                }*/

            }
        });

    }
}
