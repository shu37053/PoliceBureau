package com.example.policebureauapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComplaintRecyclerAdapter2 extends RecyclerView.Adapter<ComplaintRecyclerAdapter2.ComplaintViewHolder> {
    ArrayList<Complaint> complaintArrayList;
    ArrayList<String> complaintIdArrayLiist;

    public ComplaintRecyclerAdapter2(ArrayList<Complaint> complaintArrayList, ArrayList<String> complaintIdArrayLiist) {
        super();
        this.complaintArrayList = complaintArrayList;
        this.complaintIdArrayLiist = complaintIdArrayLiist;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.complaint_row,parent,false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, final int position) {
        Complaint complaint=complaintArrayList.get(position);
        holder.ComplaintId.setText(complaintIdArrayLiist.get(position));
        holder.complainant.setText(complaint.applicant);
        holder.date.setText(complaint.dateOfReg);
        holder.mobile.setText(complaint.applicantMobile);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ComplaintDetails.class);
                intent.putExtra(ComplaintDetails.compId,complaintIdArrayLiist.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintArrayList.size();
    }

    class ComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView ComplaintId;
        TextView complainant;
        TextView mobile;
        TextView date;
        View root;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            ComplaintId =itemView.findViewById(R.id.verificationIdTextView);
            complainant=itemView.findViewById(R.id.row_tv1);
            mobile=itemView.findViewById(R.id.row_tv2);
            date=itemView.findViewById(R.id.row_tv3);
            root=itemView;
        }
    }
}
