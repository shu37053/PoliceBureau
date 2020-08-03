package com.example.policebureauapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class PermissionRecyclerAdapter extends RecyclerView.Adapter<PermissionViewHolder> {


    ArrayList<String> permissionIdArrayList;
    ArrayList<PermissionClass> permissionArrayList;

    public PermissionRecyclerAdapter(ArrayList<String> permissionIdArrayList, ArrayList<PermissionClass> permissionArrayList) {
        super();

        this.permissionIdArrayList = permissionIdArrayList;
        this.permissionArrayList = permissionArrayList;
    }

    @NonNull
    @Override
    public PermissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.permissions_recycler_view_layout, parent, false);
        return new PermissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionViewHolder holder, final int position) {
        holder.permissionIdTextView.setText("PERMISSION ID : " + permissionIdArrayList.get(position));
        holder.permissionStatusTextView.setText(permissionArrayList.get(position).getStatus1());
        holder.applicantName.setText(permissionArrayList.get(position).getApplicantName());
        holder.applicantMobile.setText(permissionArrayList.get(position).getApplicantMobileNo());

        if (permissionArrayList.get(position).getStatus1().matches("Pending")) {
            holder.permissionUptoTextView.setVisibility(View.INVISIBLE);
        } else {
            holder.permissionUptoTextView.setVisibility(View.VISIBLE);
           if(permissionArrayList.get(position).getEndDate().matches("")){
               holder.permissionUptoTextView.setText(permissionArrayList.get(position).getStartDate());
           }
           else{
               holder.permissionUptoTextView.setText(permissionArrayList.get(position).getEndDate());
           }
        }

        if(holder.permissionStatusTextView.getText().toString().matches("Granted")){
            holder.permissionStatusTextView.setBackgroundColor(Color.GREEN);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),PermissionDetailActivity.class);

                intent.putExtra("permission",  permissionArrayList.get(position));
                intent.putExtra("permissionId",permissionIdArrayList.get(position));
                startActivity(v.getContext(),intent,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return permissionIdArrayList.size();
    }

}
