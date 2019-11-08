package com.example.test4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public DatabaseReference myRef;
    private TextView mFirebaseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_login);
        Intent T1 = new Intent(MainActivity.this,page_login.class);
        startActivity(T1);
        //setContentView(R.layout.activity_main);
        /*mFirebaseTextView = (TextView)findViewById(R.id.firebaseTextView);
        // Get firebase database ref
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef =database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map)dataSnapshot.getValue();
                String x = String.valueOf(map.get("testcode"));
                mFirebaseTextView.setText(x);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    /*public void onClickAddButton(View view) {

        Map<String, Object> value = new HashMap<String, Object>();
        value.put("testcode","Lnwsoftdev");
        myRef.updateChildren(value);
        myRef.child("testcode").child("Lnwsoftdev").setValue("001");
        myRef.child("testcode").child("Noobsoftdev").setValue("003");
    }*/
}
//kkk
