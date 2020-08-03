package com.example.policebureauapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NcrbFirActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner1, spinner2, spinner3;
    ArrayList<String> states = new ArrayList<>();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirRecyclerAdapter arrayAdapter;
    ArrayList<String> firIdList = new ArrayList<>();
    ArrayList<FIR> firArrayList = new ArrayList<FIR>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ncrb_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.ncrbLogOut) {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
            sharedPreferences.edit().putString("ncrbusername", "").apply();
            Intent intent = new Intent(NcrbFirActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(NcrbFirActivity.this, FirStatisticsActivity.class);
            intent.putExtra("firIdList", firIdList);
            intent.putExtra("firList", firArrayList);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncrb_fir);

        firIdList.clear();
        firArrayList.clear();

        toolbar = (Toolbar) findViewById(R.id.ncrbFirToolbar);
        setSupportActionBar(toolbar);
        setTitle("Fir Details");
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);

        states.add("--Select--");

        recyclerView = (RecyclerView) findViewById(R.id.ncrbFirRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHorizontalScrollBarEnabled(true);


        arrayAdapter = new FirRecyclerAdapter(firIdList, firArrayList);
        recyclerView.setAdapter(arrayAdapter);

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
                spinner1.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                            spinner2.setAdapter(spinnerAdapter);
                            check();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    List<String> list = Arrays.asList("--Select--");
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(parent.getContext(), android.R.layout.simple_spinner_item, list);
                    spinner2.setAdapter(spinnerAdapter);
                    check();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int state = spinner1.getSelectedItemPosition() - 1;
                int district = spinner2.getSelectedItemPosition() - 1;

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
                            spinner3.setAdapter(spinnerAdapter);
                            check();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    List<String> list = Arrays.asList("--Select--");
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(parent.getContext(), android.R.layout.simple_spinner_item, list);
                    spinner3.setAdapter(spinnerAdapter);
                    check();
                }
                check();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                check();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        check();
    }

    public void check() {
        final int state = spinner1.getSelectedItemPosition() - 1;
        int district = spinner2.getSelectedItemPosition() - 1;
        int policeStation = spinner3.getSelectedItemPosition() - 1;
        final String stateCode = Integer.toString(state);
        final String districtCode = Integer.toString(district);
        final String policeCode = Integer.toString(policeStation);
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Fir");
        firArrayList.clear();
        firIdList.clear();

        Log.i("usernamevalues", stateCode + districtCode + policeCode);

        if (state < 0) {

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.getValue() != null) {
//                        Log.i("helo hello", dataSnapshot.getValue().toString());
//                        HashMap<Object, Object> hm = (HashMap<Object, Object>) dataSnapshot.getValue();
//                        for (Map.Entry mapElement : hm.entrySet()) {
//                            Log.i("helo hello", mapElement.getValue().toString());
//                            if (mapElement.getValue() != null) {
//                                HashMap<Object, Object> hm1 = (HashMap<Object, Object>) mapElement.getValue();
//                                for (Map.Entry mapElement1 : hm1.entrySet()) {
//                                    FIR curFir = new FIR(mapElement1.getValue());
//                                    String firId = (String) mapElement1.getKey();
//                                    String level=curFir.getLevel();
//                                    String status=curFir.getStatus();
//                                    if (!firIdList.contains(firId)&&level.matches("3")&&status.matches("Unsolved")) {
//                                        firIdList.add(firId);
//                                        firArrayList.add(curFir);
//                                    }
//                                }
//                            }
//                        }
//                    }
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (district < 0) {

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        HashMap<Object, Object> hm = (HashMap<Object, Object>) dataSnapshot.getValue();
                        for (Map.Entry mapElement : hm.entrySet()) {
                            if (mapElement.getKey().toString().matches(stateCode + "(.*)")) {
                                Log.i("username", mapElement.getKey().toString());
                                Log.i("helo hello", mapElement.getValue().toString());
                                if (mapElement.getValue() != null) {
                                    HashMap<Object, Object> hm1 = (HashMap<Object, Object>) mapElement.getValue();
                                    for (Map.Entry mapElement1 : hm1.entrySet()) {
                                        FIR curFir = new FIR(mapElement1.getValue());
                                        String firId = (String) mapElement1.getKey();
                                        String level=curFir.getLevel();
                                        String status=curFir.getStatus();
                                        if (!firIdList.contains(firId)&&level.matches("3")&&status.matches("Unsolved")) {
                                            firIdList.add(firId);
                                            firArrayList.add(curFir);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (policeStation < 0) {

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        HashMap<Object, Object> hm = (HashMap<Object, Object>) dataSnapshot.getValue();
                        for (Map.Entry mapElement : hm.entrySet()) {
                            if (mapElement.getKey().toString().matches(stateCode + districtCode + "(.*)")) {
                                Log.i("username", mapElement.getKey().toString());
                                Log.i("helo hello", mapElement.getValue().toString());
                                if (mapElement.getValue() != null) {
                                    HashMap<Object, Object> hm1 = (HashMap<Object, Object>) mapElement.getValue();
                                    for (Map.Entry mapElement1 : hm1.entrySet()) {
                                        FIR curFir = new FIR(mapElement1.getValue());
                                        String firId = (String) mapElement1.getKey();
                                        String level=curFir.getLevel();
                                        String status=curFir.getStatus();
                                        if (!firIdList.contains(firId)&&level.matches("2")&&status.matches("Unsolved")) {
                                            firIdList.add(firId);
                                            firArrayList.add(curFir);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        HashMap<Object, Object> hm = (HashMap<Object, Object>) dataSnapshot.getValue();
                        for (Map.Entry mapElement : hm.entrySet()) {
                            if (mapElement.getKey().toString().matches(stateCode + districtCode + policeCode + "(.*)")) {
                                Log.i("username", mapElement.getKey().toString());
                                Log.i("helo hello", mapElement.getValue().toString());
                                if (mapElement.getValue() != null) {
                                    HashMap<Object, Object> hm1 = (HashMap<Object, Object>) mapElement.getValue();
                                    for (Map.Entry mapElement1 : hm1.entrySet()) {
                                        FIR curFir = new FIR(mapElement1.getValue());
                                        String firId = (String) mapElement1.getKey();
                                        String level=curFir.getLevel();
                                        String status=curFir.getStatus();
                                        if (!firIdList.contains(firId)&&level.matches("1")&&status.matches("Unsolved")) {
                                            firIdList.add(firId);
                                            firArrayList.add(curFir);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
