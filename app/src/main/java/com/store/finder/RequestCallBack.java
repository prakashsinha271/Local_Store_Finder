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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RequestCallBack extends AppCompatActivity {

    private static final int REQUEST_MSG = 1;
    EditText CustName,CustMobile, txtQuotationBill;
    Button Next;
    String OwnerMobile = null;
    String LoggedMobile,LoggedState,LoggedDist,LoggedSType;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_call_back);

        CustMobile= findViewById(R.id.txtCustomerMobile);
        CustName= findViewById(R.id.txtCustomerName);
        Next = findViewById(R.id.buttonRequest);
        txtQuotationBill = findViewById(R.id.txtQuotationBill);
        OwnerMobile = getIntent().getStringExtra("OwnerMobile");
        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        LoggedSType = getIntent().getStringExtra("LoggedSType");
        radioGroup = findViewById(R.id.groupRadioCustomer);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        RadioButton radioButton = group.findViewById(checkedId);
                    }
                });

        //mobileno.setText(OwnerMobile);

        Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if (selectedId == -1) {
                        Toast.makeText(RequestCallBack.this,"Please Select Your Service Type", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        RadioButton radioButton = radioGroup.findViewById(selectedId);
                        //Toast.makeText(SelectAccountPlan.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                        continueNext(radioButton.getText());
                    }
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RequestCallBack.this);
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

        Intent i = new Intent(RequestCallBack.this, ListviewCallChat.class);
        i.putExtra("OwnerMobile", OwnerMobile);
        i.putExtra("LoggedSType", LoggedSType);
        i.putExtra("LoggedST", LoggedState);
        i.putExtra("LoggedDST", LoggedDist);
        i.putExtra("LoggedID", LoggedMobile);
        startActivity(i);
        //super.onBackPressed();
    }

    private void continueNext(CharSequence selectedId) {

        String BillQuotation = txtQuotationBill.getText().toString().trim();
        String CutomerDetails;

        if (ContextCompat.checkSelfPermission(RequestCallBack.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RequestCallBack.this,
                    new String[]{Manifest.permission.SEND_SMS}, REQUEST_MSG);
        } else {

            if (!TextUtils.isEmpty(BillQuotation)) {
                CutomerDetails = CustName.getText().toString().trim() + " ( Mobile Number - " + CustMobile.getText().toString().trim() + " ) Bill/Quotation - " + txtQuotationBill.getText().toString().trim();
            } else {
                CutomerDetails = CustName.getText().toString().trim() + " ( Mobile Number - " + CustMobile.getText().toString().trim() + " )";
            }

            if( !(TextUtils.isEmpty(CustName.getText().toString())) && !(TextUtils.isEmpty(CustMobile.getText().toString())) ) {

                if (selectedId.equals("Product Information/Quotations")) {
                    //Toast.makeText(SelectAccountPlan.this, "You have to pay 30Rs.", Toast.LENGTH_SHORT).show();
                    String msg = CutomerDetails + " wants to communicate you for Product Information/Quotations. Please call back. By Local Store Finder";
                    sendSMS(msg);
                } else if (selectedId.equals("Home Delivery of Product")) {
                    String msg = CutomerDetails + " wants to communicate you for Home Delivery of Product. Please call back. By Local Store Finder";
                    sendSMS(msg);
                } else if (selectedId.equals("Pickup the Order")) {
                    String msg = CutomerDetails + " wants to communicate you for Pickup the Order Please. call back. By Local Store Finder";
                    sendSMS(msg);
                } else if (selectedId.equals("Return/Replace Product")) {
                    String msg = CutomerDetails + " wants to communicate you for Return/Replace Product. Please call back. By Local Store Finder";
                    sendSMS(msg);
                } else if (selectedId.equals("Cancel Order")) {
                    String msg = CutomerDetails + " wants to communicate you for Cancel Order Please. call back. By Local Store Finder";
                    sendSMS(msg);
                }
            } else {
                Toast.makeText(RequestCallBack.this,"Required field should not be empty !!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void sendSMS(String getmsg){
        String no = OwnerMobile;
        String msg = getmsg;

        try{

                 /*Toast.makeText(RequestCallBack.this, "Number " + no + " message " + msg, Toast.LENGTH_LONG).show();
            CustName.setText(msg);*/
            //Getting intent and PendingIntent instance
            Intent intent=new Intent(getApplicationContext(),ListviewCallChat.class);
            intent.putExtra("LoggedSType", LoggedSType);
            intent.putExtra("LoggedST", LoggedState);
            intent.putExtra("LoggedDST", LoggedDist);
            intent.putExtra("LoggedID", LoggedMobile);
            PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

            //Get the SmsManager instance and call the sendTextMessage method to send message
            SmsManager sms= SmsManager.getDefault();
            // sms.sendTextMessage(no, null, msg, pi,null);
            sms.sendTextMessage(no, null, msg, pi,null);

            Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_MSG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                continueNext(radioButton.getText());
                // makePhoneCall();
                //sendSMS(String getmsg);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }

    }
}