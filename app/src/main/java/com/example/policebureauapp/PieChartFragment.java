package com.example.policebureauapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartFragment extends Fragment {

    ArrayList<String>firIdArrayList;
    ArrayList<FIR>firArrayList;

    public PieChartFragment( ArrayList<String>firIdArrayList,ArrayList<FIR>firArrayList){
        this.firArrayList=firArrayList;
        this.firIdArrayList=firIdArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.piechart, container, false);

        PieChart pieChart = view.findViewById(R.id.piechart);
        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new Entry(945f, 0));
        NoOfEmp.add(new Entry(1040f, 1));
        NoOfEmp.add(new Entry(1133f, 2));
        NoOfEmp.add(new Entry(1240f, 3));
        NoOfEmp.add(new Entry(1369f, 4));
        NoOfEmp.add(new Entry(1487f, 5));
        NoOfEmp.add(new Entry(1501f, 6));
//        NoOfEmp.add(new Entry(1645f, 7));
//        NoOfEmp.add(new Entry(1578f, 8));
//        NoOfEmp.add(new Entry(1695f, 9));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Types of Fir Issued");

        ArrayList<String> year = new ArrayList<>();

        year.add("Rape");
        year.add("Sexual Assault");
        year.add("Kidnapping");
        year.add("Dowry");
        year.add("Theft");
        year.add("Murder");
        year.add("Unsolved");
//        year.add("2015");
//        year.add("2016");
//        year.add("2017");
        PieData data = new PieData(year, dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(5000, 5000);
        return view;
    }
}
