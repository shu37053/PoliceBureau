package com.example.policebureauapp;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class FirRecyclerAdapter extends RecyclerView.Adapter<FirViewHolder> {

    ArrayList<String>firIdArrayList;
    ArrayList<FIR>firarrayList;

    public FirRecyclerAdapter(ArrayList<String>firIdArrayList,ArrayList<FIR>firarrayList) {
        super();
        this.firIdArrayList=firIdArrayList;
       this.firarrayList=firarrayList;
    }


    @NonNull
    @Override
    public FirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fir_recycler_view_layout,parent,false);
        return new FirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirViewHolder holder, final int position) {
            holder.firIdTextView.setText("FIR ID : "+firIdArrayList.get(position));
            holder.statusTextView.setText(firarrayList.get(position).getStatus());
            holder.nameTextView.setText(firarrayList.get(position).getApplicantName());
            holder.mobileTextView.setText(firarrayList.get(position).getApplicantMobileNo());
            holder.dateTextView.setText(firarrayList.get(position).getIncindentDate());

            if(holder.statusTextView.getText().toString().matches("Solved")){
                holder.statusTextView.setBackgroundColor(Color.GREEN);
            }
            else{
                holder.statusTextView.setBackgroundColor(Color.RED);
            }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),FirDetailActivity.class);

                intent.putExtra("fir",  firarrayList.get(position));
                intent.putExtra("firId",firIdArrayList.get(position));
                startActivity(v.getContext(),intent,null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return firIdArrayList.size();
    }
}
