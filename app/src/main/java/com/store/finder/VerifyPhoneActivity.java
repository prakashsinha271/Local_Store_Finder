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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    private String ph;
    DatabaseReference logDatabase;
    String LoggedMobile, LoggedState, LoggedDist, LoggedMsg, LoggedSType,Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.txtCode);

        final String phonenumber = getIntent().getStringExtra("phonenumber");
        ph = phonenumber;

        logDatabase = FirebaseDatabase.getInstance().getReference().child("LoginDetails");

        logDatabase.child(phonenumber).child("Mobile Number").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbPreMob = dataSnapshot.getValue(String.class);

                if(dbPreMob != null) {
                    //Mobile number exist
                    Toast.makeText(VerifyPhoneActivity.this, "Mobile Number Exist, Please Write a mail with your Registered Mobile Number", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(VerifyPhoneActivity.this, OwnerRegister.class);
                    startActivity(i);
                }
                else {
                    //Mobile number not exist
                    sendVerificationCode(phonenumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        findViewById(R.id.btnnxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    String code = editText.getText().toString().trim();

                    if (code.isEmpty() || code.length() < 6) {

                        editText.setError("Enter code...");
                        editText.requestFocus();
                        return;
                    }
                    verifyCode(code);
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(VerifyPhoneActivity.this);
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

        final String Flag = getIntent().getStringExtra("Flag");
        Intent intent = new Intent(VerifyPhoneActivity.this, OwnerRegister.class);
        intent.putExtra("Flag", Flag);
        startActivity(intent);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final String Flag = getIntent().getStringExtra("Flag");
                            //Toast.makeText(VerifyPhoneActivity.this,Flag,Toast.LENGTH_SHORT).show();

                            if(Flag.equals("NewUser")) {
                                Intent intent = new Intent(VerifyPhoneActivity.this, OwnerDetailsActivity.class);
                                intent.putExtra("phonenumber", ph);
                                startActivity(intent);
                            } else {
                                LoggedMobile = getIntent().getStringExtra("LoggedID");
                                LoggedState = getIntent().getStringExtra("LoggedST");
                                LoggedDist = getIntent().getStringExtra("LoggedDST");
                                LoggedMsg = getIntent().getStringExtra("LoggedMSG");
                                LoggedSType = getIntent().getStringExtra("LoggedSType");

                                Intent intent = new Intent(VerifyPhoneActivity.this, OwnerMobileRegister.class);
                                intent.putExtra("phonenumber", ph);
                                intent.putExtra("LoggedID", LoggedMobile);
                                intent.putExtra("LoggedST", LoggedState);
                                intent.putExtra("LoggedDST", LoggedDist);
                                intent.putExtra("LoggedSType", LoggedSType);
                                intent.putExtra("LoggedMSG", LoggedMsg);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}