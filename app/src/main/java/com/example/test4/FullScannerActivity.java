package com.example.test4;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.r0adkll.slidr.Slidr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class FullScannerActivity extends BaseScannerActivity implements MessageDialogFragment.MessageDialogListener,
        ZXingScannerView.ResultHandler, FormatSelectorDialogFragment.FormatSelectorDialogListener,
        CameraSelectorDialogFragment.CameraSelectorDialogListener {
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZXingScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }

        setContentView(R.layout.activity_simple_scanner);
        setupToolbar();

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        setupFormats();
        contentFrame.addView(mScannerView);
        Slidr.attach(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuItem menuItem;
//
//        if(mFlash) {
//            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on);
//        } else {
//            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off);
//        }
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//
//        if(mAutoFocus) {
//            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_on);
//        } else {
//            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_off);
//        }
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//        menuItem = menu.add(Menu.NONE, R.id.menu_formats, 0, R.string.formats);
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//        menuItem = menu.add(Menu.NONE, R.id.menu_camera_selector, 0, R.string.select_camera);
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.menu_flash:
//                mFlash = !mFlash;
//                if(mFlash) {
//                    item.setTitle(R.string.flash_on);
//                } else {
//                    item.setTitle(R.string.flash_off);
//                }
//                mScannerView.setFlash(mFlash);
//                return true;
//            case R.id.menu_auto_focus:
//                mAutoFocus = !mAutoFocus;
//                if(mAutoFocus) {
//                    item.setTitle(R.string.auto_focus_on);
//                } else {
//                    item.setTitle(R.string.auto_focus_off);
//                }
//                mScannerView.setAutoFocus(mAutoFocus);
//                return true;
//            case R.id.menu_formats:
//                DialogFragment fragment = FormatSelectorDialogFragment.newInstance(this, mSelectedIndices);
//                fragment.show(getSupportFragmentManager(), "format_selector");
//                return true;
//            case R.id.menu_camera_selector:
//                mScannerView.stopCamera();
//                DialogFragment cFragment = CameraSelectorDialogFragment.newInstance(this, mCameraId);
//                cFragment.show(getSupportFragmentManager(), "camera_selector");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    public DatabaseReference myRef;
    String Str;
    String raka;
    @Override
    public void handleResult(final Result rawResult) {
        //myRef = FirebaseDatabase.getInstance().getReference();

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {}
        Log.e("xxx","scannnnnnn");
        /*myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("xxx","scannnnnnn222");
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String bufname = childSnapshot.getKey();
                    Log.e("xxx",bufname);
                    //String bufcode = dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("CODE").getValue().toString();
                    //Log.e("xxx",bufcode);
                    /*if (bufcode.equals(rawResult.getText())){
                        //myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("price").setValue(dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("price").getValue().toString());
                        myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("CODE").setValue(dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("CODE").getValue().toString());
                        myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("amount").setValue(dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("amount").getValue().toString());
                        myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("name").setValue(dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("name").getValue().toString());
                    }*...
                    //Log.e("xxx",childSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        String[] arrOfStr = rawResult.getText().split(",");
        Str = arrOfStr[0];
        raka = arrOfStr[1];
        String[] arrOfStr2 = arrOfStr[0].split(":");
        showMessageDialog("Name : " + arrOfStr2[1]+" \nDate In : " + arrOfStr2[0]+" \nKey : " + arrOfStr2[2]);
    }

    public void showMessageDialog(String message) {
        DialogFragment fragment = MessageDialogFragment.newInstance("Import Product", message, this);
        fragment.show(getSupportFragmentManager(), "scan_results");
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("xxx","scannnnnnn222");
                for (DataSnapshot childSnapshot: dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").getChildren()) {
                    String bufname = childSnapshot.getKey();
                    Log.e("xxx",bufname);
                    String bufcode = dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("CODE").getValue().toString();
                    Log.e("xxx",bufcode);
                    if (bufcode.equals(Str)){
                        //myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("price").setValue(dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("price").getValue().toString());
                        myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("ID").setValue(dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("CODE").getValue().toString());
                        myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("amount").setValue(dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("amount").getValue().toString());
                        myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("name").setValue(dataSnapshot.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).child("name").getValue().toString());
                        myRef.child(page_login.username).child("warehouse").child("A").child("item").child(bufname).child("price").setValue(raka);
                        myRef.child(page_login.username).child("warehouse").child("A").child("bufferitem").child(bufname).removeValue();
                    }
                    else {

                    }
                    //Log.e("xxx",childSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Resume the camera
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onFormatsSaved(ArrayList<Integer> selectedIndices) {
        mSelectedIndices = selectedIndices;
        setupFormats();
    }

    @Override
    public void onCameraSelected(int cameraId) {
        mCameraId = cameraId;
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        closeMessageDialog();
        closeFormatsDialog();
    }
}
