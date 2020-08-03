package com.example.policebureauapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PoliceActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Spinner spinner;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.firEntry) {
//            Toast.makeText(this, "Data Entry Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PoliceActivity.this, FirRegistration.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.permissionEntry) {
            Intent intent = new Intent(PoliceActivity.this, PermissionFormActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logOut) {
            SharedPreferences sharedPreferences =
                    getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
            sharedPreferences.edit().putString("username", "").apply();
            Intent intent = new Intent(PoliceActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);


        toolbar = (Toolbar) findViewById(R.id.policeToolbar);
        setSupportActionBar(toolbar);
        setTitle("Police Bureau App");

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new FirFragment());
        fragmentArrayList.add(new ComplaintsFragment());
        fragmentArrayList.add(new PermissionsFragment());
        fragmentArrayList.add(new VerificationsFragment());

        ArrayList<String> fragmentTitleArrayList = new ArrayList<>();
        fragmentTitleArrayList.add("FIR");
        fragmentTitleArrayList.add("Complaints");
        fragmentTitleArrayList.add("Permissions");
        fragmentTitleArrayList.add("Verifications");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList, fragmentTitleArrayList);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

}
