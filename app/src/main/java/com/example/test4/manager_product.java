package com.example.test4;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class manager_product extends AppCompatActivity {

    private SwipeMenuListView swipeListView;
    private CustomListView adapter;
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> zone = new ArrayList<String>();
    private ArrayList<String> Number = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<Integer> amount = new ArrayList<>();
    Button btn_tozone,btn_toaddproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_product);

       btn_toaddproduct=(Button)findViewById(R.id.btnToaddZone);
       btn_tozone=(Button)findViewById(R.id.btnToZone);

        btn_toaddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(manager_product.this,add_product.class);
                startActivity(test);
            }
        });
        btn_tozone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(manager_product.this,manager_zone.class);
                startActivity(test);
            }
        });

        swipeListView = findViewById(R.id.swipeZone);

        //create data to listview
        setDataListView("0200","cookie","A",20);
        setDataListView("0400","oreo","A",20);
        setDataListView("0600","pizza","A",20);



        setSwipeListView();

        adapter = new CustomListView(this);
        swipeListView.setAdapter(adapter);

    }

    private void setDataListView(String ID,String NAME,String ZONE,int AMOUNT) {
        id.add(ID);
        name.add(NAME);
        zone.add(ZONE);
        amount.add(AMOUNT);
    }

    private void setSwipeListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(manager_product.this);
                // set item background
                deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_arrow);
                // set item title
                deleteItem.setTitle("export");
                // set item title fontsize
                deleteItem.setTitleSize(14);
                // set item title font color
                deleteItem.setTitleColor(getResources().getColor(R.color.gray));
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        swipeListView.setMenuCreator(creator);
        swipeListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                final Dialog dialog = new Dialog(manager_product.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_data);
                TextView dialog_title = (TextView)dialog.findViewById(R.id.dialog_title);
                dialog_title.setText(String.valueOf("Export Prouct"));

                TextView dialog_description = (TextView)dialog.findViewById(R.id.dialog_description);
                dialog_description.setText(String.valueOf("You want export this "+name.get(position)+"?"));

                Button buttonCancel = (Button)dialog.findViewById(R.id.buttonCancel);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                Button buttonOK = (Button)dialog.findViewById(R.id.buttonOK);
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        name.remove(position);
                        id.remove(position);
                        zone.remove(position);
                        amount.remove(position);

                        adapter.notifyDataSetChanged();


                        dialog.cancel();
                    }
                });

                dialog.show();

                return false;
            }
        });
    }


    //item promotion
    private class CustomListView extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private CustomListView(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return name.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if(convertView == null) {
                convertView = mInflater.inflate(R.layout.custom_manag_product, null);
                holder = new ViewHolder();

                holder.header = (TextView) convertView.findViewById(R.id.text_header);
                holder.id = (TextView) convertView.findViewById(R.id.text_id);
                holder.zone = (TextView) convertView.findViewById(R.id.text_zone);
                holder.Number = (TextView) convertView.findViewById(R.id.text_number);
                holder.amount = (TextView) convertView.findViewById(R.id.am);

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.header.setText(String.valueOf("Name: "+name.get(position)));
            holder.zone.setText(String.valueOf("Zone: "+zone.get(position)));
            holder.id.setText(String.valueOf("ID: "+id.get(position)));
            holder.Number.setText(String.valueOf("No: " + position));
            holder.amount.setText(String.valueOf("Amount: "+amount.get(position)));

            return convertView;
        }
    }
    private class ViewHolder {
        TextView header;
        TextView zone;
        TextView id;
        TextView Number;
        TextView amount;
    }


}
