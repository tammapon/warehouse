package com.example.test4;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class manager_zone extends AppCompatActivity {

    ArrayList<Product> listProduct;
    ProductListViewAdapter productListViewAdapter;
    ListView listViewProduct;
    Dialog myDialog;
    Button btn_toproduct,btn_toaddzone,btn_show;
    private int ValueOfWarehouse = 5000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_zone);
        myDialog = new Dialog(this);

        btn_toaddzone=(Button)findViewById(R.id.btnToaddZone);
        btn_toproduct=(Button)findViewById(R.id.btnToProduct);
        btn_show=(Button)findViewById(R.id.money2);

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(manager_zone.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.windowpopup);
                Button ok = (Button)dialog.findViewById(R.id.button_value);
                TextView show_value = (TextView)dialog.findViewById(R.id.text_data);
                show_value.setText(String.valueOf("         Values : "+ ValueOfWarehouse+" Bath."));

                ok.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        btn_toproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(manager_zone.this,manager_product.class);
                startActivity(test);
            }
        });
        btn_toaddzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(manager_zone.this,page_add_zone.class);
                startActivity(test);
            }
        });


        listProduct = new ArrayList<>();
        addZone("A",15);
        addZone("B",15);
        addZone("C",15);

//        addProduct("Export",12303,"Coke",1);
//        addProduct("Import",10111,"cookie",5);
//        addProduct("Export",12111,"pizza",10);
//        addProduct("Export",13111,"fish",2);





        listViewProduct = findViewById(R.id.listzone);
        productListViewAdapter = new ProductListViewAdapter(listProduct);
        listViewProduct.setAdapter(productListViewAdapter);

    }


    private void addZone(String name,int amount){
        listProduct.add(new Product(name, amount));
    }


    class Product {
        String name;
        String type;
        int amount;
        int productID;

        public Product(String name, int am) {
            this.name = name;
            this.amount = am;

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
                viewProduct = View.inflate(parent.getContext(), R.layout.custom_zone, null);
            } else viewProduct = convertView;


            Product product = (Product) getItem(position);
            //((TextView) viewProduct.findViewById(R.id.idproduct)).setText(String.format("ID: %d", product.productID));
            ((TextView) viewProduct.findViewById(R.id.nameproduct)).setText(String.format("Name: %s", product.name));
            ((TextView) viewProduct.findViewById(R.id.amount)).setText(String.format("free slot : %d", product.amount));
            //((TextView) viewProduct.findViewById(R.id.tx_type)).setText(String.format("%s", product.type));


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

