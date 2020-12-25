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
import android.widget.Button;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CustRating extends AppCompatActivity {

    private RatingBar rBar,ratesbar;
    private TextView tView,tcust;
    private Button btn;
    DatabaseReference database, childRef;
    String LoggedMobile, LoggedState, LoggedDist, LoggedSType,Shopdetails;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_rating);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        Shopdetails = getIntent().getStringExtra("OwnerMobile");
        LoggedSType = getIntent().getStringExtra("LoggedSType");

        database = FirebaseDatabase.getInstance().getReference().child("RegisteredShop");
        childRef = database.child(LoggedState).child(LoggedDist).child(LoggedSType).child(LoggedMobile);

//        Toast.makeText(getApplicationContext(), LoggedMobile, Toast.LENGTH_LONG).show();

        rBar = findViewById(R.id.RatingBar);
        ratesbar = findViewById(R.id.ratingBar);
        tView = findViewById(R.id.textview1);
        tcust = findViewById(R.id.txtcustcount);
        btn = findViewById(R.id.btnGet);
        final double Rated[] = {0};
        final int total_Rating[] = {0};

        childRef.child("Rating").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String ratbars = dataSnapshot.getValue(String.class);
                //ratings.setText("Customer Rating for the Shop " + ratbars);
                String[] res = ratbars.split("[/]", 0);
                String A = res[0];
                String B = res[1];
                Rated[0] = Double.parseDouble(A);
                total_Rating[0] = Integer.parseInt(B);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();

                if(null != activenetwork) {

                    btn.setEnabled(false);

                    float getratings = rBar.getRating();

                    Double totalRate =  Rated[0];
                    int totalCustomer = total_Rating[0] + 1;
                    Double rateds = ((totalRate*total_Rating[0])+getratings)/(totalCustomer);
                    String Rating = rateds.toString().trim() + "/" + totalCustomer;
                    childRef.child("Rating").setValue(Rating);
                    back();

                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CustRating.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
            }
        });

    }

    private void back() {
        Intent intent = new Intent(CustRating.this,ListviewCallChat.class);
        intent.putExtra("LoggedSType", LoggedSType);
        intent.putExtra("LoggedST", LoggedState);
        intent.putExtra("LoggedDST", LoggedDist);
        intent.putExtra("LoggedID", Shopdetails);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
       back();
    }
}
