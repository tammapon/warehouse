package com.example.test4;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class page_employee extends AppCompatActivity {

    ArrayList<Product> listProduct;
    ProductListViewAdapter productListViewAdapter;
    ListView listViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_employee);

        listProduct = new ArrayList<>();

        addProduct("Export",12303,"Coke",1);
        addProduct("Import",10111,"cookie",5);
        addProduct("Export",12111,"pizza",10);
        addProduct("Export",13111,"fish",2);





        listViewProduct = findViewById(R.id.listproduct);
        productListViewAdapter = new ProductListViewAdapter(listProduct);
        listViewProduct.setAdapter(productListViewAdapter);


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

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(page_employee.this,"Scan QR Activity", Toast.LENGTH_LONG).show();
            }
        });

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
            ((TextView) viewProduct.findViewById(R.id.idproduct)).setText(String.format("ID: %d", product.productID));
            ((TextView) viewProduct.findViewById(R.id.nameproduct)).setText(String.format("Name: %s", product.name));
            ((TextView) viewProduct.findViewById(R.id.priceproduct)).setText(String.format("Amount: %d", product.amount));
            ((TextView) viewProduct.findViewById(R.id.tx_type)).setText(String.format("%s", product.type));


            return viewProduct;
        }
    }
}
