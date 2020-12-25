package com.store.finder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OwnerWelcome extends AppCompatActivity {

    Button btnMyDetails, btnUpdateDetails, btnChangePass, btnFeedback, btnHelp, btnLogout, btnNumber, btnAd;
    TextView tvMsg;
    String LoggedMobile, LoggedState, LoggedDist, LoggedMsg, LoggedSType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_welcome);

        tvMsg = findViewById(R.id.tvMSG);
        btnMyDetails = findViewById(R.id.btnMyRecord);
        btnUpdateDetails = findViewById(R.id.btnEdit);
        btnChangePass = findViewById(R.id.btnResetPass);
        btnFeedback = findViewById(R.id.btnFeedback);
        btnHelp = findViewById(R.id.btnDelete);
        btnLogout = findViewById(R.id.btnLogOut);
        btnAd = findViewById(R.id.btnAdvertisement);
        btnNumber = findViewById(R.id.btnNumber);
        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        LoggedMsg = getIntent().getStringExtra("LoggedMSG");
        LoggedSType = getIntent().getStringExtra("LoggedSType");

        CheckInternetConnectivity();
        if(!LoggedMsg.equals("Valid Account")){
            tvMsg.setText(LoggedMsg);
        }

        btnMyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    Intent i = new Intent(OwnerWelcome.this, OwnerHome.class);
                    i.putExtra("LoggedID", LoggedMobile);
                    i.putExtra("LoggedST", LoggedState);
                    i.putExtra("LoggedDST", LoggedDist);
                    i.putExtra("LoggedSType", LoggedSType);
                    i.putExtra("LoggedMSG", LoggedMsg);
                    startActivity(i);
                } else   {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(OwnerWelcome.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

            }
        });

        btnUpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    Intent i = new Intent(OwnerWelcome.this, OwnerUpdateDetails.class);
                    i.putExtra("LoggedID", LoggedMobile);
                    i.putExtra("LoggedST", LoggedState);
                    i.putExtra("LoggedDST", LoggedDist);
                    i.putExtra("LoggedSType", LoggedSType);
                    i.putExtra("LoggedMSG", LoggedMsg);
                    startActivity(i);
                } else   {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(OwnerWelcome.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    Intent intent = new Intent(OwnerWelcome.this, VerifyOTPRecover.class);
                    intent.putExtra("phonenumber", LoggedMobile);
                    intent.putExtra("LoggedID", LoggedMobile);
                    intent.putExtra("LoggedST", LoggedState);
                    intent.putExtra("LoggedDST", LoggedDist);
                    intent.putExtra("LoggedSType", LoggedSType);
                    intent.putExtra("LoggedMSG", LoggedMsg);
                    startActivity(intent);
                } else   {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(OwnerWelcome.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(OwnerWelcome.this, "Under Construction", Toast.LENGTH_LONG).show();

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    Intent i = new Intent(OwnerWelcome.this, Feedback.class);
                    i.putExtra("LoggedID", LoggedMobile);
                    i.putExtra("LoggedST", LoggedState);
                    i.putExtra("LoggedDST", LoggedDist);
                    i.putExtra("LoggedSType", LoggedSType);
                    i.putExtra("LoggedMSG", LoggedMsg);
                    i.putExtra("Flag", "Owner_Developer");
                    startActivity(i);
                } else   {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(OwnerWelcome.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(OwnerWelcome.this);
                    alert.setTitle("Local Store Finder,");
                    alert.setMessage("Under Development");
                    alert.setPositiveButton("Ok",null);
                    alert.show();

            }
        });

        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OwnerWelcome.this);
                    builder.setTitle("Delete your Account ??");
                    builder.setMessage("Press YES to  delete your account or Click CANCEL Button to cancel it !!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(OwnerWelcome.this, DeleteRegisteredOwner.class);
                            intent.putExtra("LoggedID", LoggedMobile);
                            intent.putExtra("LoggedST", LoggedState);
                            intent.putExtra("LoggedDST", LoggedDist);
                            intent.putExtra("LoggedSType", LoggedSType);
                            intent.putExtra("LoggedMSG", LoggedMsg);
                            startActivity(intent);
                        }
                    });

                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(OwnerWelcome.this,"Your Account Is Safe !!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else   {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(OwnerWelcome.this);
                    alert.setTitle("Local Store Finder,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
                Intent i = new Intent(OwnerWelcome.this, OwnerRegister.class);
                i.putExtra("LoggedID", LoggedMobile);
                i.putExtra("LoggedST", LoggedState);
                i.putExtra("LoggedDST", LoggedDist);
                i.putExtra("LoggedSType", LoggedSType);
                i.putExtra("LoggedMSG", LoggedMsg);
                i.putExtra("Flag", "Register");
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    Intent i = new Intent(OwnerWelcome.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else   {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(OwnerWelcome.this);
                    alert.setTitle("Local Store Finder,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
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
            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(OwnerWelcome.this);
            alert.setTitle("Local Store Finder,");
            alert.setMessage("Please Enable Network !!");
            alert.setPositiveButton("Ok",null);
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(OwnerWelcome.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}