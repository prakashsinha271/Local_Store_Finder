package com.store.finder;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class ChangePassword extends AppCompatActivity {

    Button btnSave, btnHome;
    EditText inpPass1, inpPass2;
    TextView tvPasswordStrengths;
    String LoggedMobile, LoggedState, LoggedDist, LoggedSType,LoggedMsg;
    DatabaseReference database,childref;
    TextView caps, num, small, words, sym,pass;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+='':;()*!])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,15}" +               //at least 8 characters and max 15
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        LoggedMsg = getIntent().getStringExtra("LoggedMSG");
        LoggedSType = getIntent().getStringExtra("LoggedSType");

        btnSave = findViewById(R.id.savePass);
        btnHome = findViewById(R.id.btnHomes);
        inpPass1 = findViewById(R.id.inpPass1);
        inpPass2 = findViewById(R.id.inpPass2);

       // btnSave.setEnabled(false);

        pass  = findViewById(R.id.txtstrong);
        pass.setVisibility(View.INVISIBLE);
        caps  = findViewById(R.id.AZ);
        num   = findViewById(R.id.num);
        small = findViewById(R.id.az);
        words = findViewById(R.id.character);
        sym   = findViewById(R.id.symbol);

        database = FirebaseDatabase.getInstance().getReference().child("LoginDetails");
        childref = FirebaseDatabase.getInstance().getReference().child("RegisteredShop");


        final String LoggedNumber = getIntent().getStringExtra("phonenumber");

        inpPass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pas = inpPass1.getText().toString();
                validatePassword(pas);
                validates();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String Pass1 = null;
                    String Pass2 = null;
                    try {
                        Pass1 = inpPass1.getText().toString().trim();
                        Pass2 = inpPass2.getText().toString().trim();
                        if(Pass1.equals(Pass2)){
                            database.child(LoggedNumber).child("Password").setValue(Pass1);
                            childref.child(LoggedState).child(LoggedDist).child(LoggedSType).child(LoggedMobile).child("Password").setValue(Pass1);

                            AlertDialog.Builder alert = new AlertDialog.Builder(ChangePassword.this);
                            alert.setTitle("Local Store Finder");
                            alert.setMessage("Passwords changed, Now you can login with new credentials");
                            alert.setPositiveButton("Ok",null);
                            alert.show();

                            Intent i = new Intent(ChangePassword.this, LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);}
                        else{
                            AlertDialog.Builder alert = new AlertDialog.Builder(ChangePassword.this);
                            alert.setTitle("Local Store Finder");
                            alert.setMessage("Passwords did not match, retype your passwords");
                            alert.setPositiveButton("Ok",null);
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChangePassword.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
        startActivity(intent);

    }

    private boolean validates() {

        String passwordInput = inpPass1.getText().toString().trim();

        if (passwordInput.isEmpty()) {
             pass.setVisibility(View.INVISIBLE);
            //inpPass1.setError("Field can't be empty");
            return false;
            //   btnChange.setTextColor(Color.GRAY);
            //        Toast.makeText(password.this, "hello Password must be strong", Toast.LENGTH_LONG).show();

        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass.setVisibility(View.INVISIBLE);
            //inpPass1.setError("Password should include following characters.");
            return false;
            //  btnChange.setTextColor(Color.GRAY);
            //        Toast.makeText(password.this, "Password must be strong", Toast.LENGTH_LONG).show();

        } else {
            inpPass1.setError(null);
            pass.setVisibility(View.VISIBLE);
            btnSave.setEnabled(true);
            return true;
        }
    }

    private void validatePassword(String pas) {

        Pattern upper = Pattern.compile("[A-Z]");
        Pattern lower = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern symbol = Pattern.compile("(?=.*[@#$%^&+=])");

        if (!lower.matcher(pas).find()) {
            small.setVisibility(View.VISIBLE);
            small.setTextColor(Color.RED);
        } else {
            small.setVisibility(View.INVISIBLE);
            //small.setTextColor(Color.GREEN);
        }

        if (!upper.matcher(pas).find()) {
            caps.setVisibility(View.VISIBLE);
            caps.setTextColor(Color.RED);
        } else {
            caps.setVisibility(View.INVISIBLE);
            //caps.setTextColor(Color.GREEN);
        }

        if (!digit.matcher(pas).find()) {
            num.setVisibility(View.VISIBLE);
            num.setTextColor(Color.RED);
        } else {
            num.setVisibility(View.INVISIBLE);
            //num.setTextColor(Color.GREEN);
        }

        if (!symbol.matcher(pas).find()) {
            sym.setVisibility(View.VISIBLE);
            sym.setTextColor(Color.RED);
        } else {
            sym.setVisibility(View.INVISIBLE);
           // sym.setTextColor(Color.GREEN);
        }

        if (pas.length() > 7 && pas.length() < 16) {
            words.setVisibility(View.INVISIBLE);
           // words.setTextColor(Color.GREEN);
        } else {
            words.setVisibility(View.VISIBLE);
            words.setTextColor(Color.RED);
        }
    }
}