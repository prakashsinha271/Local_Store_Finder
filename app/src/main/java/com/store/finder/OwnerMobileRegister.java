package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivateKey;

public class OwnerMobileRegister extends AppCompatActivity {

    EditText MobNo,txt;
    Button confirm;
    TextView txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10;
    private DatabaseReference databases,database,childref,childrefs;
    String LoggedMobile, LoggedState, LoggedDist, LoggedMsg, LoggedSType,Mobile;
    //String Mobile,Phone,State,District,Shop_Type,OName,OShop,OAddress,Account,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_mobile_register);

        MobNo = findViewById(R.id.MobNo);
        confirm = findViewById(R.id.btnConfirm);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        LoggedMsg = getIntent().getStringExtra("LoggedMSG");
        LoggedSType = getIntent().getStringExtra("LoggedSType");

        txt   = findViewById(R.id.txtacc);
        txt2  = findViewById(R.id.txt2);
        txt3  = findViewById(R.id.txt3);
        txt4  = findViewById(R.id.txt4);
        txt5  = findViewById(R.id.txt5);
        txt6  = findViewById(R.id.txt6);
        txt7  = findViewById(R.id.txt7);
        txt8  = findViewById(R.id.txt8);
        txt9  = findViewById(R.id.txt9);
        txt10 = findViewById(R.id.txt10);

        databases = FirebaseDatabase.getInstance().getReference().child("RegisteredShop");
        database = FirebaseDatabase.getInstance().getReference().child("LoginDetails");
        childref = database.child(LoggedMobile);
        childrefs = databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(LoggedMobile);

        Mobile = getIntent().getStringExtra("phonenumber");
        CheckInternetConnectivity();
        MobNo.setText(Mobile);

        childref.child("AccType").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String acc = dataSnapshot.getValue(String.class);
                if(acc!= null){
                    //State.equals(Oname);
                    txt.setText(acc);
                    //Account = txt.getText().toString();
                   // Toast.makeText(OwnerMobileRegister.this, Account,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childref.child("District").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dist = dataSnapshot.getValue(String.class);
                if(dist!= null){
                    //State.equals(Oname);
                    txt2.setText(dist);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childref.child("Owner_Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String Oname = dataSnapshot.getValue(String.class);
                if(Oname!= null){
                    //State.equals(Oname);
                    txt3.setText(Oname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childref.child("Password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String pass = dataSnapshot.getValue(String.class);
                if(pass!= null){
                    //State.equals(Oname);
                    txt4.setText(pass);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childref.child("ShopType").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String stype = dataSnapshot.getValue(String.class);
                if(stype!= null){
                    //State.equals(Oname);
                    txt5.setText(stype);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childref.child("State").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String St = dataSnapshot.getValue(String.class);
                if(St!= null){
                    //State.equals(Oname);
                    txt6.setText(St);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childrefs.child("Shop_Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String sname = dataSnapshot.getValue(String.class);
                if(sname!= null){
                    //State.equals(Oname);
                    txt7.setText(sname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        childrefs.child("Address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String add = dataSnapshot.getValue(String.class);
                if(add!= null){
                    //State.equals(Oname);
                    txt8.setText(add);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //String Mob = MobNo.getText().toString();
        //Toast.makeText(OwnerMobileRegister.this, txt.getText().toString(),Toast.LENGTH_SHORT).show();
        //database.child(Mob).child("Account").setValue(Account);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    String Acc = null;
                    String Dist = null;
                    String Oname = null;
                    String Passw = null;
                    String Stypes = null;
                    String Sts = null;
                    String Sname = null;
                    String Adds = null;

                    try {
                        assert Mobile != null;
                        txt9.setText(Mobile.substring(3));
                        if (txt != null) {
                            if (txt2 != null) {
                                if (txt3 != null) {
                                    if (txt4 != null) {
                                        if (txt5 != null) {
                                            if (txt6 != null) {
                                                if (txt7 != null) {
                                                    if (txt8 != null) {
                                                        String MOB = txt9.getText().toString();
                                                        String Mob = Mobile.trim();
                                                        Acc = txt.getText().toString();
                                                        Dist = txt2.getText().toString();
                                                        Oname = txt3.getText().toString();
                                                        Passw = txt4.getText().toString();
                                                        Stypes = txt5.getText().toString();
                                                        Sts = txt6.getText().toString();
                                                        Sname = txt7.getText().toString();
                                                        Adds = txt8.getText().toString();

                                                        database.child(Mob).child("AccType").setValue(Acc);
                                                        database.child(Mob).child("District").setValue(Dist);
                                                        database.child(Mob).child("Mobile Number").setValue(Mob);
                                                        database.child(Mob).child("Owner_Name").setValue(Oname);
                                                        database.child(Mob).child("Password").setValue(Passw);
                                                        database.child(Mob).child("State").setValue(Sts);
                                                        database.child(Mob).child("ShopType").setValue(Stypes);

                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("Owner_Name").setValue(Oname);
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("Shop_Name").setValue(Sname);
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("Shop_Type").setValue(Stypes);
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("Phone_Number").setValue(MOB);
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("Mobile_Number").setValue(Mob);
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("Password").setValue(Passw);
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("Address").setValue(Adds);
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("State").setValue(Sts);
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(Mob).child("District").setValue(Dist);

                                                        database.child(LoggedMobile).removeValue();
                                                        databases.child(LoggedState).child(LoggedDist).child(LoggedSType).child(LoggedMobile).removeValue();

                                                        Intent intent = new Intent(OwnerMobileRegister.this, OwnerWelcome.class);
                                                        intent.putExtra("LoggedID", LoggedMobile);
                                                        intent.putExtra("LoggedST", LoggedState);
                                                        intent.putExtra("LoggedDST", LoggedDist);
                                                        intent.putExtra("LoggedSType", LoggedSType);
                                                        intent.putExtra("LoggedMSG", LoggedMsg);
                                                        startActivity(intent);

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Network is slow !!", Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(OwnerMobileRegister.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

            }
        });
        /*

        String upoName = null;
                String upsname = null;
                String upsphone = null;
                String upsadd = null;
                try {
                    if(upOwnerName!=null) {
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
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Owner Name can not be null", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

        String Mob = MobNo.getText().toString();
        Toast.makeText(OwnerMobileRegister.this, Account,Toast.LENGTH_SHORT).show();
        database.child(Mob).child("Account").setValue(Account);

         */

        /*txt.setText(childref.getKey().toString());
        //String data = childref.getKey().replace(LoggedMobile,"98");
        //data.replace(LoggedMobile,"98");
        //txt.setText(childref.getKey().toString());

        //database.child(MobNo.toString()).child("State").setValue(txt6.getText().toString());
        String Mob = MobNo.getText().toString();
        String Account = txt.getText().toString().trim();
        Toast.makeText(OwnerMobileRegister.this, txt.getText().toString(),Toast.LENGTH_SHORT).show();
        database.child(Mob).child("Account").setValue(txt.getText().toString());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // database.child(MobNo.toString()).child("State").setValue(Account);
                //String data = childref.getKey().replace(LoggedMobile,"98");
                //txt.setText(childref.getKey().toString());
            }
        });

        //.child(LoggedDist).child(LoggedSType).child(LoggedMobile);
        //Toast.makeText(OwnerMobileRegister.this, childref.toString(),Toast.LENGTH_SHORT).show();
         */

    }

    private void CheckInternetConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager.getActiveNetworkInfo();
        if(null != activenetwork) {

        } else   {
            AlertDialog.Builder alert = new AlertDialog.Builder(OwnerMobileRegister.this);
            alert.setTitle("Hii,");
            alert.setMessage("Please Enable Network !!");
            alert.setPositiveButton("Ok",null);
            alert.show();
        }
    }
}