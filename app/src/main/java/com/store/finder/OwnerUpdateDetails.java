package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerUpdateDetails extends AppCompatActivity {

    EditText upOwnerName, upShopName, upPhone, upAddress, upinpState, upDistrict;
    Button log,upt,uptStdist;
    DatabaseReference database,childRef;
    private String Mobile;
    String LoggedMobile,LoggedState,LoggedDist, LoggedSType,LoggedMsg;
    TextView type,state,dist;
    // private Spinner upinpStates,upDistricts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_update_details);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        LoggedSType = getIntent().getStringExtra("LoggedSType");
        LoggedMsg = getIntent().getStringExtra("LoggedMSG");
        upOwnerName = findViewById(R.id.upOwnerName);
        upShopName = findViewById(R.id.upShopName);
        upPhone = findViewById(R.id.upPhone);
        upAddress = findViewById(R.id.upAddress);
        log = findViewById(R.id.btnLog);
        upt = findViewById(R.id.btnUpdate);
        uptStdist = findViewById(R.id.btnupt);
        type = findViewById(R.id.TxtType);
        state =  findViewById(R.id.TxtState);
        dist = findViewById(R.id.TxtDistrict);

        CheckInternetConnectivity();

        database = FirebaseDatabase.getInstance().getReference().child("RegisteredShop");

        childRef = database.child(LoggedState).child(LoggedDist).child(LoggedSType).child(LoggedMobile);

        try{
            getDetails();
        }catch(Exception e){
            upAddress.setText(e.toString());
            e.printStackTrace();
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });

        uptStdist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    if (!(TextUtils.isEmpty(LoggedMobile) || TextUtils.isEmpty(LoggedState) || TextUtils.isEmpty(LoggedDist) ||
                            TextUtils.isEmpty(LoggedSType) || TextUtils.isEmpty(LoggedMsg) || TextUtils.isEmpty(upOwnerName.getText().toString()) ||
                            TextUtils.isEmpty(upShopName.getText().toString()) || TextUtils.isEmpty(upPhone.getText().toString()) || TextUtils.isEmpty(upAddress.getText().toString()))) {
                        Intent intent = new Intent(OwnerUpdateDetails.this, OwnerStateDistrictUpdate.class);
                        intent.putExtra("LoggedID", LoggedMobile);
                        intent.putExtra("LoggedST", LoggedState);
                        intent.putExtra("LoggedDST", LoggedDist);
                        intent.putExtra("LoggedSType", LoggedSType);
                        intent.putExtra("LoggedMSG", LoggedMsg);
                        intent.putExtra("LoggedOwnName", upOwnerName.getText().toString().trim());
                        intent.putExtra("LoggedShpName", upShopName.getText().toString().trim());
                        intent.putExtra("LoggedPhone", upPhone.getText().toString().trim());
                        intent.putExtra("LoggedAddress", upAddress.getText().toString().trim());
                        startActivity(intent);
                    } else {
                        Toast.makeText(OwnerUpdateDetails.this, "Required field must not be empty !!", Toast.LENGTH_LONG).show();
                    }
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(OwnerUpdateDetails.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

            }
        });

        upt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if (null != activenetwork) {
                    String upoName = null;
                    String upsname = null;
                    String upsphone = null;
                    String upsadd = null;
                    try {
                        if (upOwnerName != null) {
                            if (upShopName != null) {
                                if (upPhone != null) {
                                    if (upAddress != null) {
                                        upoName = upOwnerName.getText().toString();
                                        upsname = upShopName.getText().toString();
                                        upsphone = upPhone.getText().toString();
                                        upsadd = upAddress.getText().toString();

                                        childRef.child("Owner_Name").setValue(upoName);
                                        childRef.child("Shop_Name").setValue(upsname);
                                        childRef.child("Phone_Number").setValue(upsphone);
                                        childRef.child("Address").setValue(upsadd);

                                        Intent intent = new Intent(OwnerUpdateDetails.this, OwnerWelcome.class);
                                        intent.putExtra("LoggedID", LoggedMobile);
                                        intent.putExtra("LoggedST", LoggedState);
                                        intent.putExtra("LoggedDST", LoggedDist);
                                        intent.putExtra("LoggedSType", LoggedSType);
                                        intent.putExtra("LoggedMSG", LoggedMsg);
                                        startActivity(intent);


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Address can not be null", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Mobile Number can not be null", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Shop Name can not be null", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Owner Name can not be null", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(OwnerUpdateDetails.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok", null);
                    alert.show();
                }
            }
        });
    }

    private void CheckInternetConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager.getActiveNetworkInfo();
        if(null != activenetwork) {

        } else   {
            AlertDialog.Builder alert = new AlertDialog.Builder(OwnerUpdateDetails.this);
            alert.setTitle("Hii,");
            alert.setMessage("Please Enable Network !!");
            alert.setPositiveButton("Ok",null);
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(OwnerUpdateDetails.this, OwnerWelcome.class);
        intent.putExtra("LoggedID", LoggedMobile);
        intent.putExtra("LoggedST", LoggedState);
        intent.putExtra("LoggedDST", LoggedDist);
        intent.putExtra("LoggedSType", LoggedSType);
        intent.putExtra("LoggedMSG", LoggedMsg);
        startActivity(intent);
    }

    private void getDetails() {

        childRef.child("Owner_Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Oname = dataSnapshot.getValue(String.class);
                if(Oname!= null){
                    upOwnerName.setText(Oname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childRef.child("Shop_Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Sname = dataSnapshot.getValue(String.class);
                if(Sname!= null){
                    upShopName.setText(Sname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childRef.child("Phone_Number").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Sphone = dataSnapshot.getValue(String.class);
                if(Sphone!= null){
                    upPhone.setText(Sphone);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childRef.child("Address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Sadd = dataSnapshot.getValue(String.class);
                if(Sadd!= null){
                    upAddress.setText(Sadd);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childRef.child("Shop_Type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Stype = dataSnapshot.getValue(String.class);
                if(Stype!= null){
                    type.setText(Stype);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childRef.child("State").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Sstate = dataSnapshot.getValue(String.class);
                if(Sstate!= null){
                    state.setText(Sstate);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childRef.child("District").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Sdist = dataSnapshot.getValue(String.class);
                if(Sdist!= null){
                    dist.setText(Sdist);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void SignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(OwnerUpdateDetails.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


}