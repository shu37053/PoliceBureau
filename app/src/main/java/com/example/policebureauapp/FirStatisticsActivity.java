package com.example.policebureauapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FirStatisticsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_statistics);

        toolbar = (Toolbar) findViewById(R.id.toolbarSecondLast);
        setSupportActionBar(toolbar);
        setTitle("Statistical Analysis");

        Intent intent = getIntent();
        ArrayList<FIR> firArrayList = (ArrayList<FIR>) intent.getSerializableExtra("firList");
        ArrayList<String> firIdArrayList = intent.getStringArrayListExtra("firIdList");

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new BarchartFragment(firIdArrayList,firArrayList));
        fragmentArrayList.add(new PieChartFragment(firIdArrayList,firArrayList));

        ArrayList<String> fragmentTitleArrayList = new ArrayList<>();
        fragmentTitleArrayList.add("Bar Chart");
        fragmentTitleArrayList.add("Pie Chart");

        viewPager = (ViewPager) findViewById(R.id.viewPager1);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList, fragmentTitleArrayList);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout1);
        tabLayout.setupWithViewPager(viewPager);
    }
}
