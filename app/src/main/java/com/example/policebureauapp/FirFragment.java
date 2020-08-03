package com.example.policebureauapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class FirFragment extends Fragment {

    ArrayList<String> status = new ArrayList<>(Arrays.asList("All", "Solved", "Unsolved"));
    Spinner spinner;
    RecyclerView recyclerView;
    FirRecyclerAdapter arrayAdapter;
    ArrayList<FIR> firArrayList = new ArrayList<>();
    ArrayList<String> firIdList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;

    public FirFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fir, container, false);
        firIdList.clear();
        firArrayList.clear();

//        spinner = (Spinner) view.findViewById(R.id.spinner1);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(parent.getContext(), "No Item selected", Toast.LENGTH_SHORT).show();
//            }
//        });


        recyclerView = (RecyclerView) view.findViewById(R.id.firRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHorizontalScrollBarEnabled(true);


        arrayAdapter = new FirRecyclerAdapter(firIdList, firArrayList);
        recyclerView.setAdapter(arrayAdapter);

        sharedPreferences = view.getContext().getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Fir").child(username);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FIR newFir = dataSnapshot.getValue(FIR.class);
                if (!firIdList.contains(dataSnapshot.getKey())) {
                    firIdList.add(dataSnapshot.getKey());
                    firArrayList.add(newFir);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}
