package com.example.policebureauapp;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PermissionViewHolder extends RecyclerView.ViewHolder {

    TextView permissionIdTextView;
    TextView permissionStatusTextView;
    TextView permissionUptoTextView,applicantMobile,applicantName;
    CardView relativeLayout;

    public PermissionViewHolder(@NonNull View itemView) {
        super(itemView);

        permissionIdTextView = itemView.findViewById(R.id.permissionIdTextView);
        permissionStatusTextView = itemView.findViewById(R.id.permissionStatusTextView);
        permissionUptoTextView = itemView.findViewById(R.id.permissionUptoTextView);
        applicantMobile=itemView.findViewById(R.id.permissionApplicantMobileNoTextView);
        applicantName=itemView.findViewById(R.id.permissionApplicantNameTextView);
        relativeLayout = itemView.findViewById(R.id.permissionRelativeLayout);
    }

}
