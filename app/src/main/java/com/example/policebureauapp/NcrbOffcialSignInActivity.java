package com.example.policebureauapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NcrbOffcialSignInActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Toolbar toolbar;
    RelativeLayout relativeLayout;
    ArrayList<String>username;
    ArrayList<String>password;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncrb_offcial_sign_in);
        toolbar = (Toolbar) findViewById(R.id.ncrbOfficialToolbar);
        setSupportActionBar(toolbar);
        setTitle("Special Officer SignIn");

        editTextPassword = (EditText) findViewById(R.id.passwordNcrbEditText);
        editTextUsername = (EditText) findViewById(R.id.usernameNcrbEditText);
        relativeLayout=(RelativeLayout) findViewById(R.id.ncrbRelativeLayout);

//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View view = v.get;
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//            }
//        });
    }

    public void onEnter(View view) {

        if (editTextUsername.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
            editTextUsername.requestFocus();
        } else if (editTextPassword.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
            editTextPassword.requestFocus();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
            sharedPreferences.edit().putString("ncrbusername", editTextUsername.getText().toString()).apply();

            Intent intent = new Intent(NcrbOffcialSignInActivity.this, NcrbFirActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
