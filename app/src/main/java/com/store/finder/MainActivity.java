package com.store.finder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogin1, btnLogin2, btnShare, btnSuggestion;
    private long backTime;
    private Toast BackToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin1 = findViewById(R.id.btnCust);
        btnLogin2 = findViewById(R.id.btnSell);
        btnShare = findViewById(R.id.btnShare);
        btnSuggestion = findViewById(R.id.btnShopSugg);
        //checkInternetConnectivity();

            btnLogin1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                    if(null != activenetwork) {
                        Intent i = new Intent(MainActivity.this, CustomerSearch.class);
                        startActivity(i);
                    } else   {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setTitle("Hii,");
                        alert.setMessage("Please Enable Network !!");
                        alert.setPositiveButton("Ok",null);
                        alert.show();
                    }

                }
            });

            btnLogin2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkInternetConnectivity();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentInvite = new Intent(Intent.ACTION_SEND);
                    intentInvite.setType("text/plain");
                    String body = "https://drive.google.com/file/d/1Y5Zo32g_DFpDRhUCMxl_rqPU6-adQFOh/view?usp=sharing";   //Application Sharing Link will be here
                    String subject = "Local Store Finder";
                    intentInvite.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intentInvite.putExtra(Intent.EXTRA_TEXT, subject + "\n " + body);
                    startActivity(Intent.createChooser(intentInvite, "Share using"));
                }
            });

            btnSuggestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, HelpActivity.class);
                    startActivity(i);
                }
            });

    }

    @Override
    public void onBackPressed() {


        if(backTime + 2000 > System.currentTimeMillis())
        {
            BackToast.cancel();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            BackToast = Toast.makeText(MainActivity.this, "Press back again to exit !!", Toast.LENGTH_SHORT);
            BackToast.show();
        }
        backTime = System.currentTimeMillis();
    }
    public void checkInternetConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager.getActiveNetworkInfo();
        if(null != activenetwork) {

            if (activenetwork.getType() == ConnectivityManager.TYPE_WIFI) {
              //  Toast.makeText(this, "Wifi Enabled", Toast.LENGTH_SHORT).show();
            } else if (activenetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
             //   Toast.makeText(this, "Mobile Data Enabled", Toast.LENGTH_SHORT).show();
                //continue;
            }
        }
            else {
            Toast.makeText(this, "Please Enabled Network !!", Toast.LENGTH_SHORT).show();
        }
    }
}

