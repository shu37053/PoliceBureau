package com.example.policebureauapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment>fragmentArrayList;
    private ArrayList<String>fragmentTitleArrayList;

    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment>arrayList1,ArrayList<String>arrayList2) {
        super(fm);

        fragmentArrayList=arrayList1;
        fragmentTitleArrayList=arrayList2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleArrayList.get(position);
    }
}
