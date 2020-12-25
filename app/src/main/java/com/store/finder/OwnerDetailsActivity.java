package com.store.finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class OwnerDetailsActivity extends AppCompatActivity {

    EditText OwnerName;
    EditText ShopName;
    EditText Phone;
    EditText Password;
    EditText Address;
    EditText District;
    int flag;
    private int value = 0;
    Button btnSaves;
    private DatabaseReference database, logDatabase,databaseReference;
    private String Mobile;
    private Spinner spin, Districts, inpStates;
    ValueEventListener listner;
    ArrayAdapter<String> adapter;
    ArrayList<String> spindatatlist;
    TextView caps, num, small, words, sym,include,strongpass;
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
        setContentView(R.layout.activity_owner_details);

        btnSaves = findViewById(R.id.btnSave);
        OwnerName = findViewById(R.id.inpOwnerName);
        ShopName = findViewById(R.id.impShopName);
        Phone = findViewById(R.id.inpPhone);
        Password = findViewById(R.id.inpPass);
        Address = findViewById(R.id.inpAddress);
        inpStates = findViewById(R.id.inpState);
        Districts = findViewById(R.id.inpDistrict);
        spin = findViewById(R.id.inpSType);

        btnSaves.setEnabled(false);
        CheckBox checkBox = findViewById(R.id.checkBox);

        include = findViewById(R.id.inc);
        strongpass = findViewById(R.id.txtgoodpass);
        strongpass.setVisibility(View.INVISIBLE);
        caps    = findViewById(R.id.AZ);
        num     = findViewById(R.id.num);
        small   = findViewById(R.id.az);
        words   = findViewById(R.id.character);
        sym     = findViewById(R.id.symbol);

        Mobile = getIntent().getStringExtra("phonenumber");

        CheckInternetConnectivity();

        //WE HAVE TO DELETE OLD REGISTERED SHOP DATA FIRST, BEFORE EDITING DETAILS IF STATE or DISTRICT or Both will be change by shop owner
        try{
            assert Mobile != null;
            Phone.setText(Mobile.substring(3));
        }
        catch(Exception e){

        }
        database = FirebaseDatabase.getInstance().getReference().child("RegisteredShop");

        logDatabase = FirebaseDatabase.getInstance().getReference().child("LoginDetails");
        databaseReference = FirebaseDatabase.getInstance().getReference("ShopType");

        btnSaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    RegisterRecords();
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(OwnerDetailsActivity.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
            }
        });

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null != activenetwork) {
                    SignOut();
                } else   {
                    AlertDialog.Builder alert = new AlertDialog.Builder(OwnerDetailsActivity.this);
                    alert.setTitle("Hii,");
                    alert.setMessage("Please Enable Network !!");
                    alert.setPositiveButton("Ok",null);
                    alert.show();
                }
            }
        });

        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pas = Password.getText().toString();
                validatePassword(pas);
                validates();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        spindatatlist = new ArrayList<>();
        adapter = new ArrayAdapter<String>(OwnerDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,spindatatlist);

        spin.setAdapter(adapter);
        retrievedata();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ( (CheckBox) v).isChecked();
                if(checked && flag==1 ) {
                    btnSaves.setEnabled(true);
                }
                else {
                    btnSaves.setVisibility(View.VISIBLE);
                    btnSaves.setEnabled(false);
                }
            }
        });

    }

    private void CheckInternetConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager.getActiveNetworkInfo();
        if(null != activenetwork) {

        } else   {
            AlertDialog.Builder alert = new AlertDialog.Builder(OwnerDetailsActivity.this);
            alert.setTitle("Hii,");
            alert.setMessage("Please Enable Network !!");
            alert.setPositiveButton("Ok",null);
            alert.show();
        }
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(OwnerDetailsActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @SuppressLint("WrongConstant")
    private boolean validates() {

        String passwordInput = Password.getText().toString().trim();
        // Toast.makeText(password.this, "hiiPassword must be strong", Toast.LENGTH_LONG).show();

        if (passwordInput.isEmpty()) {
            btnSaves.setEnabled(false);
            strongpass.setVisibility(View.INVISIBLE);
            include.setVisibility(View.VISIBLE);
            flag = 0;
            //Password.setError("Field can't be empty");
            return false;
            //   btnChange.setTextColor(Color.GRAY);
            //        Toast.makeText(password.this, "hello Password must be strong", Toast.LENGTH_LONG).show();

        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            btnSaves.setEnabled(false);
            include.setVisibility(View.VISIBLE);
            strongpass.setVisibility(View.INVISIBLE);
            flag = 0;
            //Password.setError("Password should include following characters.");
            return false;
            //  btnChange.setTextColor(Color.GRAY);
            //        Toast.makeText(password.this, "Password must be strong", Toast.LENGTH_LONG).show();

        } else {
            Password.setError(null);
            include.setVisibility(View.INVISIBLE);
            strongpass.setVisibility(View.VISIBLE);
            flag = 1;
           // btnSaves.setEnabled(true);
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
           // num.setTextColor(Color.GREEN);
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

    private void retrievedata() {

        listner = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()) {

                    spindatatlist.add(item.getValue().toString());
                }

                adapter.notifyDataSetChanged();
                //Toast.makeText(CustomerSearch.this, "Hii" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final String state[] = {"---Select State---", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh (UT)", "Chhattisgarh", "Dadra and Nagar Haveli (UT)", "Daman and Diu (UT)", "Delhi (NCT)", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep (UT)", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry (UT)", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh", "West Bengal"
        };

        final String district[] = {"---Select District --"};

        final String AndhraPradesh[] = {
                //"--Select Andhra Pradesh District--",
                "---Select District --","Anantapur", "Chittoor", "East Godavari", "Guntur", "Krishna", "Kurnool", "Nellore", "Prakasam", "Srikakulam", "Visakhapatnam", "Vizianagaram", "West Godavari", "YSR Kadapa"
        };

        final String ArunanchalPradesh[] = {
                //"--Select Arunanchal Pradesh District--",
                "---Select District --","Tawang", "West Kameng", "East Kameng", "Papum Pare", "Kurung Kumey", "Kra Daadi", "Lower Subansiri", "Upper Subans iri", "West Siang", "East Siang", "Siang", "Upper Siang", "Lower Siang", "Lower Dibang Valley", "Dibang Valley", "Anjaw", "Lohit", "Namsai", "Changlang", "Tirap", "Longding"
        };

        final String Assam[] = {
                //"--Select Assam District--",
                "---Select District --","Baksa", "Barpeta", "Biswanath", "Bongaigaon", "Cachar", "Charaideo", "Chirang", "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Goalpara", "Golaghat", "Hailakandi", "Hojai", "Jorhat", "Kamrup Metropolitan", "Kamrup", "Karbi Anglong", "Karimganj", "Kokrajhar", "Lakhimpur", "Majuli", "Morigaon", "Nagaon", "Nalbari", "Dima Hasao", "Sivasagar", "Sonitpur", "South Salmara-Mankachar", "Tinsukia", "Udalguri", "West Karbi Anglong"
        };

        final String Bihar[] = {
                //"--Select Bihar District--",
                "---Select District --","Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", "Bhojpur", "Buxar", "Darbhanga", "East Champaran (Motihari)", "Gaya", "Gopalganj", "Jamui", "Jehanabad", "Kaimur (Bhabua)", "Katihar", "Khagaria", "Kishanganj", "Lakhisarai", "Madhepura", "Madhubani", "Munger (Monghyr)", "Muzaffarpur", "Nalanda", "Nawada", "Patna", "Purnia (Purnea)", "Rohtas", "Saharsa", "Samastipur", "Saran", "Sheikhpura", "Sheohar", "Sitamarhi", "Siwan", "Supaul", "Vaishali", "West Champaran"
        };

        final String chandigarh[] = {
                //"--Select Chandigarh District--",
                "---Select District --","Chandigarh"
        };

        final String Chhattisgarh[] = {
                //"--Select Chhattisgarh District--",
                "---Select District --","Balod", "Baloda Bazar", "Balrampur", "Bastar", "Bemetara", "Bijapur", "Bilaspur", "Dantewada (South Bastar)", "Dhamtari", "Durg", "Gariyaband", "Janjgir-Champa", "Jashpur", "Kabirdham (Kawardha)", "Kanker (North Bastar)", "Kondagaon", "Korba", "Korea (Koriya)", "Mahasamund", "Mungeli", "Narayanpur", "Raigarh", "Raipur", "Rajnandgaon", "Sukma", "Surajpur  ", "Surguja"
        };

        final String Dadra[] = {
                //"--Select Dadra and Nagar Haveli (UT) District--",
                "---Select District --","Dadra & Nagar Haveli"
        };

        final String Daman[] = {
                //"--Select Daman and Diu (UT) District--",
                "---Select District --","Daman", "Diu"
        };

        final String Delhi[] = {
                //"--Select Delhi (NCT) District--",
                "---Select District --","Central Delhi", "East Delhi", "New Delhi", "North Delhi", "North East  Delhi", "North West  Delhi", "Shahdara", "South Delhi", "South East Delhi", "South West  Delhi", "West Delhi"
        };

        final String Goa[] = {
                //"--Select Goa District--",
                "---Select District --","North Goa", "South Goa"
        };

        final String Gujarat[] = {
                //  "--Select Gujarat  District--",
                "---Select District --","Ahmedabad", "Amreli", "Anand", "Aravalli", "Banaskantha (Palanpur)", "Bharuch", "Bhavnagar", "Botad", "Chhota Udepur", "Dahod", "Dangs (Ahwa)", "Devbhoomi Dwarka", "Gandhinagar", "Gir Somnath", "Jamnagar", "Junagadh", "Kachchh", "Kheda (Nadiad)", "Mahisagar", "Mehsana", "Morbi", "Narmada (Rajpipla)", "Navsari", "Panchmahal (Godhra)", "Patan", "Porbandar", "Rajkot", "Sabarkantha (Himmatnagar)", "Surat", "Surendranagar", "Tapi (Vyara)", "Vadodara", "Valsad"
        };

        final String Haryana[] = {
                //"--Select Haryana District--",
                "---Select District --","Ambala", "Bhiwani", "Charkhi Dadri", "Faridabad", "Fatehabad", "Gurgaon", "Hisar", "Jhajjar", "Jind", "Kaithal", "Karnal", "Kurukshetra", "Mahendragarh", "Mewat", "Palwal", "Panchkula", "Panipat", "Rewari", "Rohtak", "Sirsa", "Sonipat", "Yamunanagar"
        };

        final String Himachal[] = {
                //"--Select Himachal Pradesh District--",
                "---Select District --","Bilaspur", "Chamba", "Hamirpur", "Kangra", "Kinnaur", "Kullu", "Lahaul &amp; Spiti", "Mandi", "Shimla", "Sirmaur (Sirmour)", "Solan", "Una"
        };

        final String JammuKashmir[] = {
                //"--Select Jammu and Kashmir District--",
                "---Select District --","Anantnag", "Bandipore", "Baramulla", "Budgam", "Doda", "Ganderbal", "Jammu", "Kargil", "Kathua", "Kishtwar", "Kulgam", "Kupwara", "Leh", "Poonch", "Pulwama", "Rajouri", "Ramban", "Reasi", "Samba", "Shopian", "Srinagar", "Udhampur"
        };

        final String Jharkhand[] = {
                //"--Select Jharkhand District--",
                "---Select District --","Bokaro", "Chatra", "Deoghar", "Dhanbad", "Dumka", "East Singhbhum", "Garhwa", "Giridih", "Godda", "Gumla", "Hazaribag", "Jamtara", "Khunti", "Koderma", "Latehar", "Lohardaga", "Pakur", "Palamu", "Ramgarh", "Ranchi", "Sahibganj", "Seraikela-Kharsawan", "Simdega", "West Singhbhum"
        };

        final String Karnataka[] = {
                //"--Select Karnataka District--",
                "---Select District --","Bagalkot", "Ballari (Bellary)", "Belagavi (Belgaum)", "Bengaluru (Bangalore) Rural", "Bengaluru (Bangalore) Urban", "Bidar", "Chamarajanagar", "Chikballapur", "Chikkamagaluru (Chikmagalur)", "Chitradurga", "Dakshina Kannada", "Davangere", "Dharwad", "Gadag", "Hassan", "Haveri", "Kalaburagi (Gulbarga)", "Kodagu", "Kolar", "Koppal", "Mandya", "Mysuru (Mysore)", "Raichur", "Ramanagara", "Shivamogga (Shimoga)", "Tumakuru (Tumkur)", "Udupi", "Uttara Kannada (Karwar)", "Vijayapura (Bijapur)", "Yadgir"
        };

        final String Kerala[] = {
                //"--Select Kerala District--",
                "---Select District --","Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasaragod", "Kollam", "Kottayam", "Kozhikode", "Malappuram", "Palakkad", "Pathanamthitta", "Thiruvananthapuram", "Thrissur", "Wayanad"
        };

        final String Lakshadweep[] = {
                //"--Select Lakshadweep (UT) District--",
                "---Select District --","Agatti", "Amini", "Androth", "Bithra", "Chethlath", "Kavaratti", "Kadmath", "Kalpeni", "Kilthan", "Minicoy"
        };

        final String Madhya[] = {
//                "--Select Madhya Pradesh District--",
                "---Select District --","Agar Malwa", "Alirajpur", "Anuppur", "Ashoknagar", "Balaghat", "Barwani", "Betul", "Bhind", "Bhopal", "Burhanpur", "Chhatarpur", "Chhindwara", "Damoh", "Datia", "Dewas", "Dhar", "Dindori", "Guna", "Gwalior", "Harda", "Hoshangabad", "Indore", "Jabalpur", "Jhabua", "Katni", "Khandwa", "Khargone", "Mandla", "Mandsaur", "Morena", "Narsinghpur", "Neemuch", "Panna", "Raisen", "Rajgarh", "Ratlam", "Rewa", "Sagar", "Satna", "Sehore", "Seoni", "Shahdol", "Shajapur", "Sheopur", "Shivpuri", "Sidhi", "Singrauli", "Tikamgarh", "Ujjain", "Umaria", "Vidisha"
        };

        final String Maharashtra[] = {
                //"--Select Maharashtra District--",
                "---Select District --","Ahmednagar", "Akola", "Amravati", "Aurangabad", "Beed", "Bhandara", "Buldhana", "Chandrapur", "Dhule", "Gadchiroli", "Gondia", "Hingoli", "Jalgaon", "Jalna", "Kolhapur", "Latur", "Mumbai City", "Mumbai Suburban", "Nagpur", "Nanded", "Nandurbar", "Nashik", "Osmanabad", "Palghar", "Parbhani", "Pune", "Raigad", "Ratnagiri", "Sangli", "Satara", "Sindhudurg", "Solapur", "Thane", "Wardha", "Washim", "Yavatmal"
        };

        final String Manipur[] = {
                //"--Select Manipur District--",
                "---Select District --","Bishnupur", "Chandel", "Churachandpur", "Imphal East", "Imphal West", "Jiribam", "Kakching", "Kamjong", "Kangpokpi", "Noney", "Pherzawl", "Senapati", "Tamenglong", "Tengnoupal", "Thoubal", "Ukhrul"
        };

        final String Meghalaya[] = {
                //"--Select Meghalaya District--",
                "---Select District --","East Garo Hills", "East Jaintia Hills", "East Khasi Hills", "North Garo Hills", "Ri Bhoi", "South Garo Hills", "South West Garo Hills ", "South West Khasi Hills", "West Garo Hills", "West Jaintia Hills", "West Khasi Hills"
        };

        final String Mizoram[] = {
                //"--Select Mizoram District--",
                "---Select District --","Aizawl", "Champhai", "Kolasib", "Lawngtlai", "Lunglei", "Mamit", "Saiha", "Serchhip"
        };

        final String Nagaland[] = {
                //--Select Nagaland District--",
                "---Select District --","Dimapur", "Kiphire", "Kohima", "Longleng", "Mokokchung", "Mon", "Peren", "Phek", "Tuensang", "Wokha", "Zunheboto"
        };

        final String Odisha[] = {
                //"--Select Odisha District--",
                "---Select District --","Angul", "Balangir", "Balasore", "Bargarh", "Bhadrak", "Boudh", "Cuttack", "Deogarh", "Dhenkanal", "Gajapati", "Ganjam", "Jagatsinghapur", "Jajpur", "Jharsuguda", "Kalahandi", "Kandhamal", "Kendrapara", "Kendujhar (Keonjhar)", "Khordha", "Koraput", "Malkangiri", "Mayurbhanj", "Nabarangpur", "Nayagarh", "Nuapada", "Puri", "Rayagada", "Sambalpur", "Sonepur", "Sundargarh"
        };

        final String Puducherry[] = {
                //--Select Puducherry (UT) District--",
                "---Select District --","Karaikal", "Mahe", "Pondicherry", "Yanam"
        };

        final String Punjab[] = {
                //"--Select Punjab District--",
                "---Select District --","Amritsar", "Barnala", "Bathinda", "Faridkot", "Fatehgarh Sahib", "Fazilka", "Ferozepur", "Gurdaspur", "Hoshiarpur", "Jalandhar", "Kapurthala", "Ludhiana", "Mansa", "Moga", "Muktsar", "Nawanshahr (Shahid Bhagat Singh Nagar)", "Pathankot", "Patiala", "Rupnagar", "Sahibzada Ajit Singh Nagar (Mohali)", "Sangrur", "Tarn Taran"
        };

        final String Rajasthan[] = {
                //"--Select Rajasthan District--",
                "---Select District --","Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bharatpur", "Bhilwara", "Bikaner", "Bundi", "Chittorgarh", "Churu", "Dausa", "Dholpur", "Dungarpur", "Hanumangarh", "Jaipur", "Jaisalmer", "Jalore", "Jhalawar", "Jhunjhunu", "Jodhpur", "Karauli", "Kota", "Nagaur", "Pali", "Pratapgarh", "Rajsamand", "Sawai Madhopur", "Sikar", "Sirohi", "Sri Ganganagar", "Tonk", "Udaipur"
        };

        final String Sikkim[] = {
                //"--Select Sikkim District--",
                "---Select District --","East Sikkim", "North Sikkim", "South Sikkim", "West Sikkim"
        };

        final String TamilNadu[] = {
                //"--Select Tamil Nadu District--",
                "---Select District --","Ariyalur", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kanchipuram", "Kanyakumari", "Karur", "Krishnagiri", "Madurai", "Nagapattinam", "Namakkal", "Nilgiris", "Perambalur", "Pudukkottai", "Ramanathapuram", "Salem", "Sivaganga", "Thanjavur", "Theni", "Thoothukudi (Tuticorin)", "Tiruchirappalli", "Tirunelveli", "Tiruppur", "Tiruvallur", "Tiruvannamalai", "Tiruvarur", "Vellore", "Viluppuram", "Virudhunagar"
        };

        final String Telangana[] = {
                //"--Select Telangana District--",
                "---Select District --","Adilabad", "Bhadradri Kothagudem", "Hyderabad", "Jagtial", "Jangaon", "Jayashankar Bhoopalpally", "Jogulamba Gadwal", "Kamareddy", "Karimnagar", "Khammam", "Komaram Bheem Asifabad", "Mahabubabad", "Mahabubnagar", "Mancherial", "Medak", "Medchal", "Nagarkurnool", "Nalgonda", "Nirmal", "Nizamabad", "Peddapalli", "Rajanna Sircilla", "Rangareddy", "Sangareddy", "Siddipet", "Suryapet", "Vikarabad", "Wanaparthy", "Warangal (Rural)", "Warangal (Urban)", "Yadadri Bhuvanagiri"
        };

        final String Tripura[] = {
                //"--Select Tripura District--",
                "---Select District --","Dhalai", "Gomati", "Khowai", "North Tripura", "Sepahijala", "South Tripura", "Unakoti", "West Tripura"
        };

        final String Uttarakhand[] = {
                //"--Select Uttarakhand District--",
                "---Select District --","Almora", "Bageshwar", "Chamoli", "Champawat", "Dehradun", "Haridwar", "Nainital", "Pauri Garhwal", "Pithoragarh", "Rudraprayag", "Tehri Garhwal", "Udham Singh Nagar", "Uttarkashi"
        };

        final String UttarPradesh[] = {
                //"--Select Uttar Pradesh District--",
                "---Select District --","Agra", "Aligarh", "Allahabad", "Ambedkar Nagar", "Amethi (Chatrapati Sahuji Mahraj Nagar)", "Amroha (J.P. Nagar)", "Auraiya", "Azamgarh", "Baghpat", "Bahraich", "Ballia", "Balrampur", "Banda", "Barabanki", "Bareilly", "Basti", "Bhadohi", "Bijnor", "Budaun", "Bulandshahr", "Chandauli", "Chitrakoot", "Deoria", "Etah", "Etawah", "Faizabad", "Farrukhabad", "Fatehpur", "Firozabad", "Gautam Buddha Nagar", "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur", "Hamirpur", "Hapur (Panchsheel Nagar)", "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi", "Kannauj", "Kanpur Dehat", "Kanpur Nagar", "Kanshiram Nagar (Kasganj)", "Kaushambi", "Kushinagar (Padrauna)", "Lakhimpur - Kheri", "Lalitpur", "Lucknow", "Maharajganj", "Mahoba", "Mainpuri", "Mathura", "Mau", "Meerut", "Mirzapur", "Moradabad", "Muzaffarnagar", "Pilibhit", "Pratapgarh", "RaeBareli", "Rampur", "Saharanpur", "Sambhal (Bhim Nagar)", "Sant Kabir Nagar", "Shahjahanpur", "Shamali (Prabuddh Nagar)", "Shravasti", "Siddharth Nagar", "Sitapur", "Sonbhadra", "Sultanpur", "Unnao", "Varanasi"
        };

        final String WestBengal[] = {
                //"--Select West Bengal District--",
                "---Select District --","Alipurduar", "Bankura", "Birbhum", "Burdwan (Bardhaman)", "Cooch Behar", "Dakshin Dinajpur (South Dinajpur)", "Darjeeling", "Hooghly", "Howrah", "Jalpaiguri", "Kalimpong", "Kolkata", "Malda", "Murshidabad", "Nadia", "North 24 Parganas", "Paschim Medinipur (West Medinipur)", "Purba Medinipur (East Medinipur)", "Purulia", "South 24 Parganas", "Uttar Dinajpur (North Dinajpur)"
        };

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, state);

        inpStates.setAdapter(adapter);

        inpStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDivision = state[i];
                if (i == 0) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, district);
                    Districts.setAdapter(adapter4);
                }
                if (i == 1) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, AndhraPradesh);
                    Districts.setAdapter(adapter4);
                }
                if (i == 2) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, ArunanchalPradesh);
                    Districts.setAdapter(adapter4);
                }
                if (i == 3) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Assam);
                    Districts.setAdapter(adapter4);
                }
                if (i == 4) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Bihar);
                    Districts.setAdapter(adapter4);
                }
                if (i == 5) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, chandigarh);
                    Districts.setAdapter(adapter4);
                }
                if (i == 6) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Chhattisgarh);
                    Districts.setAdapter(adapter4);
                }
                if (i == 7) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Dadra);
                    Districts.setAdapter(adapter4);
                }
                if (i == 8) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Daman);
                    Districts.setAdapter(adapter4);
                }
                if (i == 9) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Delhi);
                    Districts.setAdapter(adapter4);
                }
                if (i == 10) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Goa);
                    Districts.setAdapter(adapter4);
                }
                if (i == 11) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Gujarat);
                    Districts.setAdapter(adapter4);
                }
                if (i == 12) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Haryana);
                    Districts.setAdapter(adapter4);
                }
                if (i == 13) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Himachal);
                    Districts.setAdapter(adapter4);
                }
                if (i == 14) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, JammuKashmir);
                    Districts.setAdapter(adapter4);
                }
                if (i == 15) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Jharkhand);
                    Districts.setAdapter(adapter4);
                }
                if (i == 16) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Karnataka);
                    Districts.setAdapter(adapter4);
                }
                if (i == 17) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Kerala);
                    Districts.setAdapter(adapter4);
                }
                if (i == 18) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Lakshadweep);
                    Districts.setAdapter(adapter4);
                }
                if (i == 19) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Madhya);
                    Districts.setAdapter(adapter4);
                }
                if (i == 20) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Maharashtra);
                    Districts.setAdapter(adapter4);
                }
                if (i == 21) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Manipur);
                    Districts.setAdapter(adapter4);
                }
                if (i == 22) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Meghalaya);
                    Districts.setAdapter(adapter4);
                }
                if (i == 23) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Mizoram);
                    Districts.setAdapter(adapter4);
                }
                if (i == 24) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Nagaland);
                    Districts.setAdapter(adapter4);
                }
                if (i == 25) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Odisha);
                    Districts.setAdapter(adapter4);
                }
                if (i == 26) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Puducherry);
                    Districts.setAdapter(adapter4);
                }
                if (i == 27) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Punjab);
                    Districts.setAdapter(adapter4);
                }
                if (i == 28) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Rajasthan);
                    Districts.setAdapter(adapter4);
                }
                if (i == 29) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Sikkim);
                    Districts.setAdapter(adapter4);
                }
                if (i == 30) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, TamilNadu);
                    Districts.setAdapter(adapter4);
                }
                if (i == 31) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Telangana);
                    Districts.setAdapter(adapter4);
                }
                if (i == 32) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Tripura);
                    Districts.setAdapter(adapter4);
                }
                if (i == 33) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Uttarakhand);
                    Districts.setAdapter(adapter4);
                }
                if (i == 34) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, UttarPradesh);
                    Districts.setAdapter(adapter4);
                }
                if (i == 35) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(OwnerDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, WestBengal);
                    Districts.setAdapter(adapter4);
                }
               // Toast.makeText(OwnerDetailsActivity.this, "" + selectedDivision, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void SignOut() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(OwnerDetailsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void RegisterRecords() {

        String NAME = OwnerName.getText().toString().trim();
        String SNAME = ShopName.getText().toString().trim();
        String PH = Phone.getText().toString().trim();
        String MOB = Mobile.trim();
        String PASS = Password.getText().toString().trim();
        String ADD = Address.getText().toString().trim();
        String STYPE = String.valueOf(spin.getSelectedItem());
        String SSTATE = String.valueOf(inpStates.getSelectedItem());
        String SDIST = String.valueOf(Districts.getSelectedItem());
        String AccStatus = null;
        String Rating = "0.0/0";

        Calendar now = Calendar.getInstance();
        final int thisMonth = now.get(Calendar.MONTH) + 1; //Return one month back, so add 1 for getting correct month
        final int thisYear = now.get(Calendar.YEAR) + 1; //One year of trial period from registration year

        if(thisMonth <= 9){
            AccStatus = "TRAIL0" + thisMonth + thisYear;
        }else{
            AccStatus = "TRAIL" + thisMonth + thisYear;
        }

        if (TextUtils.isEmpty(NAME)) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE ENTER NAME", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(SNAME)) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE ENTER SHOP NAME", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(PH)) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE ENTER PHONE NUMBER", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(SNAME)) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE ENTER PASSWORD", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(ADD)) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE ENTER ADDRESS", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(PASS)) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE ENTER PASSWORD", Toast.LENGTH_LONG).show();
        } else if(AccStatus == null){
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE CHECK ACCOUNT STATUS", Toast.LENGTH_LONG).show();
        } else if(STYPE.equals("--Select Shop Type--") || TextUtils.isEmpty(STYPE) ) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE SELECT SHOP TYPE", Toast.LENGTH_LONG).show();
        } else if(SSTATE.equals("---Select State---") ) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE SELECT STATE", Toast.LENGTH_LONG).show();
        } else if(SDIST.equals("---Select District --") ) {
            Toast.makeText(OwnerDetailsActivity.this, "PLEASE SELECT DISTRICT", Toast.LENGTH_LONG).show();
        } else {
            Owner owner = new Owner(NAME, SNAME, PH, MOB, PASS, ADD, STYPE, SSTATE, SDIST, AccStatus, Rating);

            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("Owner_Name").setValue(NAME);
            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("Shop_Name").setValue(SNAME);
            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("Shop_Type").setValue(STYPE);
            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("Phone_Number").setValue(PH);
            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("Mobile_Number").setValue(MOB);
            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("Address").setValue(ADD);
            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("State").setValue(SSTATE);
            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("District").setValue(SDIST);
            database.child(SSTATE).child(SDIST).child(STYPE).child(MOB).child("Rating").setValue(Rating);

            logDatabase.child(MOB).child("Mobile Number").setValue(MOB);
            logDatabase.child(MOB).child("Password").setValue(PASS);
            logDatabase.child(MOB).child("State").setValue(SSTATE);
            logDatabase.child(MOB).child("District").setValue(SDIST);
            logDatabase.child(MOB).child("AccType").setValue(AccStatus);
            logDatabase.child(MOB).child("ShopType").setValue(STYPE);

            Toast.makeText(this, "Record Saved Successfully....Please Login Again....", Toast.LENGTH_LONG).show();
            SignOut();
        }

    }
}