package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopList extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference, logDatabase;
    ListView listView;
    TextView textView;
    String LoggedShopType, LoggedState, LoggedDist;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ImageView img;
    Owner owner;

    //BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        LoggedShopType = getIntent().getStringExtra("LoggedSTYPE");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        listView = findViewById(R.id.list);
        textView = findViewById(R.id.txtno);

        textView.setText("Tap in particular section for details!!" + "\n" +"(और अधिक जानकारी के लिए विशेष सूची में क्लिक करें !!)" );

        list = new ArrayList<>();
        //img = (ImageView) findR.drawable.logo;
        adapter = new ArrayAdapter<String>(this, R.layout.activity_owner_info, R.id.ownerinfo, list);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("RegisteredShop");
        logDatabase = databaseReference.child(LoggedState).child(LoggedDist).child(LoggedShopType);

        logDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        owner = ds.getValue(Owner.class);
                        assert owner != null;
                        final String call = owner.getMobile_Number();
                        list.add(call+'\t'+'\t'+"  (Mobile Number)" + '\n' +
                                '\n' + "Owner Name: " + owner.getOwner_Name() +
                                '\n' + "Shop Name: " + owner.getShop_Name() +
                                '\n' + "Shop Address: " + owner.getAddress() +
                                '\n' + "!!Click me for view shop rating and more details!!" +
                                "\n" );
                    }
                    listView.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ShopList.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private AdapterView<?> parent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    owner.setMobile_Number(list.get(position));
                    String mob = list.get(position);
                    // Object phone = textView.setText(list.get(position));

                    Intent i = new Intent(ShopList.this,ListviewCallChat.class);
                    i.putExtra("LoggedSType", LoggedShopType);
                    i.putExtra("LoggedST", LoggedState);
                    i.putExtra("LoggedDST", LoggedDist);
                    i.putExtra("LoggedID", mob);
                    startActivity(i);
                }catch(Exception e){
                    Toast.makeText(ShopList.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ShopList.this,CustomerSearch.class);
        startActivity(intent);
    }
}