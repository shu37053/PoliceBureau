package com.example.policebureauapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ComplaintDetails extends AppCompatActivity {
    static  final String compId="commpId";
    //static  final String pid=""
    SharedPreferences sharedPreferences;
    TextView complaintId;
    TextView applicantnamme;
    TextView applicantMobile;
    TextView date;
    TextView detail;
    Toolbar toolbar;
    ImageButton callButton;


    private void initialize(){
        complaintId=findViewById(R.id.compDet_compId);
        applicantnamme=findViewById(R.id.compDet_name);
        applicantMobile=findViewById(R.id.compDet_mob);
        date=findViewById(R.id.compDet_date);
        detail=findViewById(R.id.compDet_det);
        toolbar=findViewById(R.id.complaintDet_toolbar);
        callButton=findViewById(R.id.compDet_call);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_details);
        initialize();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Complaint  complaint;
        final String compId;
        final Intent intent=getIntent();
        compId=intent.getStringExtra(ComplaintDetails.compId);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Registered Complaint");
        sharedPreferences = getBaseContext().getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        if(username.equals("")||sharedPreferences==null){
            Log.e("Tag","shared preferences error");
        }
        reference=reference.child(username);
        reference=reference.child(compId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("structure of snapshot",dataSnapshot.toString());
                Complaint complaint1=dataSnapshot.getValue(Complaint.class);
                complaintId.setText(compId);
                applicantnamme.setText(complaint1.applicant);
                applicantMobile.setText(complaint1.applicantMobile);
                date.setText(complaint1.dateOfReg);
                detail.setText(complaint1.detaails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        callButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1=new Intent(Intent.ACTION_CALL);
//                intent1.setData(Uri.parse("tel:"+applicantMobile.getText().toString().trim()));
//                startActivity(intent1);
//            }
//        });
    }
}
