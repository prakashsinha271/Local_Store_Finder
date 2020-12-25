package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerHome extends AppCompatActivity {

    TextView tvOwner, tvShop, tvContact, tvShopType, tvAddress,tvst, tvdist, tvMSG;
    private DatabaseReference database;
    String LoggedMobile, LoggedState, LoggedDist, LoggedMsg, LoggedSType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        LoggedMsg = getIntent().getStringExtra("LoggedMSG");
        LoggedSType = getIntent().getStringExtra("LoggedSType");
        tvOwner = (TextView) findViewById(R.id.tvOwner);
        tvShop = (TextView) findViewById(R.id.tvShop);
        tvContact = (TextView) findViewById(R.id.tvContact);
        tvShopType = (TextView) findViewById(R.id.tvShopType);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvst = findViewById(R.id.tvState);
        tvdist = findViewById(R.id.tvDistrict);
        tvMSG = findViewById(R.id.tvMSG);
        database = FirebaseDatabase.getInstance().getReference().child("RegisteredShop").child(LoggedState).child(LoggedDist).child(LoggedSType).child(LoggedMobile);

        CheckInternetConnectivity();

        try{
            if(!LoggedMsg.equals("Valid Account")){
                tvMSG.setText(LoggedMsg);
                tvMSG.setVisibility(View.VISIBLE);
            }
            getDetails();
        }catch(Exception e){
            tvAddress.setText(e.toString());
            e.printStackTrace();
        }
    }

    private void CheckInternetConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager.getActiveNetworkInfo();
        if(null != activenetwork) {

        } else   {
            AlertDialog.Builder alert = new AlertDialog.Builder(OwnerHome.this);
            alert.setTitle("Local Store Finder,");
            alert.setMessage("Please Enable Network !!");
            alert.setPositiveButton("Ok",null);
            alert.show();
        }
    }

    private void getDetails() {
        database.child("Owner_Name").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String OName = dataSnapshot.getValue(String.class);
                //String tmp = dbname.concat("(" + LoggedMobile + ")");
                tvOwner.setText(String.format("Owner Name : %s", OName));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child("Shop_Name").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String SName = dataSnapshot.getValue(String.class);
                //String tmp = dbname.concat("(" + LoggedMobile + ")");
                tvShop.setText(String.format("Shop Name : %s", SName));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child("Phone_Number").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Pho = dataSnapshot.getValue(String.class);
                assert Pho != null;
                String Ph = "+91".concat(Pho);
                if(Ph.equals(LoggedMobile)){
                    tvContact.setText(String.format("Contact Number :" + LoggedMobile));
                }
                else{
                    String tmp = Ph.concat(", " + LoggedMobile);
                    tvContact.setText(String.format("Contact Number : %s", tmp));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child("Shop_Type").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Stype = dataSnapshot.getValue(String.class);
                tvShopType.setText(String.format("Shop Type : %s", Stype));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child("Address").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Address = dataSnapshot.getValue(String.class);
                //String tmp = Ph[0].concat(" " + "(" + Mobile + ")");
                tvAddress.setText(String.format("Address : %s", Address));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child("State").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String State = dataSnapshot.getValue(String.class);
                tvst.setText(String.format("Shop State : %s", State));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.child("District").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String District = dataSnapshot.getValue(String.class);
                tvdist.setText(String.format("Shop District : %s", District));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}