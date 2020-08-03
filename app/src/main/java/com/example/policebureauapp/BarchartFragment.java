package com.example.policebureauapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BarchartFragment extends Fragment {

    BarChart barChart;


    ArrayList<String> firIdArrayList;
    ArrayList<FIR> firArrayList;
    ArrayList<String> datesFirArrayList=new ArrayList<>();

    public BarchartFragment(ArrayList<String> firIdArrayList, ArrayList<FIR> firArrayList) {
        this.firArrayList = firArrayList;
        this.firIdArrayList = firIdArrayList;
//        for (int i = 0; i < firArrayList.size(); i++) {
//            datesFirArrayList.add(firArrayList.get(i).getIncindentDate().toString());
//        }
//
//        Date c = Calendar.getInstance().getTime();
//        System.out.println("Current time => " + c);
//
//        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        String formattedDate = df.format(c);
//        Log.i("dates",formattedDate);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Log.i("value", String.valueOf(firArrayList));

        View view = inflater.inflate(R.layout.barchart, container, false);

        barChart = view.findViewById(R.id.barchart);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(1487f, 5));
        NoOfEmp.add(new BarEntry(1501f, 6));
        NoOfEmp.add(new BarEntry(1445f, 7));
        NoOfEmp.add(new BarEntry(1200f, 8));
        NoOfEmp.add(new BarEntry(1000f, 9));

        ArrayList<String> year = new ArrayList<>();

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Fir Issued in one year");
        barChart.animateY(3000);
        BarData data = new BarData(year, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);
        barChart.setDrawBarShadow(true);
        return view;
    }
}
