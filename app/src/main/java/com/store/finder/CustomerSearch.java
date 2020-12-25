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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerSearch extends AppCompatActivity {

    private Spinner spin, Districts, inpStates;
    DatabaseReference databaseReference;
    ValueEventListener listner;
    ArrayAdapter<String> adapter;
    ArrayList<String> spindatatlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);

        spin = findViewById(R.id.ctype);
        Districts = findViewById(R.id.cdist);
        inpStates = findViewById(R.id.cstate);

        CheckingInternetConnectivity();

        Districts.setEnabled(false);
        spin.setEnabled(false);

        databaseReference = FirebaseDatabase.getInstance().getReference("ShopType");

        spindatatlist = new ArrayList<>();
        adapter = new ArrayAdapter<String>(CustomerSearch.this,android.R.layout.simple_spinner_dropdown_item,spindatatlist);

        spin.setAdapter(adapter);
        retrievedata();

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
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, district);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(false);
                    spin.setEnabled(false);
                    spin.setSelection(0);
                    Districts.setSelection(0);
                }
                if (i == 1) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, AndhraPradesh);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 2) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, ArunanchalPradesh);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 3) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Assam);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 4) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Bihar);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 5) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, chandigarh);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 6) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Chhattisgarh);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 7) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Dadra);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 8) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Daman);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 9) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Delhi);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 10) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Goa);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 11) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Gujarat);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 12) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Haryana);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 13) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Himachal);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 14) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, JammuKashmir);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 15) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Jharkhand);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 16) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Karnataka);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 17) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Kerala);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 18) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Lakshadweep);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 19) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Madhya);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 20) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Maharashtra);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 21) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Manipur);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 22) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Meghalaya);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 23) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Mizoram);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 24) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Nagaland);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 25) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Odisha);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 26) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Puducherry);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 27) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Punjab);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 28) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Rajasthan);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 29) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Sikkim);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 30) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, TamilNadu);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 31) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Telangana);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 32) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Tripura);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 33) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, Uttarakhand);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 34) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, UttarPradesh);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                if (i == 35) {
                    final ArrayAdapter<String> adapter4;
                    adapter4 = new ArrayAdapter<String>(CustomerSearch.this, android.R.layout.simple_spinner_dropdown_item, WestBengal);
                    Districts.setAdapter(adapter4);
                    Districts.setEnabled(true);
                }
                //Toast.makeText(CustomerSearch.this, "" + selectedDivision, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Districts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("---Select District --")) {
                    String text = parent.getItemAtPosition(position).toString();
                    spin.setEnabled(false);
                    spin.setSelection(0);
                    //Toast.makeText(CustomerSearch.this,"null",Toast.LENGTH_SHORT).show();
                }
                else {
                    spin.setEnabled(true);
                    //Toast.makeText(CustomerSearch.this,"not null",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void CheckingInternetConnectivity(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager.getActiveNetworkInfo();
        if(null != activenetwork) {

        } else   {
            AlertDialog.Builder alert = new AlertDialog.Builder(CustomerSearch.this);
            alert.setTitle("Local Store Finder,");
            alert.setMessage("Please Enable Network !!");
            alert.setPositiveButton("Ok",null);
            alert.show();
        }
    }

    public void btnadd(View view) {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager.getActiveNetworkInfo();
        if(null != activenetwork) {
            SearchRecords();
        } else   {
            AlertDialog.Builder alert = new AlertDialog.Builder(CustomerSearch.this);
            alert.setTitle("Local Store Finder,");
            alert.setMessage("Please Enable Network !!");
            alert.setPositiveButton("Ok",null);
            alert.show();
        }
    }

    private void SearchRecords() {

        String STYPE = String.valueOf(spin.getSelectedItem());
        String SSTATE = String.valueOf(inpStates.getSelectedItem());
        String SDIST = String.valueOf(Districts.getSelectedItem());

        if(!(STYPE.equals("---Select Shop Type---") || SSTATE.equals("---Select State---") || SDIST.equals("---Select District --") || STYPE.equals("null")) ) {
            Intent intent = new Intent(CustomerSearch.this, ShopList.class);
            //  Toast.makeText(CustomerSearch.this,STYPE,Toast.LENGTH_SHORT).show();
            intent.putExtra("LoggedSTYPE", STYPE);
            intent.putExtra("flag", "0");
            intent.putExtra("LoggedST", SSTATE);
            intent.putExtra("LoggedDST", SDIST);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder alert = new AlertDialog.Builder(CustomerSearch.this);
            alert.setTitle("Local Store Finder,");
            alert.setMessage("All fields are required");
            alert.setPositiveButton("Ok",null);
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CustomerSearch.this,MainActivity.class);
        startActivity(intent);
    }
}