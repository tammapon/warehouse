package com.example.test4;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class page_admin extends AppCompatActivity {
    private DatabaseReference myRef;
    private SwipeMenuListView swipeListView;
    private CustomListView adapter;
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> level = new ArrayList<String>();
    private ArrayList<String> Number = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    //public ArrayList<String> test = new ArrayList<String>();
    Button btn_showID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database

        Button refresh = (Button) findViewById(R.id.refresh4);
        btn_showID=(Button)findViewById(R.id.btnToaddZone);

        btn_showID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent add = new Intent(page_admin.this,page_showID.class);
                startActivity(add);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef = FirebaseDatabase.getInstance().getReference().child(page_login.username).child("user");
                Log.e("xxx",page_login.username);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> passname = new ArrayList<String>();
                        long count = dataSnapshot.getChildrenCount();
                        int passcount = (int)count;
                        for(DataSnapshot child:dataSnapshot.getChildren()){
                            Log.e("==x lng",Long.toString(count));
                            Log.e("==x child",child.toString());
                            passname.add(child.getKey().toString());
                        }

                        Log.e("==x pass",passname.toString());
                        Intent intent = getIntent();
                        finish();
                        intent.putExtra("count",count);
                        intent.putExtra("DBname",passname);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        ArrayList<String> mylist = (ArrayList<String>) getIntent().getSerializableExtra("DBname");

        if(mylist!=null) {
            Log.e("==x get", mylist.toString());
            Log.e("==x size", String.valueOf(mylist.size()));
            swipeListView = findViewById(R.id.swipeListView);

            int size = mylist.size();
            //create data to listview
//        setDataListView("Supachok","10001");
//        setDataListView("Napat","20001");
//        setDataListView("Cindolara","20001");
//        setDataListView("Ichiton","10001");
//        setDataListView("Tammapon","20001");
//       setDataListView("Supanat","10001");

//        test.add("2");
//        test.add("3");
//        test.add("4");
//        Log.e("==x ",test.toString());
//
            for (int i = 0; i < size; i++) {
                String a = mylist.get(i);
                setDataListView(a, "1");
            }


            //create swipe menu listview
            setSwipeListView();

            adapter = new CustomListView(this);
            swipeListView.setAdapter(adapter);
        }
    }

    private void setDataListView(String a,String i) {
        name.add(a);
        id.add(i);
        if(i.charAt(0)=='1'){
        level.add("admin");
        }
        else if(i.charAt(0)=='2'){
            level.add("employee");
        }
        else if(i.charAt(0)=='3'){
            level.add("manager");
        }
        else{
            level.add("manager");
            level.add("manager");
        }
    }

    private void setSwipeListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(page_admin.this);
                // set item background
                deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_bin);
                // set item title
                deleteItem.setTitle("Delete");
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
                final Dialog dialog = new Dialog(page_admin.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_data);
                TextView dialog_title = (TextView)dialog.findViewById(R.id.dialog_title);
                dialog_title.setText(String.valueOf("Delete List"));

                TextView dialog_description = (TextView)dialog.findViewById(R.id.dialog_description);
                dialog_description.setText(String.valueOf("You want delete "+name.get(position)+"?"));

                Button buttonCancel = (Button)dialog.findViewById(R.id.buttonCancel);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                Button buttonOK = (Button)dialog.findViewById(R.id.buttonOK);
                buttonOK.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        myRef = FirebaseDatabase.getInstance().getReference();
                        myRef.child("Lnwklui").child("user").child(name.get(position)).removeValue();
                        name.remove(position);
                        id.remove(position);
                        level.remove(position);

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
                convertView = mInflater.inflate(R.layout.custom_listview, null);
                holder = new ViewHolder();

                holder.header = (TextView) convertView.findViewById(R.id.text_header);
                holder.id = (TextView) convertView.findViewById(R.id.text_id);
                holder.level = (TextView) convertView.findViewById(R.id.text_zone);
                holder.Number = (TextView) convertView.findViewById(R.id.text_number);

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.header.setText(name.get(position));
            holder.level.setText(level.get(position));
            holder.id.setText(id.get(position));

            holder.Number.setText(String.valueOf("No. " + position));
            //holder.image.setImageResource(photo.get(position));

            return convertView;
        }
    }
    private class ViewHolder {
        TextView header;
        TextView level;
        TextView id;
        TextView Number;
    }


}
