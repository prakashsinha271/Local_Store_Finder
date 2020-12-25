package com.store.finder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectAccountPlan extends AppCompatActivity {

    private RadioGroup radioGroup;
    Button submit, clear, back;
    String LoggedMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_plan);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        submit = findViewById(R.id.submit);
        clear = findViewById(R.id.clear);
        back = findViewById(R.id.btnBackToHome);
        radioGroup = findViewById(R.id.groupRadio);
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

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(SelectAccountPlan.this,"Please Select Your Plan", Toast.LENGTH_SHORT).show();
                }
                else {
                    RadioButton radioButton = radioGroup.findViewById(selectedId);
                    //Toast.makeText(SelectAccountPlan.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                    continueNext(radioButton.getText());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectAccountPlan.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                radioGroup.clearCheck();
            }
        });
    }

    private void continueNext(CharSequence selectedId) {

        if(selectedId.equals("1 Month, @Rs. 30/-")){
            Toast.makeText(SelectAccountPlan.this, "You have to pay 30Rs.", Toast.LENGTH_SHORT).show();
        }else if(selectedId.equals("2 Months, @Rs. 60/-")){
            Toast.makeText(SelectAccountPlan.this, "You have to pay 60Rs.", Toast.LENGTH_SHORT).show();
        }else if(selectedId.equals("6 Months, @Rs. 150/-")){
            Toast.makeText(SelectAccountPlan.this, "You have to pay 150Rs.", Toast.LENGTH_SHORT).show();
        }else if(selectedId.equals("1 Year, @Rs. 300/-")){
            Toast.makeText(SelectAccountPlan.this, "You have to pay 300Rs.", Toast.LENGTH_SHORT).show();
        }else if(selectedId.equals("Cancel Subscriptions")){
            Toast.makeText(SelectAccountPlan.this, "Deleting Subscriptions", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SelectAccountPlan.this, DeleteRegisteredOwner.class);
            i.putExtra("LoggedID", LoggedMobile);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}