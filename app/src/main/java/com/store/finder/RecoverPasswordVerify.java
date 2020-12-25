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

public class RecoverPasswordVerify extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    String LoggedMobile,LoggedState,LoggedDist,LoggedMsg,LoggedSType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password_verify);

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

                    Intent intent = new Intent(RecoverPasswordVerify.this, VerifyOTPRecover.class);
                    intent.putExtra("phonenumber", phoneNumber);
                    startActivity(intent);

                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RecoverPasswordVerify.this);
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
        Intent intent = new Intent(RecoverPasswordVerify.this, LoginActivity.class);
        startActivity(intent);
    }
}