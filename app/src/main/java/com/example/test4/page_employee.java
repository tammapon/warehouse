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
        Button refresh = (Button)findViewById(R.id.refresh3);
        listProduct = new ArrayList<>();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference();
                myRef = myRef.child("Lnwklui").child("warehouse").child("A").child("bufferitem");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> name = new ArrayList<String>();
                        ArrayList<String> ID = new ArrayList<String>();
                        ArrayList<String> Amount = new ArrayList<String>();
                        ArrayList<String> status = new ArrayList<String>();
                        ArrayList<String> code = new ArrayList<String>();

                        for (DataSnapshot child:dataSnapshot.getChildren()){
                            name.add(child.child("name").getValue().toString());
                            ID.add(child.child("ID").getValue().toString());
                            Amount.add(child.child("amount").getValue().toString());
                            status.add(child.child("status").getValue().toString());
                            code.add(child.child("CODE").getValue().toString());
                        }
                        Log.e("==x",code.toString());

                        Intent intent = getIntent();
                        intent.putExtra("passN",name);
                        intent.putExtra("passI",ID);
                        intent.putExtra("passA",Amount);
                        intent.putExtra("passS",status);
                        intent.putExtra("passC",code);
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        ArrayList<String> item = (ArrayList<String>)getIntent().getSerializableExtra("passN");
        if(item!=null) {
            ArrayList<String> id = (ArrayList<String>)getIntent().getSerializableExtra("passI");
            ArrayList<String> amount = (ArrayList<String>)getIntent().getSerializableExtra("passA");
            ArrayList<String> status = (ArrayList<String>)getIntent().getSerializableExtra("passS");
            ArrayList<String> code = (ArrayList<String>)getIntent().getSerializableExtra("passC");
            String dataStr = "01/01/2010";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = new Date();
            String current_date = sdf.format(date1);
            ArrayList<String> olditem = new ArrayList<String>();
            for (int a = 0; a < item.size();a++){
                addProduct(status.get(a), Integer.valueOf(id.get(a)), item.get(a), Integer.valueOf(id.get(a)));

                String[] datasplt = code.get(a).split("S");
                String date = datasplt[0];
                String year = "20"+Character.toString(date.charAt(0))+Character.toString(date.charAt(1));
                int month = Integer.valueOf(Character.toString(date.charAt(2))+Character.toString(date.charAt(3)))+1;
                String day = Character.toString(date.charAt(4))+Character.toString(date.charAt(5));
                String in_date = day+"/"+String.valueOf(month)+"/"+year;

                //Log.e("==x in_d",in_date);
                //Log.e("==x date",current_date);

                SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date d1 = DF.parse(in_date);
                    Date d2 = DF.parse(current_date);
                    //Log.e("==x d1",d1.toString());
                    //Log.e("==x d2",d2.toString());
                    if(d1.before(d2)){
                        Log.e("==x","yehee");
                        olditem.add(item.get(a));
                        Toast.makeText(getApplicationContext(), "Item will expire soon", Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Log.e("==x old item",olditem.toString());

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

