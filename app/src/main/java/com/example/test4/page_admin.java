package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class page_admin extends AppCompatActivity {

    private SwipeMenuListView swipeListView;
    private CustomListView adapter;
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> level = new ArrayList<String>();
    private ArrayList<String> Number = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeListView = findViewById(R.id.swipeListView);

        //create data to listview
        setDataListView("napat","Admin","110001");
        setDataListView("petch","Manager","220001");
        setDataListView("kuy","Employee","330001");

        //create swipe menu listview
        setSwipeListView();

        adapter = new CustomListView(this);
        swipeListView.setAdapter(adapter);

    }

    private void setDataListView(String a,String l,String i) {
        name.add(a);
        level.add(l);
        id.add(i);
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
                dialog_description.setText(String.valueOf("You want delete this"+name.get(position)+"?"));

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
                holder.level = (TextView) convertView.findViewById(R.id.text_level);
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
