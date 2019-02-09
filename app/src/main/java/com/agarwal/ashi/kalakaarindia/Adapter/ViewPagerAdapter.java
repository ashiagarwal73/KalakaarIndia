package com.agarwal.ashi.kalakaarindia.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.agarwal.ashi.kalakaarindia.Fragment.ImageFragment;

import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<String> images;
    public ViewPagerAdapter(FragmentManager fm, List<String> images) {
        super(fm);
        this.images=images;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle=new Bundle();
        bundle.putString("image",images.get(i));
        ImageFragment imageFragment=new ImageFragment();
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
