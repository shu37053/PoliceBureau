package com.example.policebureauapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    Switch policeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        policeSwitch=(Switch) findViewById(R.id.policeSwitch);
    }

    public void onEnter(View view){

        if(policeSwitch.isChecked()){
//            Toast.makeText(this, "Enter as a police Station", Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(getApplicationContext(),PoliceSignInActivity.class);
            startActivity(intent);
            finish();
        }
        else{

            Intent intent=new Intent(getApplicationContext(),NcrbOffcialSignInActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
