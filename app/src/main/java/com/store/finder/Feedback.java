package com.store.finder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;


public class Feedback extends AppCompatActivity {

    EditText em,sub,msg;
    Button btn;
    String email,pass;
    String LoggedMobile, LoggedState, LoggedDist, LoggedSType;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        LoggedMobile = getIntent().getStringExtra("LoggedID");
        LoggedState = getIntent().getStringExtra("LoggedST");
        LoggedDist = getIntent().getStringExtra("LoggedDST");
        //LoggedMsg = getIntent().getStringExtra("LoggedMSG");
        LoggedSType = getIntent().getStringExtra("LoggedSType");
        String Flag = getIntent().getStringExtra("Flag");
        String OMail = getIntent().getStringExtra("OwnerEmail");
        em = findViewById(R.id.mailID);
        sub = findViewById(R.id.subjectID);
        msg = findViewById(R.id.messageID);
        btn = findViewById(R.id.btnnsend);
        database = FirebaseDatabase.getInstance().getReference().child("RegisteredShop").child(LoggedState).child(LoggedDist).child(LoggedSType).child(LoggedMobile);
        //Toast.makeText(Feedback.this,LoggedMobile+LoggedDist+LoggedState+LoggedSType,Toast.LENGTH_SHORT).show();
        email = "development.project.testmail@gmail.com";
        pass = "Development@(06";


        assert Flag != null;
        if(Flag.equals("Owner_Customer")){
            //em.setText(OMail);    //Shop Owner email will set here while we click Send Us Email on Communication Panel
        }else if(Flag.equals("Owner_Developer")){
            em.setText("aman10yadav.ay@gmail.com");
        }

        database.child("Owner_Name").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String OName = dataSnapshot.getValue(String.class);
                //String tmp = dbname.concat("(" + LoggedMobile + ")");
                sub.setText(String.format("Local Store Finder: %s", OName + " - " + LoggedMobile));
                sub.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {

                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth","true");
                    properties.put("mail.smtp.starttls.enable","true");
                    properties.put("mail.smtp.host","smtp.gmail.com");
                    properties.put("mail.smtp.port","587");

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email, pass);
                        }
                    });

                    try {
                        MimeMessage message = new MimeMessage(session);

                        message.setFrom(new InternetAddress(email));

                        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(em.getText().toString().trim()));
                        message.setRecipients(Message.RecipientType.CC,InternetAddress.parse("prakashsinha271@gmail.com"));

                        message.setSubject(sub.getText().toString().trim());

                        message.setText(msg.getText().toString().trim());

                        new SendMail().execute(message);
                    }
                    catch (MessagingException e) {
                        e.printStackTrace();
                    }
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Feedback.this);
                    alert.setTitle("Local Store Finder,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
            }
        });
    }

    private class SendMail extends AsyncTask<Message,String,String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Feedback.this,"Please wait","Sending Mail.....",true,false);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(Feedback.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>SUCCESS</font>"));
                builder.setMessage("Mail send Successfully !!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        //em.setText("");
                        msg.setText("");
                        sub.setText("");
                    }
                });

                builder.show();
            }
            else {
                AlertDialog.Builder alert = new AlertDialog.Builder(Feedback.this);
                alert.setTitle("Local Store Finder,");
                alert.setMessage("Something went wrong, please retry after some time");
                alert.setPositiveButton("Ok",null);
                alert.show();
            }
        }
    }
}