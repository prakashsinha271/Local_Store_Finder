package com.store.finder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class OwnerRegister extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    String LoggedMobile, LoggedState, LoggedDist, LoggedMsg, LoggedSType,Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);

        final String Flag = getIntent().getStringExtra("Flag");

       // Toast.makeText(OwnerRegister.this,Flag,Toast.LENGTH_SHORT).show();
        spinner = findViewById(R.id.spinCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.inpMobile);

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                    String number = editText.getText().toString().trim();

                    if (number.isEmpty() || number.length() < 10) {
                        editText.setError("Valid number is required");
                        editText.requestFocus();
                        return;
                    }

                    String phoneNumber = "+" + code + number;

                    if(Flag.equals("Register")) {
                        LoggedMobile = getIntent().getStringExtra("LoggedID");
                        LoggedState = getIntent().getStringExtra("LoggedST");
                        LoggedDist = getIntent().getStringExtra("LoggedDST");
                        LoggedMsg = getIntent().getStringExtra("LoggedMSG");
                        LoggedSType = getIntent().getStringExtra("LoggedSType");

                        Intent intent = new Intent(OwnerRegister.this, VerifyPhoneActivity.class);
                        intent.putExtra("phonenumber", phoneNumber);
                        Toast.makeText(OwnerRegister.this, Flag, Toast.LENGTH_SHORT).show();
                        intent.putExtra("Flag", Flag);
                        intent.putExtra("LoggedID", LoggedMobile);
                        intent.putExtra("LoggedST", LoggedState);
                        intent.putExtra("LoggedDST", LoggedDist);
                        intent.putExtra("LoggedSType", LoggedSType);
                        intent.putExtra("LoggedMSG", LoggedMsg);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(OwnerRegister.this, VerifyPhoneActivity.class);
                        intent.putExtra("phonenumber", phoneNumber);
                        Toast.makeText(OwnerRegister.this, Flag, Toast.LENGTH_SHORT).show();
                        intent.putExtra("Flag", Flag);
                        startActivity(intent);
                    }
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(OwnerRegister.this);
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

        Intent intent = new Intent(OwnerRegister.this,LoginActivity.class);
        startActivity(intent);
    }
}