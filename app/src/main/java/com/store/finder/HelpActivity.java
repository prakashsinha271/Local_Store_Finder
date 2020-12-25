package com.store.finder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HelpActivity extends AppCompatActivity {

    Button btnNext;
    EditText textAddShop, textPhoneNo,textIssue, textTransNu, textDate, textVerify, textOther;
    RelativeLayout RLSpinner1, RLSpinner2, RLSpinner3, RLSpinner4, RLSpinner5, RLSpinner6, RLNext;
    Spinner dropdown;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        RLSpinner1 = findViewById(R.id.RLAddNewShop);
        RLSpinner2 = findViewById(R.id.RLPassword);
        RLSpinner3 = findViewById(R.id.RLRegister);
        RLSpinner4 = findViewById(R.id.RLVerify);
        RLSpinner5 = findViewById(R.id.RLPayment);
        RLSpinner6 = findViewById(R.id.RLOther);
        RLNext = findViewById(R.id.RLMobBtn);
        btnNext = findViewById(R.id.btnNext);
        textAddShop = findViewById(R.id.shopTypeAddShop);
        textPhoneNo = findViewById(R.id.phoneMob);
        textIssue = findViewById(R.id.issue);
        textTransNu = findViewById(R.id.transNumber);
        textDate = findViewById(R.id.datePicker);
        textVerify = findViewById(R.id.verify);
        textOther = findViewById(R.id.other);
        dropdown = findViewById(R.id.SpinnerSubject);
        final int[] flagVal = {0};
        final String[] selSubject = {null};

        String[] items = new String[]{"--Select Help Category--", "Add New Shop Type", "Unable to Recover Password", "Unable to Register", "Unable to Verify Account", "Payment Related Query", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position){
                    case 0:
                        RLSpinner1.setVisibility(View.INVISIBLE);
                        RLSpinner2.setVisibility(View.INVISIBLE);
                        RLSpinner3.setVisibility(View.INVISIBLE);
                        RLSpinner4.setVisibility(View.INVISIBLE);
                        RLSpinner5.setVisibility(View.INVISIBLE);
                        RLSpinner6.setVisibility(View.INVISIBLE);
                        RLNext.setVisibility(View.INVISIBLE);
                        textPhoneNo.getText().clear();
                        textAddShop.getText().clear();
                        textPhoneNo.getText().clear();
                        textIssue.getText().clear();
                        textTransNu.getText().clear();
                        textDate.getText().clear();
                        textVerify.getText().clear();
                        textOther.getText().clear();
                        flagVal[0] = position;
                        selSubject[0] = parent.getItemAtPosition(position).toString();
                        break;
                    case 1:
                        RLSpinner1.setVisibility(View.VISIBLE);
                        RLSpinner2.setVisibility(View.INVISIBLE);
                        RLSpinner3.setVisibility(View.INVISIBLE);
                        RLSpinner4.setVisibility(View.INVISIBLE);
                        RLSpinner5.setVisibility(View.INVISIBLE);
                        RLSpinner6.setVisibility(View.INVISIBLE);
                        RLNext.setVisibility(View.VISIBLE);
                        textPhoneNo.getText().clear();
                        textAddShop.getText().clear();
                        textPhoneNo.getText().clear();
                        textIssue.getText().clear();
                        textTransNu.getText().clear();
                        textDate.getText().clear();
                        textVerify.getText().clear();
                        textOther.getText().clear();
                        flagVal[0] = position;
                        selSubject[0] = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(HelpActivity.this, "Selected " + position + " " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        RLSpinner1.setVisibility(View.INVISIBLE);
                        RLSpinner2.setVisibility(View.VISIBLE);
                        RLSpinner3.setVisibility(View.INVISIBLE);
                        RLSpinner4.setVisibility(View.INVISIBLE);
                        RLSpinner5.setVisibility(View.INVISIBLE);
                        RLSpinner6.setVisibility(View.INVISIBLE);
                        RLNext.setVisibility(View.VISIBLE);
                        textPhoneNo.getText().clear();
                        textAddShop.getText().clear();
                        textPhoneNo.getText().clear();
                        textIssue.getText().clear();
                        textTransNu.getText().clear();
                        textDate.getText().clear();
                        textVerify.getText().clear();
                        textOther.getText().clear();
                        flagVal[0] = position;
                        selSubject[0] = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(HelpActivity.this, "Selected " + position + " " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        RLSpinner1.setVisibility(View.INVISIBLE);
                        RLSpinner2.setVisibility(View.INVISIBLE);
                        RLSpinner3.setVisibility(View.VISIBLE);
                        RLSpinner4.setVisibility(View.INVISIBLE);
                        RLSpinner5.setVisibility(View.INVISIBLE);
                        RLSpinner6.setVisibility(View.INVISIBLE);
                        RLNext.setVisibility(View.VISIBLE);
                        textPhoneNo.getText().clear();
                        textAddShop.getText().clear();
                        textPhoneNo.getText().clear();
                        textIssue.getText().clear();
                        textTransNu.getText().clear();
                        textDate.getText().clear();
                        textVerify.getText().clear();
                        textOther.getText().clear();
                        flagVal[0] = position;
                        selSubject[0] = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(HelpActivity.this, "Selected " + position + " " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        RLSpinner1.setVisibility(View.INVISIBLE);
                        RLSpinner2.setVisibility(View.INVISIBLE);
                        RLSpinner3.setVisibility(View.INVISIBLE);
                        RLSpinner4.setVisibility(View.VISIBLE);
                        RLSpinner5.setVisibility(View.INVISIBLE);
                        RLSpinner6.setVisibility(View.INVISIBLE);
                        RLNext.setVisibility(View.VISIBLE);
                        textPhoneNo.getText().clear();
                        textAddShop.getText().clear();
                        textPhoneNo.getText().clear();
                        textIssue.getText().clear();
                        textTransNu.getText().clear();
                        textDate.getText().clear();
                        textVerify.getText().clear();
                        textOther.getText().clear();
                        flagVal[0] = position;
                        selSubject[0] = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(HelpActivity.this, "Selected " + position + " " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        RLSpinner1.setVisibility(View.INVISIBLE);
                        RLSpinner2.setVisibility(View.INVISIBLE);
                        RLSpinner3.setVisibility(View.INVISIBLE);
                        RLSpinner4.setVisibility(View.INVISIBLE);
                        RLSpinner5.setVisibility(View.VISIBLE);
                        RLSpinner6.setVisibility(View.INVISIBLE);
                        RLNext.setVisibility(View.VISIBLE);
                        textPhoneNo.getText().clear();
                        textAddShop.getText().clear();
                        textPhoneNo.getText().clear();
                        textIssue.getText().clear();
                        textTransNu.getText().clear();
                        textDate.getText().clear();
                        textVerify.getText().clear();
                        textOther.getText().clear();
                        flagVal[0] = position;
                        selSubject[0] = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(HelpActivity.this, "Selected " + position + " " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                        break;
                    case 6:
                        RLSpinner1.setVisibility(View.INVISIBLE);
                        RLSpinner2.setVisibility(View.INVISIBLE);
                        RLSpinner3.setVisibility(View.INVISIBLE);
                        RLSpinner4.setVisibility(View.INVISIBLE);
                        RLSpinner5.setVisibility(View.INVISIBLE);
                        RLSpinner6.setVisibility(View.VISIBLE);
                        RLNext.setVisibility(View.VISIBLE);
                        textPhoneNo.getText().clear();
                        textAddShop.getText().clear();
                        textPhoneNo.getText().clear();
                        textIssue.getText().clear();
                        textTransNu.getText().clear();
                        textDate.getText().clear();
                        textVerify.getText().clear();
                        textOther.getText().clear();
                        flagVal[0] = position;
                        selSubject[0] = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(HelpActivity.this, "Selected " + position + " " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textDate.setInputType(InputType.TYPE_NULL);
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(HelpActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                textDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    try{
                        getValuesOfEditText(flagVal[0], selSubject[0]);
                    }catch(Exception e){
                        Toast.makeText(HelpActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(HelpActivity.this);
                    alert.setTitle("Local Store Finder,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }

            }
        });

    }
    private void getValuesOfEditText(int selectedCase, String selectedSubject){
        String newSubjectLine;
        String mailBody;
        String enteredMobile = textPhoneNo.getText().toString().trim();
        switch (selectedCase){
            case 0:
                Toast.makeText(HelpActivity.this, "Select your problem title first", Toast.LENGTH_LONG).show();
                break;
            case 1:
                if(textPhoneNo.getText().toString().trim().equals("") || textAddShop.getText().toString().trim().equals("")){
                    Toast.makeText(HelpActivity.this, "Shop Name and Mobile Number are Required", Toast.LENGTH_LONG).show();
                    return;
                }
                String newShop = textAddShop.getText().toString().trim();
                newSubjectLine = "Local Store Finder: " + selectedSubject + " - " + enteredMobile;
                mailBody = "Hello, I want to register my shop on Local Store Finder, but unfortunately I can not able to find my shop type on your list, please add following shop type into your shop type list, so that I can register my shop.\n\nNew Shop Type Suggestion - " + newShop + "\n\n\n\nThanks and Regards\n" + enteredMobile;
                sendMail(mailBody, newSubjectLine);
                break;
            case 2:
                if(textPhoneNo.getText().toString().trim().equals("")){
                    Toast.makeText(HelpActivity.this, "Mobile Number is Required", Toast.LENGTH_LONG).show();
                    return;
                }
                newSubjectLine = "Local Store Finder: " + selectedSubject + " - " + enteredMobile;
                mailBody = "Hello, I am trying to recover my login credential with my registered mobile number, but unfortunately I can't. I follow all the steps to recover credential but it didn't work for me. Please help me with following mobile number.\n\nRegistered Mobile Number - " + enteredMobile + "\n\n\n\nThanks and Regards\n" + enteredMobile;
                sendMail(mailBody, newSubjectLine);
                break;
            case 3:
                if(textPhoneNo.getText().toString().trim().equals("") || textIssue.getText().toString().trim().equals("")){
                    Toast.makeText(HelpActivity.this, "Mobile Number and Problem are Required, transaction details may be optional", Toast.LENGTH_LONG).show();
                    return;
                }
                newSubjectLine = "Local Store Finder: " + selectedSubject + " - " + enteredMobile;
                mailBody = "Hello, I am trying to register/recover my shop, but unfortunately I can't. I follow all the steps to registration/account recover process but it didn't work for me. Please help me with following mobile number.\n\nRegistered Mobile Number - " + enteredMobile + "\n\nIssue - " + textIssue.getText().toString().trim() +"\nTransaction Details - " + textTransNu.getText().toString().trim() + " Date - " + textDate.getText().toString().trim() + "\n\n\n\nThanks and Regards\n" + enteredMobile;
                sendMail(mailBody, newSubjectLine);
                break;
            case 4:
                if(textPhoneNo.getText().toString().trim().equals("")){
                    Toast.makeText(HelpActivity.this, "Mobile Number is Required", Toast.LENGTH_LONG).show();
                    return;
                }
                newSubjectLine = "Local Store Finder: " + selectedSubject + " - " + enteredMobile;
                mailBody = "Hello, I am trying to verify my account, but unfortunately I didn't get OTP. I follow all the steps to registration/account recover/password change process but it didn't work for me. Please help me with following mobile number.\n\nRegistered Mobile Number - " + enteredMobile + "\n\n\n\nThanks and Regards\n" + enteredMobile;
                sendMail(mailBody, newSubjectLine);
                break;
            case 5:
                if(textPhoneNo.getText().toString().trim().equals("") || textVerify.getText().toString().trim().equals("")){
                    Toast.makeText(HelpActivity.this, "Mobile Number and Problem are Required", Toast.LENGTH_LONG).show();
                    return;
                }
                newSubjectLine = "Local Store Finder: " + selectedSubject + " - " + enteredMobile;
                mailBody = "Hello, I have payment related issue, mentioned as - " + textVerify.getText().toString().trim() + "\n\nPlease help me. You can contact me by following mobile number.\n\nRegistered Mobile Number - " + enteredMobile + "\n\n\n\nThanks and Regards\n" + enteredMobile;
                sendMail(mailBody, newSubjectLine);
                break;
            case 6:
                if(textPhoneNo.getText().toString().trim().equals("") || textOther.getText().toString().trim().equals("")){
                    Toast.makeText(HelpActivity.this, "Mobile Number and Problem are Required", Toast.LENGTH_LONG).show();
                    return;
                }
                newSubjectLine = "Local Store Finder: " + selectedSubject + " - " + enteredMobile;
                mailBody = "Hello, I have an issue, mentioned as - \n\n" + textOther.getText().toString().trim() + "\n\nPlease help me. You can contact me by following mobile number.\n\nRegistered Mobile Number - " + enteredMobile + "\n\n\n\nThanks and Regards\n" + enteredMobile;
                sendMail(mailBody, newSubjectLine);
                break;
        }
    }

    private void sendMail(String mailBody, String newSubjectLine) {
        String addressTo = "prakashsinha271@gmail.com";
        String addressCC = "aman10yadav.ay@gmail.com";
        final String senderMail = "development.project.testmail@gmail.com";
        final String senderPass = "Development@(06";
        //Toast.makeText(HelpActivity.this, "Composed mail -  " + newSubjectLine + mailBody, Toast.LENGTH_LONG).show();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMail, senderPass);
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(senderMail));

            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(addressTo.trim()));
            message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(addressCC.trim()));

            message.setSubject(newSubjectLine.trim());

            message.setText(mailBody.trim());

            new SendMail().execute(message);
        }catch(Exception e){
            Toast.makeText(HelpActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class SendMail extends AsyncTask<Message,String,String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(HelpActivity.this,"Please wait","Sending Mail.....",true,false);
        }

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
                return "Success";
            }
            catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            if(s.equals("Success")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HelpActivity.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>SUCCESS</font>"));
                builder.setMessage("Mail send Successfully !!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        textPhoneNo.getText().clear();
                        textAddShop.getText().clear();
                        textPhoneNo.getText().clear();
                        textIssue.getText().clear();
                        textTransNu.getText().clear();
                        textDate.getText().clear();
                        textVerify.getText().clear();
                        textOther.getText().clear();
                    }
                });
                builder.show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Something went wrong !!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}