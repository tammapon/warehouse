package com.example.test4;

import java.text.*;
import java.util.Date;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class page_employee extends AppCompatActivity {

    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private DatabaseReference myRef;
    ArrayList<Product> listProduct;
    ProductListViewAdapter productListViewAdapter;
    ListView listViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_employee);
        setupToolbar();
        Button refresh3 = (Button) findViewById(R.id.refresh3);
        listProduct = new ArrayList<>();

        refresh3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef;
                myRef = FirebaseDatabase.getInstance().getReference();
                myRef = myRef.child("Lnwklui").child("warehouse").child("A").child("bufferitem");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> name = new ArrayList<String>();
                        ArrayList<String> id = new ArrayList<String>();
                        ArrayList<String> type = new ArrayList<String>();
                        ArrayList<String> amount = new ArrayList<String>();
                        ArrayList<String> code = new ArrayList<String>();
                        for (DataSnapshot ItemName:dataSnapshot.getChildren()){
                            name.add(ItemName.child("name").getValue().toString());
                            id.add(ItemName.child("ID").getValue().toString());
                            type.add(ItemName.child("status").getValue().toString());
                            amount.add(ItemName.child("amount").getValue().toString());
                            code.add(ItemName.child("CODE").getValue().toString());
                        }
                        Log.e("==x id",id.toString());
                        Log.e("==x nm",name.toString());
                        Log.e("==x ty",type.toString());
                        Log.e("==x am",amount.toString());
                        Log.e("==x cd",code.toString());

                        Intent intent = getIntent();
                        finish();
                        intent.putExtra("passid",id);
                        intent.putExtra("passnm",name);
                        intent.putExtra("passty",type);
                        intent.putExtra("passam",amount);
                        intent.putExtra("passcd",code);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        ArrayList<String> id =(ArrayList<String>)getIntent().getSerializableExtra("passid");
    if (id != null) {
        ArrayList<String> name =(ArrayList<String>)getIntent().getSerializableExtra("passnm");
        ArrayList<String> type =(ArrayList<String>)getIntent().getSerializableExtra("passty");
        ArrayList<String> amth =(ArrayList<String>)getIntent().getSerializableExtra("passam");
        ArrayList<String> code =(ArrayList<String>)getIntent().getSerializableExtra("passcd");

        for (int a = 0; a< name.size();a++){
            String atype  = type.get(a);
            int aamth     = Integer.valueOf(amth.get(a));
            String aname  = name.get(a);
            int aid = Integer.valueOf(id.get(a));
            addProduct(atype, aid, aname, aamth);
        }
        listViewProduct = findViewById(R.id.listzone);
        productListViewAdapter = new ProductListViewAdapter(listProduct);
        listViewProduct.setAdapter(productListViewAdapter);
    }

    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void launchFullActivity(View v) {
        //Toast.makeText(page_employee.this,"Scan QR IN", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, FullScannerActivity.class));
    }

    public void launchOutActivity(View v) {
        //Toast.makeText(page_employee.this,"Scan QR OUT", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, ScanOutActivity.class));
    }


    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void addProduct(String type,int id,String name,int amount){
        listProduct.add(new Product(type,id, name, amount));
    }


    class Product {
        String name;
        String type;
        int amount;
        int productID;

        public Product(String t,int productID, String name, int am) {
            this.type = t;
            this.name = name;
            this.amount = am;
            this.productID = productID;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return amount;
        }
    }

    class ProductListViewAdapter extends BaseAdapter {

        final ArrayList<Product> listProduct;

        ProductListViewAdapter(ArrayList<Product> listProduct) {
            this.listProduct = listProduct;
        }

        @Override
        public int getCount() {

            return listProduct.size();
        }

        @Override
        public Object getItem(int position) {

            return listProduct.get(position);
        }

        @Override
        public long getItemId(int position) {

            return listProduct.get(position).productID;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View viewProduct;
            if (convertView == null) {
                viewProduct = View.inflate(parent.getContext(), R.layout.product_view, null);
            } else viewProduct = convertView;


            Product product = (Product) getItem(position);
            ((TextView) viewProduct.findViewById(R.id.amount)).setText(String.format("ID: %d", product.productID));
            ((TextView) viewProduct.findViewById(R.id.nameproduct)).setText(String.format("Name: %s", product.name));
            ((TextView) viewProduct.findViewById(R.id.priceproduct)).setText(String.format("Amount: %d", product.amount));
            ((TextView) viewProduct.findViewById(R.id.tx_type)).setText(String.format("%s", product.type));


            return viewProduct;
        }
    }
}


//        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Product product = (Product) productListViewAdapter.getItem(position);
//                Toast.makeText(MainActivity.this, product.name, Toast.LENGTH_LONG).show();
//            }
//        });


//        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listProduct.size() > 0) {
//                    int productpost = 0;
//                    listProduct.remove(productpost);
//                    productListViewAdapter.notifyDataSetChanged();
//                }
//            }
//        });

