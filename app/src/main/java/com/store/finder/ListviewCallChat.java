package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ListviewCallChat extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    TextView Number, CurrentOwner, ratings;
    String LoggedMobile,LoggedState,LoggedDist,LoggedSType;
    Owner owner;
    Button makeCall, requestCall, sendMessage, sendMail,ButtonRate;
    DatabaseReference database, childRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_call_chat);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        LoggedSType = getIntent().getStringExtra("LoggedSType");

        ratings = findViewById(R.id.rates);
        Number = findViewById(R.id.edit_text_number);
        CurrentOwner = findViewById(R.id.CurrentShop);
        makeCall = findViewById(R.id.make_call);
        requestCall = findViewById(R.id.call_back);
        sendMessage = findViewById(R.id.txtMessage);
        sendMail = findViewById(R.id.mailUs);
        ButtonRate = findViewById(R.id.btnRate);
        final double[] Rated = {0};
        final int[] total_Rating = {0};

        database = FirebaseDatabase.getInstance().getReference().child("RegisteredShop");
        String[] shopDetails = LoggedMobile.split("[!]", 0);
        CurrentOwner.setText(shopDetails[0]);
        Number.setText(LoggedMobile);
        String mobile = Number.getText().toString();
        String mob = mobile.trim();
        childRef = database.child(LoggedState).child(LoggedDist).child(LoggedSType).child(mob);

        childRef.child("Rating").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String ratbars = dataSnapshot.getValue(String.class);
                String[] res = ratbars.split("[/]", 0);
                String A = res[0];
                String B = res[1];

                String[] tmp = A.split("[.]", 0);
                String C = tmp[0];
                int X = tmp[1].length();
                String D = tmp[1].substring(0, X);

                ratings.setText("Rating " + C + "." + D + "/5\n Total Rating - " + B);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        requestCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    //Toast.makeText(ListviewCallChat.this, "Under Development", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ListviewCallChat.this, RequestCallBack.class);
                    i.putExtra("OwnerMobile", Number.getText().toString());
                    i.putExtra("LoggedSType", LoggedSType);
                    i.putExtra("LoggedST", LoggedState);
                    i.putExtra("LoggedDST", LoggedDist);
                    i.putExtra("LoggedID", LoggedMobile);
                    startActivity(i);
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListviewCallChat.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

            }
        });

        ButtonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {

                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListviewCallChat.this);
                    alert.setTitle("Local Store Finder,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

                try{

                }catch(Exception e){

                }


                Intent i = new Intent(ListviewCallChat.this, CustRating.class);
                i.putExtra("OwnerMobile", LoggedMobile);
                i.putExtra("LoggedSType", LoggedSType);
                i.putExtra("LoggedST", LoggedState);
                i.putExtra("LoggedDST", LoggedDist);
                i.putExtra("LoggedID", Number.getText().toString());
                startActivity(i);
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListviewCallChat.this, "This feature will be coming soon....", Toast.LENGTH_SHORT).show();
                //i.putExtra("OwnerEmail", ownerEmail);
                //i.putExtra("Flag", "Owner_Customer");
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    //Toast.makeText(ListviewCallChat.this, "Under Development", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ListviewCallChat.this, SendTextMessage.class);
                    //i.putExtra("OwnerEmail", ownerEmail);
                    i.putExtra("OwnerMobile", Number.getText().toString());
                    i.putExtra("LoggedSType", LoggedSType);
                    i.putExtra("LoggedST", LoggedState);
                    i.putExtra("LoggedDST", LoggedDist);
                    i.putExtra("LoggedID", LoggedMobile);
                    startActivity(i);
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListviewCallChat.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(ListviewCallChat.this,ShopList.class);
        i.putExtra("LoggedSTYPE", LoggedSType);
        i.putExtra("LoggedST", LoggedState);
        i.putExtra("LoggedDST", LoggedDist);
        //  i.putExtra("LoggedID", LoggedMobile);
        startActivity(i);
    }

    private void makePhoneCall() {
        String number = Number.getText().toString();
        if (number.trim().length() > 0 && number.trim().length() < 14) {
            if (ContextCompat.checkSelfPermission(ListviewCallChat.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ListviewCallChat.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(ListviewCallChat.this, "Invalid Phone Number, Please Retry....", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }

    }
}