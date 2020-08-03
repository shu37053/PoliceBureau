package com.example.policebureauapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoliceSignInActivity extends AppCompatActivity {

    TextView usernameTextView;
    Spinner stateSpinner;
    Spinner districtSpinner;
    Spinner policeStationSpinner;
    ArrayList<String> states = new ArrayList<String>();
    AlertDialog.Builder builder;
    DatabaseReference databaseReference;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_sign_in);

        toolbar=(Toolbar) findViewById(R.id.policeSignIntoolbar);
        setSupportActionBar(toolbar);
        setTitle("Select Your Police Station");

        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        districtSpinner = (Spinner) findViewById(R.id.districtSpinner);
        policeStationSpinner = (Spinner) findViewById(R.id.policeStationSpinner);
        builder = new AlertDialog.Builder(this);
        states.add("--Select--");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("State");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    ArrayList<Map<Object, Object>> arrayList = (ArrayList<Map<Object, Object>>) dataSnapshot.getValue();
                    Log.i("hello1", String.valueOf(arrayList.size()));
                    Log.i("hello1", arrayList.toString());
                    for (Map<Object, Object> itr : arrayList) {
                        if (itr != null) {

                            Log.i("Hello", itr.get("state") + "");
                            Log.i("Hello", itr.get("sid") + "");
                            states.add(itr.get("state") + "");
                        }
                    }
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, states);
                stateSpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                usernameTextView.setVisibility(View.INVISIBLE);

                if (position != 0) {

                    final ArrayList<String> districts = new ArrayList<>();
                    districts.add("--Select--");
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("City").child(position + "");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Log.i("District", dataSnapshot.toString());
                            if (dataSnapshot.getValue() != null) {
                                ArrayList<HashMap<Object, Object>> arrayList = (ArrayList<HashMap<Object, Object>>) dataSnapshot.getValue();
                                for (HashMap<Object, Object> itr : arrayList) {
                                    if (itr != null) {
                                        districts.add(itr.get("city") + "");
                                    }
                                }
                            }
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, districts);
                            districtSpinner.setAdapter(spinnerAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    List<String> list = Arrays.asList("--Select--");
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(parent.getContext(), android.R.layout.simple_spinner_item, list);
                    districtSpinner.setAdapter(spinnerAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                usernameTextView.setVisibility(View.INVISIBLE);
                int state = stateSpinner.getSelectedItemPosition() - 1;
                int district = districtSpinner.getSelectedItemPosition() - 1;

                if (state >= 0 && district >= 0) {
                    Integer.toString(state);
                    databaseReference = FirebaseDatabase.getInstance().getReference().
                            child("PoliceStation").child(Integer.toString(state) + Integer.toString(district));

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.i("Hello", dataSnapshot.toString());

                            ArrayList<String> policeStations = new ArrayList<>();
                            policeStations.add("--Select--");

                            if (dataSnapshot.getValue() != null) {
                                HashMap<Integer, Object> hm = (HashMap<Integer, Object>) dataSnapshot.getValue();
                                Log.i("Hello", hm.toString());

                                for (Map.Entry mapElement : hm.entrySet()) {
                                    if (mapElement.getValue() != null) {
                                        HashMap<Object, Object> val = (HashMap<Object, Object>) mapElement.getValue();
                                        Log.i("Prakhar", (String) val.get("policestation"));
                                        policeStations.add(val.get("policestation") + "");
                                    }
                                }
                            }
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, policeStations);
                            policeStationSpinner.setAdapter(spinnerAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    List<String> list = Arrays.asList("--Select--");
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(parent.getContext(), android.R.layout.simple_spinner_item, list);
                    policeStationSpinner.setAdapter(spinnerAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        policeStationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usernameTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void generateUsername(View view) {

        if (((String) stateSpinner.getSelectedItem()).matches("--Select--")) {
            Toast.makeText(this, "Please select the state", Toast.LENGTH_SHORT).show();
        } else if (((String) districtSpinner.getSelectedItem()).matches("--Select--")) {
            Toast.makeText(this, "Please select the district", Toast.LENGTH_SHORT).show();
        } else if (((String) policeStationSpinner.getSelectedItem()).matches("--Select--")) {
            Toast.makeText(this, "Please select the police station", Toast.LENGTH_SHORT).show();
        } else {

//            String state = (String) stateSpinner.getSelectedItem();
//            String district = (String) districtSpinner.getSelectedItem();
//            String policeStation = (String) policeStationSpinner.getSelectedItem();

            int stateNo = stateSpinner.getSelectedItemPosition() - 1;
            int districtNo = districtSpinner.getSelectedItemPosition() - 1;
            int policeStationNo = policeStationSpinner.getSelectedItemPosition() - 1;

            final String username = Integer.toString(stateNo) + Integer.toString(districtNo) + Integer.toString(policeStationNo);
//            usernameTextView.setText(username);
//            usernameTextView.setVisibility(View.VISIBLE);

            databaseReference=FirebaseDatabase.getInstance().getReference("RegisteredStations").child(username);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!=null){

                        Log.i("LogIn Value",dataSnapshot.getValue().toString());
                        HashMap<Object,Object>hashMap= (HashMap<Object, Object>) dataSnapshot.getValue();
                        Log.i("LogIn Value",hashMap.get("password").toString());
                        generateDialogBoxToLogin(username,hashMap.get("password").toString());
                    }
                    else{
                        generateDialogBoxToRegister(username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void generateDialogBoxToRegister(final String username) {
        final View dialogLayout = getLayoutInflater().inflate(R.layout.register_alert_dialog, null);
        EditText UsernameEditText = (EditText) dialogLayout.findViewById(R.id.registrationIdEditText);
        UsernameEditText.setText(username);

        builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText SoEditText = (EditText) dialogLayout.findViewById(R.id.stationHeadNameEditText);
                String So = SoEditText.getText().toString().trim();

                EditText MobileEditText = (EditText) dialogLayout.findViewById(R.id.MobileNoEditText);
                String MobileNo = MobileEditText.getText().toString().trim();

                if (dialogLayout.getParent() != null) {
                    ((ViewGroup) dialogLayout.getParent()).removeView(dialogLayout); // <- fix
                }
                generateDialogBoxForPassword(username, So, MobileNo);

            }
        }).setNegativeButton("Cancel", null);
        builder.setView(dialogLayout);
        builder.setCancelable(false);
        builder.setTitle("Register Screen");
        builder.setMessage("Do u want to register?");
        builder.show();
    }

    public void generateDialogBoxForPassword(final String username, String So, String MobileNo) {
        View dialogLayout = getLayoutInflater().inflate(R.layout.password_screen, null);
        TextView textView = (TextView) dialogLayout.findViewById(R.id.PasswordTextView);

        databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredStations").child(username);
        String password = databaseReference.push().getKey();
        String specialPassword = databaseReference.push().getKey();
        Log.i("Password", password);
        Log.i("specialPassword", specialPassword);

        PoliceStation policeStation=new PoliceStation(username,password,specialPassword,So,MobileNo);
        databaseReference.setValue(policeStation);

        textView.setText("  Password : "+password+"\n\n  Special Password : "+specialPassword);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), PoliceActivity.class);
                startActivity(intent);
                SharedPreferences sharedPreferences =
                        getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
                sharedPreferences.edit().putString("username", username).apply();
                finish();
            }
        });
        builder.setCancelable(false);
        builder.setTitle("Password!!!");
        builder.setMessage("Save your passwords for future reference");
        builder.setView(dialogLayout);
        builder.show();
    }

    public void generateDialogBoxToLogin(final String username, final String password) {
        final View dialogLayout = getLayoutInflater().inflate(R.layout.login_alert_dialog, null);
        final EditText editText = (EditText) dialogLayout.findViewById(R.id.registrationIdEditText);
        editText.setText(username);
        final EditText editTextPassword = (EditText) dialogLayout.findViewById(R.id.passwordEditText);

        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                String Password = String.valueOf(editTextPassword.getText());

                if (password.matches(Password)) {
                    Intent intent = new Intent(getApplicationContext(), PoliceActivity.class);
                    startActivity(intent);

                    SharedPreferences sharedPreferences =
                            getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
                    sharedPreferences.edit().putString("username", username).apply();
                    finish();
                } else {
                    Toast.makeText(PoliceSignInActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    editTextPassword.setText("");
                }
            }
        }).setNegativeButton("Cancel", null);
        builder.setView(dialogLayout);
        builder.setCancelable(false);
        builder.setTitle("Login");
        builder.setMessage("Do u want to Login?");
        builder.show();
    }
}
