package com.example.policebureauapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
        final String usernamePolice = sharedPreferences.getString("username", "");
        final String usernameNcrb = sharedPreferences.getString("ncrbusername", "");

        Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.i("Runnable has run!", "A second has passed...");

//                Intent intent =new Intent(MainActivity.this,PoliceActivity.class);

                if (usernamePolice.matches("")&&usernameNcrb.matches("")) {
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(!usernamePolice.matches("")){
                    Intent intent = new Intent(MainActivity.this, PoliceActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, NcrbFirActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        handler.postDelayed(run, 2000);
    }
}
