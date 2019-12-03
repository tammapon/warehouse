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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class manager_product extends AppCompatActivity {

    private SwipeMenuListView swipeListView;
    private CustomListView adapter;
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> zone = new ArrayList<String>();
    private ArrayList<String> DateIN = new ArrayList<String>();
    private ArrayList<String> Number = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<Integer> amount = new ArrayList<>();
    private ArrayList<Integer> value = new ArrayList<>();
    private ArrayList<String> data_check = new ArrayList<String>();
    private String check_data = "620106";
    private int ValueOfWarehouse = 5000000;
    Dialog myDialog;

    Button btn_tozone,btn_toaddproduct,btn_show;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_product);
        myDialog = new Dialog(this);

       btn_toaddproduct=(Button)findViewById(R.id.btnToaddZone);
       btn_tozone=(Button)findViewById(R.id.btnToZone);
       btn_show=(Button)findViewById(R.id.money1);

       btn_show.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Dialog dialog = new Dialog(manager_product.this);
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
        Button refresh = (Button) findViewById(R.id.refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference().child("Lnwklui").child("warehouse").child("A").child("item");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> passNM = new ArrayList<String>();
                        ArrayList<String> passID = new ArrayList<String>();
                        ArrayList<Integer> passAM = new ArrayList<Integer>();
                        for(DataSnapshot child:dataSnapshot.getChildren()){
                            passNM.add(child.child("name").getValue().toString());
                            passID.add(child.child("ID").getValue().toString());
                            passAM.add(Integer.parseInt(child.child("amount").getValue().toString()));
                        }
//                        Log.e("==x n",passNM.toString());
//                        Log.e("==x i",passID.toString());
//                        Log.e("==x a",passAM.toString());

                        Intent intent = getIntent();
                        finish();
                        intent.putExtra("passNM",passNM);
                        intent.putExtra("passAM",passAM);
                        intent.putExtra("passID",passID);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        //create data to listview
        ArrayList<String> DBNM = (ArrayList<String>)getIntent().getSerializableExtra("passNM");
        ArrayList<Integer> DBAM = (ArrayList<Integer>)getIntent().getSerializableExtra("passAM");
        ArrayList<String> DBID = (ArrayList<String>)getIntent().getSerializableExtra("passID");
        if(DBNM != null){
//            setDataListView("0200","cookie","A",20);
//            setDataListView("0400","oreo","A",20);
//            setDataListView("0600","pizza","A",20);
            long c = DBNM.size();
            for(int a=0;a< c;a++){
                String id = DBID.get(a);
                String name = DBNM.get(a);
                int amount = DBAM.get(a);
                String date = "620105";
                int Value = 499;
                setDataListView(id,name,"A",amount,date,Value);
            }
        }




        setSwipeListView();

        adapter = new CustomListView(this);
        swipeListView.setAdapter(adapter);

    }

    private void setDataListView(String ID,String NAME,String ZONE,int AMOUNT,String date,int Value) {
        id.add(ID);
        name.add(NAME);
        zone.add(ZONE);
        amount.add(AMOUNT);
        DateIN.add(date);
        value.add(Value);
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
                Button buttonOK = (Button)dialog.findViewById(R.id.ma_OK);
                TextView dialog_title = (TextView)dialog.findViewById(R.id.dialog_title);
                Button buttonCancel = (Button)dialog.findViewById(R.id.buttonCancel);
                TextView dialog_description = (TextView)dialog.findViewById(R.id.dialog_description);

//check firstin - firstout
                if(DateIN.get(position).equals(check_data)){
                    dialog_title.setText(String.valueOf("Export Prouct"));
                    dialog_description.setText(String.valueOf("You want export this "+name.get(position)+"?"));
                }
                else{
                    dialog_title.setText(String.valueOf("!!!This product should not be exported"));
                    dialog_description.setText(String.valueOf("You want export this "+name.get(position)+"?"));
                }


                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });


                buttonOK.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        myRef = FirebaseDatabase.getInstance().getReference().child("Lnwklui").child("warehouse").child("A");
                        myRef.child("item").child(name.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String ID = dataSnapshot.child("ID").getValue().toString();
                                String name = dataSnapshot.child("name").getValue().toString();
                                String amount = dataSnapshot.child("amount").getValue().toString();

                                DatabaseReference newMyRef;
                                newMyRef = FirebaseDatabase.getInstance().getReference();
                                newMyRef = newMyRef.child("Lnwklui").child("warehouse").child("A").child("bufferitem");
                                newMyRef.child(name).child("ID").setValue(ID);
                                newMyRef.child(name).child("name").setValue(name);
                                newMyRef.child(name).child("amount").setValue(amount);
                                newMyRef.child(name).child("status").setValue("Export");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        myRef.child("item").child(name.get(position)).removeValue();
                        name.remove(position);
                        id.remove(position);
                        zone.remove(position);
                        DateIN.remove(position);

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
            //holder.amount.setText(String.valueOf("Amount: "+amount.get(position)));

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
