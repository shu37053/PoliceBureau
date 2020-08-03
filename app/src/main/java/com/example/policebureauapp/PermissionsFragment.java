package com.example.policebureauapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class PermissionsFragment extends Fragment {

    RecyclerView recyclerView;
    PermissionRecyclerAdapter arrayAdapter;
    ArrayList<String> permissionIdList = new ArrayList<>();
    ArrayList<PermissionClass> permissionArrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String username;
    DatabaseReference databaseReference;

    public PermissionsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.permissions, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.permissionsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHorizontalScrollBarEnabled(true);

        permissionIdList.clear();
        permissionArrayList.clear();


        arrayAdapter = new PermissionRecyclerAdapter(permissionIdList,permissionArrayList);
        recyclerView.setAdapter(arrayAdapter);

        sharedPreferences = view.getContext().getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Permissions").child(username);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("Permission Fragment", dataSnapshot.getValue().toString());
                PermissionClass currentPermission = dataSnapshot.getValue(PermissionClass.class);
                if (!permissionIdList.contains(dataSnapshot.getKey())) {
                    permissionIdList.add(dataSnapshot.getKey());
                    permissionArrayList.add(currentPermission);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                PermissionClass currentPermission = dataSnapshot.getValue(PermissionClass.class);
                if (permissionIdList.contains(dataSnapshot.getKey())) {
                    permissionIdList.remove(dataSnapshot.getKey());
                    permissionArrayList.remove(currentPermission);
                    arrayAdapter.notifyDataSetChanged();
                }
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
