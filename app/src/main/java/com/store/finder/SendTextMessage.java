package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendTextMessage extends AppCompatActivity {

    private static final int REQUEST_MSG = 1;
    EditText mobileno,message;
    Button sendsms;
    String OwnerMobile = null;
    String LoggedMobile,LoggedState,LoggedDist,LoggedSType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text_message);

        mobileno= findViewById(R.id.editText1);
        message= findViewById(R.id.editText2);
        sendsms= findViewById(R.id.button1);
        OwnerMobile = getIntent().getStringExtra("OwnerMobile");
        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        LoggedSType = getIntent().getStringExtra("LoggedSType");

        mobileno.setText(OwnerMobile);

        //Performing action on button click
        sendsms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    SendMessage();
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(SendTextMessage.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_MSG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SendMessage();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(SendTextMessage.this, ListviewCallChat.class);
        i.putExtra("OwnerMobile", OwnerMobile);
        i.putExtra("LoggedSType", LoggedSType);
        i.putExtra("LoggedST", LoggedState);
        i.putExtra("LoggedDST", LoggedDist);
        i.putExtra("LoggedID", LoggedMobile);
        startActivity(i);
    }

    private void SendMessage() {

        String no=mobileno.getText().toString();
        String msg=message.getText().toString();

        try {

            if (ContextCompat.checkSelfPermission(SendTextMessage.this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SendTextMessage.this,
                        new String[]{Manifest.permission.SEND_SMS}, REQUEST_MSG);
            } else {
                //Getting intent and PendingIntent instance
                Intent intent = new Intent(getApplicationContext(), ListviewCallChat.class);
                //intent.putExtra("OwnerMobile", Number.getText().toString());
                intent.putExtra("LoggedSType", LoggedSType);
                intent.putExtra("LoggedST", LoggedState);
                intent.putExtra("LoggedDST", LoggedDist);
                intent.putExtra("LoggedID", LoggedMobile);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(no, null, msg, pi, null);

                Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e){
            message.setText(e.toString());
        }
    }
}