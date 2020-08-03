package com.example.policebureauapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class FirDetailActivity extends AppCompatActivity {


    TextView firId, name, mobile, date, firDetail;
    androidx.appcompat.widget.AppCompatImageButton callButton;
    Button status;
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    String username;
    String specialPassword;
    String curFirId;
    FIR curFir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_detail);

        firId = (TextView) findViewById(R.id.firDetailIdTextView);
        name = (TextView) findViewById(R.id.firDetailNameTextView);
        mobile = (TextView) findViewById(R.id.firDetailMobileTextView);
        date = (TextView) findViewById(R.id.firDetailDateTextView);
        firDetail = (TextView) findViewById(R.id.firDetailDTextView);
        callButton = (androidx.appcompat.widget.AppCompatImageButton) findViewById(R.id.firDetailCallButton);
        status = (Button) findViewById(R.id.firDetailStatusButton);


        sharedPreferences=getSharedPreferences("com.example.policebureauapp",MODE_PRIVATE);
        username=sharedPreferences.getString("username","");
        databaseReference= FirebaseDatabase.getInstance().getReference("RegisteredStations").child(username);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<Object,Object> hp= (HashMap<Object, Object>) dataSnapshot.getValue();
                Log.i("hello",hp.get("specialPassword").toString());
                specialPassword= (String) hp.get("specialPassword");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent = getIntent();
        curFir = (FIR) intent.getSerializableExtra("fir");
        curFirId = intent.getStringExtra("firId");

        firId.setText(curFirId);
        name.setText(curFir.getApplicantName());
        mobile.setText(curFir.getApplicantMobileNo());
        date.setText(curFir.getIncindentDate());
        firDetail.setText(curFir.getFirDetails());

        if(curFir.getStatus().matches("Solved")){
            status.setText("Solved");
            status.setBackgroundColor(Color.GREEN);
        }
        else{
            status.setText("Unsolved");
            status.setBackgroundColor(Color.RED);
        }


        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.getText().toString().matches("Unsolved")) {
                    showDialogBox();
                } else {
                    Toast.makeText(FirDetailActivity.this,
                            "You can't change Solved Fir to Unsolved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialogBox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialogBoxForConfirmation();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Change Status to Solved!!!");
        builder.setMessage("Are you sure?");
        builder.show();
    }

    private void showDialogBoxForConfirmation() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.login_alert_dialog, null);
        TextView textView = (TextView) view.findViewById(R.id.passwordTextView);
        textView.setText("Special Password");
        final EditText editText=(EditText) view.findViewById(R.id.passwordEditText);
        editText.setHint("Special Password");


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText.getText().toString().matches(specialPassword)){
                    databaseReference = FirebaseDatabase.getInstance().getReference("Registered Fir").child(username);
                    databaseReference.child(curFirId).removeValue();
                    curFir.setStatus("Solved");
                    databaseReference.child(curFirId).setValue(curFir);
                    Toast.makeText(FirDetailActivity.this, "Status Changed", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(FirDetailActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Change Status to Solved");
        builder.setView(view);
        builder.show();
    }
}
