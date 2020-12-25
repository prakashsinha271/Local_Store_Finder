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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    EditText IndianMob, inputPass;
    Button btnLogin, btnHome, btnRegister;
    TextView tvRecoverPass;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IndianMob = findViewById(R.id.logID);
        inputPass = findViewById(R.id.logPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnHome = findViewById(R.id.btnHomes);
        btnRegister = findViewById(R.id.btnReg);
        tvRecoverPass = findViewById(R.id.tvRecover);

        database = FirebaseDatabase.getInstance().getReference().child("LoginDetails");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    String inID = null;
                    String inPass = null;
                    try {
                        inID = "+91" + IndianMob.getText().toString();
                        inPass = inputPass.getText().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try{
                        logVerify(inID, inPass);
                    }catch (Exception e){
                        btnRegister.setText(e.toString());
                        e.printStackTrace();
                    }
                }
                else {
                    //Toast.makeText(LoginActivity.this, "Please Enabled Mobile Data !!", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    Intent i = new Intent(LoginActivity.this, OwnerRegister.class);
                    i.putExtra("Flag", "NewUser");
                    startActivity(i);
                }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
            }
        });

        tvRecoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RecoverPasswordVerify.class);
                startActivity(i);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    private void logVerify(final String inIDNo, final String inPassword) {

        database.child(inIDNo).child("Mobile Number").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbMobile = dataSnapshot.getValue(String.class);

                if(dbMobile != null){
                    if (dbMobile.equals(inIDNo)) {
                        //Mobile Number Matched, Now check for Password
                        database.child(inIDNo).child("Password").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String dbPassword = dataSnapshot.getValue(String.class);

                                if(dbPassword != null){
                                    if (dbPassword.equals(inPassword)) {
                                        //Password Matched, Now check for Account Status

                                        AccountStatus(dbMobile);

                                        //Toast.makeText(LoginActivity.this, "Login ID from Database  - " + dbMobile + ", Password - " + dbPassword, Toast.LENGTH_LONG).show();

                                    }else{
                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();

                                    }
                                }else{
                                    Toast.makeText(LoginActivity.this, "Password not matched", Toast.LENGTH_LONG).show();

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else{
                        Toast.makeText(LoginActivity.this, "Registration ID not found", Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Incorrect registration ID", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void AccountStatus(final String dbMob) {

        database.child(dbMob).child("AccType").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbACStatus = dataSnapshot.getValue(String.class);

                if(dbACStatus != null){
                    //Toast.makeText(LoginActivity.this, "Account status id - " + dbACStatus, Toast.LENGTH_LONG).show();
                    try{
                        final String AccStatus = dbACStatus.substring(0,5);
                        final String AccMonth = dbACStatus.substring(5,7);
                        final String AccYear = dbACStatus.substring(7,11);

                        Calendar now = Calendar.getInstance();
                        final int thisMonth = now.get(Calendar.MONTH) + 1;
                        final int thisYear = now.get(Calendar.YEAR);

                        final int regYEAR = Integer.parseInt(AccYear);
                        final int regMONTH = Integer.parseInt(AccMonth);

                        database.child(dbMob).child("State").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String dbState = dataSnapshot.getValue(String.class);

                                if(dbState != null){

                                    database.child(dbMob).child("District").addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            final String dbDistrict = dataSnapshot.getValue(String.class);

                                            if(dbDistrict != null){

                                                database.child(dbMob).child("ShopType").addListenerForSingleValueEvent(new ValueEventListener() {

                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        final String dbSType = dataSnapshot.getValue(String.class);

                                                        if(dbState != null){
                                                            if(AccStatus.equals("PAYED")){
                                                                //Payed Account, now check for valid up to
                                                                if(regYEAR > thisYear){
                                                                    //Have more than one year to get expired, no need to check month, go into owner home page without notification messages.....
                                                                    OwnerHome(dbMob, dbState, dbDistrict, dbSType);

                                                                }else if(regYEAR == thisYear){
                                                                    //Check for month....
                                                                    if(regMONTH > thisMonth){
                                                                        //Have couple of months, go into owner home page without notification......
                                                                        OwnerHome(dbMob, dbState, dbDistrict, dbSType);
                                                                    }else if(regMONTH == thisMonth){
                                                                        //Payed period will expire after the current month, remind for payment....
                                                                        OwnerHomeMsg1(dbMob, dbState, dbDistrict, dbSType, regMONTH, regYEAR);
                                                                    }else{
                                                                        //Payed Period is expired, ask for payment, or confirm and delete account...
                                                                        UpdateAccType(dbMob);
                                                                    }
                                                                }else{
                                                                    //Payed Period is expired, ask for payment, or confirm and delete account
                                                                    UpdateAccType(dbMob);
                                                                }
                                                            }else if(AccStatus.equals("TRAIL")){
                                                                //Trail Account, now check for valid up to
                                                                if(regYEAR > thisYear){
                                                                    //Have more than one year to get expired, no need to check month, go into owner home page without notification messages.....
                                                                    OwnerHome(dbMob, dbState, dbDistrict, dbSType);

                                                                }else if(regYEAR == thisYear){
                                                                    //Check for month....
                                                                    if(regMONTH > thisMonth){
                                                                        //Have couple of months, go into owner home page without notification......
                                                                        OwnerHome(dbMob, dbState, dbDistrict, dbSType);
                                                                    }else if(regMONTH == thisMonth){
                                                                        //Payed period will expire after the current month, remind for payment....
                                                                        OwnerHomeMsg1(dbMob, dbState, dbDistrict, dbSType, regMONTH, regYEAR);
                                                                    }else{
                                                                        //Trail Period is expired, ask for payment, or confirm and delete account
                                                                        UpdateAccType(dbMob);
                                                                    }
                                                                }else{
                                                                    //Payed Period is expired, ask for payment, or confirm and delete account
                                                                    UpdateAccType(dbMob);
                                                                }
                                                            }else if(AccStatus.equals("ADMIN")){
                                                                Intent i = new Intent(LoginActivity.this, AdminAddShopType.class);
                                                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(i);
                                                            }else{
                                                                //Account Expired, confirm and delete Registration details, and Skip login details for prevent reregistration, without payment
                                                                AccPlan(dbMob);
                                                            }
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "Error - " + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Account Status Not Found", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void OwnerHome(final String dbMo, final String dbST, final String dbDST, final String dbSType){
        Intent i = new Intent(LoginActivity.this, OwnerWelcome.class);
        i.putExtra("LoggedID", dbMo);
        i.putExtra("LoggedST", dbST);
        i.putExtra("LoggedDST", dbDST);
        i.putExtra("LoggedSType", dbSType);
        i.putExtra("LoggedMSG", "Valid Account");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void OwnerHomeMsg1(final String dbMo, final String dbST, final String dbDST, final String dbSType, final int regM, final int regY) {
        Intent i = new Intent(LoginActivity.this, OwnerWelcome.class);
        i.putExtra("LoggedID", dbMo);
        i.putExtra("LoggedST", dbST);
        i.putExtra("LoggedDST", dbDST);
        i.putExtra("LoggedSType", dbSType);
        i.putExtra("LoggedMSG", "Your Plan will expire on " + regM + "/" + regY + " Make a payment to avoid discontinued");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void AccPlan(final String  dbMo){
        Intent i = new Intent(LoginActivity.this, SelectAccountPlan.class);
        i.putExtra("LoggedID", dbMo);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void UpdateAccType(final String dbMo){
        database.child(dbMo).child("AccType").setValue("Expir000000");
        AccPlan(dbMo);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}