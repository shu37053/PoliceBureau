package com.example.policebureauapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import static android.content.Context.MODE_PRIVATE;

public class ComplaintsFragment extends Fragment {

    RecyclerView recyclerView;
    ComplaintRecyclerAdapter2 arrayAdapter;
    ArrayList<Complaint>  complaintArrayList=new ArrayList<Complaint>();
    ArrayList<String> complaintIdArrayList=new ArrayList<String>();
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;


    public ComplaintsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.complaints,container,false);
        complaintArrayList.clear();
        complaintIdArrayList.clear();

        recyclerView = (RecyclerView) view.findViewById(R.id.complaintRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHorizontalScrollBarEnabled(true);
        final ComplaintRecyclerAdapter2 adapter2=new ComplaintRecyclerAdapter2(complaintArrayList,complaintIdArrayList);
        recyclerView.setAdapter(adapter2);
        sharedPreferences = view.getContext().getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Registered Complaint");
        databaseReference=databaseReference.child(username);
        //databaseReference = FirebaseDatabase.getInstance().getReference("Registered Complaint").child(username);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Complaint complaint=dataSnapshot.getValue(Complaint.class);
                if(complaintIdArrayList.contains(dataSnapshot.getKey())==false){
                    complaintIdArrayList.add(dataSnapshot.getKey());
                    complaintArrayList.add(complaint);
                    adapter2.notifyDataSetChanged();
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
