package com.example.policebureauapp;

import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FirViewHolder extends RecyclerView.ViewHolder {

    TextView firIdTextView;
    TextView statusTextView;
    TextView nameTextView,mobileTextView,dateTextView;
    CardView cardView;


    public FirViewHolder(@NonNull View itemView) {
        super(itemView);

        firIdTextView = (TextView) itemView.findViewById(R.id.firIdTextView);
        statusTextView = (TextView) itemView.findViewById(R.id.firStatusTextView);
        nameTextView=(TextView) itemView.findViewById(R.id.firComplainaintNameTextView);
        mobileTextView=(TextView) itemView.findViewById(R.id.firComplainantMobileNoTextView);
        dateTextView=(TextView) itemView.findViewById(R.id.firIncidentTextView);
        cardView=(CardView) itemView.findViewById(R.id.cardView1);


    }
}
