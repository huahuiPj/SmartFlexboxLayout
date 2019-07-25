package com.chh.flexboxlayoututils.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chh.flexboxlayoututils.widget.SmartFlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentViewPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private Context context;
    private List<Fragment> fragmentList ;
    private SmartFlexboxLayout flexboxLayout;
    private List<Integer> list;

    public MyFragmentViewPagerAdapter(@NonNull FragmentManager fm, Context context, List<Fragment> fragmentList,SmartFlexboxLayout flexboxLayout) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.flexboxLayout = flexboxLayout;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList!=null?fragmentList.get(position):null;
    }

    @Override
    public int getCount() {
        return fragmentList!=null?fragmentList.size():0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        list.add(position);
        flexboxLayout.setSelectedData(list);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
