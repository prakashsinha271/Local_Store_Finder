package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminAddShopType extends AppCompatActivity {

    EditText data;
    Spinner spin;
    String textdata = "";
    DatabaseReference databaseReference;
    ValueEventListener listner;
    ArrayAdapter<String> adapter;
    ArrayList<String> spindatatlist;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_shop_type);


        data = findViewById(R.id.txtn);
        spin = findViewById(R.id.spinn);
        btn  = findViewById(R.id.buttonBack);

        databaseReference = FirebaseDatabase.getInstance().getReference("ShopType");

        spindatatlist = new ArrayList<>();
        adapter = new ArrayAdapter<String>(AdminAddShopType.this,android.R.layout.simple_spinner_dropdown_item,spindatatlist);

        spin.setAdapter(adapter);
        retrievedata();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminAddShopType.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void retrievedata() {

        listner = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()) {

                    spindatatlist.add(item.getValue().toString());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    public void btnadd(View view) {
        textdata = data.getText().toString().trim();
        databaseReference.push().setValue(textdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                data.setText("");
                AlertDialog.Builder alert = new AlertDialog.Builder(AdminAddShopType.this);
                alert.setTitle("Local Store Finder");
                alert.setMessage("New Shop Type Added");
                alert.setPositiveButton("Ok",null);
                alert.show();
            }
        });
    }
}