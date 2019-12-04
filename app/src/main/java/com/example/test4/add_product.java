package com.example.test4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.r0adkll.slidr.Slidr;

import java.text.SimpleDateFormat;
import java.util.Date;

public class add_product extends AppCompatActivity {
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        b2 = (Button)findViewById(R.id.backok);
        final EditText text_name = (EditText)findViewById(R.id.az_bar);
        final EditText text_amount = (EditText)findViewById(R.id.editText3);
        String dataStr = "01/01/2010";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date date1 = new Date();
        String current_date = sdf.format(date1);
        String[] spldate = current_date.split("/");
        final String dateIn = spldate[2]+spldate[1]+spldate[0];

        Log.e("==x",dateIn);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("xxx","OKKK");
                final String name = text_name.getText().toString();
                final String amount = text_amount.getText().toString();
                DatabaseReference myRef;
                myRef = FirebaseDatabase.getInstance().getReference();
                myRef = myRef.child(page_login.username).child("warehouse").child("A");
                Log.e("xxx",page_login.username);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int ID = Integer.valueOf(dataSnapshot.child("ID").getValue().toString());
                        ID = ID+1;
                        DatabaseReference newRef;
                        newRef = FirebaseDatabase.getInstance().getReference();
                        newRef = newRef.child(page_login.username).child("warehouse").child("A");
                        newRef.child("ID").setValue(ID);
                        newRef.child("bufferitem").child(name).child("ID").setValue(ID);
                        newRef.child("bufferitem").child(name).child("CODE").setValue(dateIn+":"+name+":"+String.valueOf(ID));
                        newRef.child("bufferitem").child(name).child("name").setValue(name);
                        newRef.child("bufferitem").child(name).child("amount").setValue(amount);
                        newRef.child("bufferitem").child(name).child("status").setValue("Import");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(new Intent(add_product.this,manager_product.class));
            }
        });
        Slidr.attach(this);
    }


}
