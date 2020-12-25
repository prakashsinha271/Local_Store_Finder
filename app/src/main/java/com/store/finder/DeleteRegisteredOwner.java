package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteRegisteredOwner extends AppCompatActivity {

    String LoggedMobile,LoggedSType;
    private DatabaseReference database;
    Button Back;
    //String LoggedMobile, LoggedState, LoggedDist, LoggedMsg, LoggedSType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_registered_owner);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedSType = getIntent().getStringExtra("LoggedSType");
        database = FirebaseDatabase.getInstance().getReference();
        Back = findViewById(R.id.btnBack);

        database.child("LoginDetails").child(LoggedMobile).child("State").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbState = dataSnapshot.getValue(String.class);

                if(dbState != null){
                    database.child("LoginDetails").child(LoggedMobile).child("District").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String dbDistrict = dataSnapshot.getValue(String.class);

                            if(dbDistrict != null){
                                database.child("RegisteredShop").child(dbState).child(dbDistrict).child(LoggedSType).child(LoggedMobile).removeValue();
                                database.child("LoginDetails").child(LoggedMobile).child("Password").removeValue();

                            }else{
                                AlertDialog.Builder alert = new AlertDialog.Builder(DeleteRegisteredOwner.this);
                                alert.setTitle("Local Store Finder,");
                                alert.setMessage("Record not found, please write us to re-activate your account");
                                alert.setPositiveButton("Ok",null);
                                alert.show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(DeleteRegisteredOwner.this, "Record not found.", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(DeleteRegisteredOwner.this);
                    alert.setTitle("Local Store Finder,");
                    alert.setMessage("Record not found, please write us to re-activate your account");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(DeleteRegisteredOwner.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }
}